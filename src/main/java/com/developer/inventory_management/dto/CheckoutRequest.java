package com.developer.inventory_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckoutRequest {
    @NotBlank(message = "Product SKU cannot be blank")
    private String sku;

    @NotNull(message = "quantity is required")
    @Min(value = 1 , message = "quantity must be at least 1")
    private Integer quantity;
}
