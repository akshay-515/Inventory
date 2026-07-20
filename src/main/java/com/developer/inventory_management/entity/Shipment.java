package com.developer.inventory_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "shipments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "shipment_type")
    private ShipmentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "shipment_status")
    private ShipmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;


    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;


    public enum ShipmentType {
        UPSTREAM_VENDOR_TO_WH,
        DOWNSTREAM_WH_TO_SHOP
    }

    public enum ShipmentStatus {
        ORDERED_FROM_VENDOR,
        IN_TRANSIT_TO_WH,
        ARRIVED_AT_WH,
        REQUESTED_BY_SHOP,
        DISPATCHED_TO_SHOP,
        DELIVERED
    }
}
