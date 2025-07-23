package com.project.code.Controller;

import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OrderService orderService;

    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store){
        Store savedStore = storeRepository.save(store);
        Map<String,String> map = new HashMap<>();
        map.put("message","Store added successfully with id: "+savedStore.getId());
        return map;
    }

    @GetMapping("validate/{storeId}")
    public boolean validateStore(@PathVariable("storeId") Long storeId){
        Store store = storeRepository.findByStoreId(storeId);
        return store != null;
    }

    @PostMapping("/placeOrder")
    public Map<String,String> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequestDTO){
        Map<String,String> map = new HashMap<>();
        try {
            orderService.saveOrder(placeOrderRequestDTO);
            map.put("message","Place order added successfully");
        } catch(Error e) {
            map.put("message","Error: " + e);
        }
        return map;
    }

}
