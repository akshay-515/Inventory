package com.developer.inventory_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "shop_inventories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true )
    private Product product;

    @Column(name = "stock_count", nullable = false)
    @Builder.Default
    private Integer stockCount = 0;

    @Column(name = "updated_at", insertable = false, updatable = false )
    private LocalDateTime updatedAt;

}
