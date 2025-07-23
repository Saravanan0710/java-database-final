package com.project.code.Controller;

import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.OrderItemRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    ServiceClass serviceClass;

    @PostMapping
    public Map<String, String> addProduct(@RequestBody Product product){
        Map<String,String> map = new HashMap<>();
        if(!serviceClass.validateProduct(product)){
            map.put("message", "Product already exists");
            return map;
        }
        
        try{
            productRepository.save(product);
            map.put("message", "Product added  successfully");
        } catch (DataIntegrityViolationException e){
            map.put("message", "SKU should be unique");
        }

        return map;
    }

    @GetMapping("/product/{id}")
    public Map<String, Object> getProductById(@PathVariable Long id){
        Map<String,Object> map = new HashMap<>();
        map.put("products", productRepository.findById(id));
        return map;
    }

    @PutMapping
    public Map<String, String> updateProduct(@RequestBody Product product){
        Map<String,String> map = new HashMap<>();
        try{
            productRepository.save(product);
            map.put("message", "Product updated successfully");
        } catch (Error e){
            map.put("message","error occured");
        }
        return map;
    }

    @GetMapping("/category/{name}/{category}")
    public Map<String,Object> filterByCategoryProduct(@PathVariable String name, @PathVariable String category){
        Map<String,Object> map = new HashMap<>();
        if(name.equals(null)){
            map.put("products", productRepository.findByCategory(category));
            return map;
        }

        if(category.equals(null)){
            map.put("products", productRepository.findProductBySubName(name));
            return map;
        }

        map.put("products", productRepository.findProductBySubNameAndCategory(name,category));
        return map;
    }

    @GetMapping
    public Map<String,Object> listProduct(){
        Map<String,Object> map = new HashMap<>();
        map.put("products",productRepository.findAll());
        return map;
    }

    @GetMapping("/filter/{category}/{storeId}")
    public Map<String,Object> getProductByCategoryAndStoreId(@PathVariable String category, @PathVariable Long storeId){
        Map<String,Object> map = new HashMap<>();
        map.put("product", productRepository.findByCategoryAndStoreId(storeId,category));
        return map;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id){
        Map<String,String> map = new HashMap<>();

        if(!serviceClass.validateProductId(id)){
            map.put("message", "Product id not present in db");
            return map;
        }

        inventoryRepository.deleteByProductId(id);
        productRepository.deleteById(id);
        orderItemRepository.deleteById(id);

        map.put("message", "Product deleted successfully with id: " + id);
        return map;
    }

    @GetMapping("/searchProduct/{name}")
    Map<String,Object> searchProduct(@PathVariable String name){
        Map<String,Object> map = new HashMap<>();
        map.put("products",productRepository.findProductBySubName(name));
        return map;
    }
    
}
