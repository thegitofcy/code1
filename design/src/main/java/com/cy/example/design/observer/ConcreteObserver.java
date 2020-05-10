package com.cy.example.design.observer;

/**
 * <p>具体观察者</p>
 *
 * @author : cy
 */

public class ConcreteObserver implements Observer {
    @Override
    public void update() {
        System.out.println("收到通知, 进行操作");
    }
}
