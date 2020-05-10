package com.cy.example.design.observer;

/**
 * <p>抽象主题(被观察者)</p>
 *
 * @author : cy
 */
public interface Subject {
    // 登记一个新的观察者(订阅者)
    public void attache(Observer obs);
    // 删除一个登记过观察者(订阅者)
    public void detacher(Observer obs);
    // 通知所有登记的观察者
    public void notifyObservers();
}
