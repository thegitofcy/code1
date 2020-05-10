package com.cy.example.design.observer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * <p>具体主题(被观察者)</p>
 *
 * @author : cy
 */

public class ConcreteSubject implements Subject {
    
    private Vector<Observer> observers = new Vector<>();

    @Override
    public void attache(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void detacher(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    // 返回所有已登记的观察者 Enumeration对象
    public Enumeration<Observer> getObservers(){
        return observers.elements();
    }

    // 业务方法,  改变状态, 状态改变后, 会给观察者发送通知
    public void change(){
        this.notifyObservers();
    }
}
