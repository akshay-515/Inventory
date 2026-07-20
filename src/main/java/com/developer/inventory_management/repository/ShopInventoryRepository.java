package com.developer.inventory_management.repository;

import com.developer.inventory_management.entity.ShopInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopInventoryRepository extends JpaRepository<ShopInventory, Long> {
    Optional<ShopInventory> findByProductId(Long productId);
}
