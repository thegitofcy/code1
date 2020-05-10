package com.cy.example.design.strategy;

/**
 * <p>上下文</p>
 *
 * @author : cy
 */

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void contextInterface(){
        this.strategy.strategyInterface();
    }
}
