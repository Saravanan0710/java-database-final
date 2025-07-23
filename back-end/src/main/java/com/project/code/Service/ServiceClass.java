package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClass {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public boolean validateInventory(Inventory inventory) {
        Inventory result = inventoryRepository.findByProductIdandStoreId(inventory.getProduct().getId(), inventory.getStore().getId());
        if (result == null) return false;
        return true;
    }

    public boolean validateProduct(Product product) {
        Product result = productRepository.findByName(product.getName());
        if (result == null) return false;
        return true;
    }

    public boolean validateProductId(Long id){
        Product product = productRepository.findByProductId(id);
        if (product == null) return false;
        return true;
    }

    public Inventory getInventoryId(Inventory inventory){
        return inventoryRepository.findByProductIdandStoreId(inventory.getProduct().getId(), inventory.getStore().getId());
    }

}
