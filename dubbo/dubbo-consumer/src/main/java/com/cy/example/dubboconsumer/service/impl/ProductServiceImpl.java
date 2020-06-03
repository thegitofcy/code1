package com.cy.example.dubboconsumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cy.example.dubboapi.service.CostService;
import com.cy.example.dubboconsumer.service.ProductService;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    @Reference
    private CostService costService;

    @Override
    public int getCost(int a) {
        Integer result = costService.add(a);
        return result;
    }
}
