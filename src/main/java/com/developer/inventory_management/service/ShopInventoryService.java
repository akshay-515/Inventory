package com.developer.inventory_management.service;

import com.developer.inventory_management.dto.CheckoutRequest;
import com.developer.inventory_management.dto.CheckoutResponse;
import com.developer.inventory_management.entity.Product;
import com.developer.inventory_management.entity.ShopInventory;
import com.developer.inventory_management.exception.OutOfStockException;
import com.developer.inventory_management.repository.ProductRepository;
import com.developer.inventory_management.repository.ShopInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShopInventoryService {
    private final ProductRepository productRepository;
    private final ShopInventoryRepository shopInventoryRepository;

    @Transactional
    public CheckoutResponse processCheckout(CheckoutRequest request){
        Product product = productRepository.findBySku(request.getSku())
                .orElseThrow(() -> new RuntimeException("Product Not found with SKU: " + request.getSku()));

        ShopInventory shopInventory = shopInventoryRepository.findByProductId(product.getId())
                .orElseThrow(() -> new RuntimeException("No inventory record found for Product: " + product.getName()));

        if(shopInventory.getStockCount() < request.getQuantity()){
            throw new OutOfStockException("Insufficient stock for " + product.getName()
            + ". Available: " + shopInventory.getStockCount()
            + ", Requested: " + request.getQuantity());
        }

        int newStockCount = shopInventory.getStockCount() - request.getQuantity();
        shopInventory.setStockCount(newStockCount);
        shopInventoryRepository.save(shopInventory);

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        boolean lowStockTriggered = newStockCount <= product.getLowStockThreshold();

        return CheckoutResponse.builder()
                .sku(product.getSku())
                .productName(product.getName())
                .purchasedQuantity(request.getQuantity())
                .remainingStock(newStockCount)
                .totalPrice(totalPrice)
                .isLowStockAlertTriggered(lowStockTriggered)
                .message(lowStockTriggered ? "WARNING: Stock is now below threshold!" : "Checkout successful")
                .build();
    }
}
