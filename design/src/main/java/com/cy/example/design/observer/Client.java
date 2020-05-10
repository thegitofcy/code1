package com.cy.example.design.observer;

/**
 * <p></p>
 *
 * @author : cy
 */

public class Client {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        Observer observer = new ConcreteObserver();

        subject.attache(observer);
        subject.change();
    }
}
