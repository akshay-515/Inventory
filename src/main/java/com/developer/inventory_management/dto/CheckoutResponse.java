package com.developer.inventory_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {

    private String sku;
    private String productName;
    private Integer purchasedQuantity;
    private Integer remainingStock;
    private BigDecimal totalPrice;
    private Boolean isLowStockAlertTriggered;
    private String message;
}
