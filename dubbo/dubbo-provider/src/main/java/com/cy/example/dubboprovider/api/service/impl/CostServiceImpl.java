package com.cy.example.dubboprovider.api.service.impl;

import com.cy.example.dubboapi.service.CostService;

public class CostServiceImpl implements CostService {

    private final Integer totalCost = 0;

    @Override
    public Integer add(int cost) {
        return totalCost + cost;
    }
}
