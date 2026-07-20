package com.developer.inventory_management.controller;

import com.developer.inventory_management.dto.CheckoutRequest;
import com.developer.inventory_management.dto.CheckoutResponse;
import com.developer.inventory_management.service.ShopInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ShopInventorycontroller {
    private final ShopInventoryService shopInventoryService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request){
        CheckoutResponse response = shopInventoryService.processCheckout(request);
        return ResponseEntity.ok(response);
    }
}
