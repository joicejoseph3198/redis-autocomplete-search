package com.example.redis_autocomplete.controller;

import com.example.redis_autocomplete.dto.AutoCompleteSuggestionResponse;
import com.example.redis_autocomplete.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/createProducts")
    public void addProduct(){
        productService.generateSampleProducts();
    }

    @GetMapping(value = "/autocomplete")
    public Map<String,List<AutoCompleteSuggestionResponse>> search(@RequestParam(name = "searchTerm") String searchTerm){
        return productService.getAutocompleteSuggestions(searchTerm);
    }


}
