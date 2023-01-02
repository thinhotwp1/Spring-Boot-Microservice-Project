package com.learn.inventoryservice.repository;

import com.learn.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findAllBySkuCode(String skuCode);
}