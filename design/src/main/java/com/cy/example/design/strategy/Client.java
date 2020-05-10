package com.cy.example.design.strategy;

/**
 * <p></p>
 *
 * @author : cy
 */

public class Client {

    public static void main(String[] args) {
        Context contextA = new Context(new ConcreteStrategyA());
        contextA.contextInterface();

        Context contextB = new Context(new ConcreteStrategyB());
        contextB.contextInterface();
    }
}
