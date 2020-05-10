package com.cy.example.design.strategy;

/**
 * <p>具体策略</p>
 *
 * @author : cy
 */

public class ConcreteStrategyA extends Strategy {

    @Override
    public void strategyInterface() {
        System.out.println("具体策略 A");
    }
}
