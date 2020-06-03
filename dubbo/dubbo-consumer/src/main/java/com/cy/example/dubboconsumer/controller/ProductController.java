package com.cy.example.dubboconsumer.controller;


import com.cy.example.dubboconsumer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String getCost(int a) {
        return "该产品总共消费 ："+productService.getCost(a);
    }
}
