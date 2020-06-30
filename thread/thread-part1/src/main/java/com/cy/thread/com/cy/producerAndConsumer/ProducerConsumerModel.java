package com.cy.thread.com.cy.producerAndConsumer;

import java.util.LinkedList;

/**
 * @program: thread
 * @description: 基于 wait 和 notify 实现生产者消费者模式
 * @author: cy
 */
public class ProducerConsumerModel {

    public static void main(String[] args) {
        EventStore eventStore = new EventStore();
        Runnable producer = new Producer(eventStore);
        Runnable consumer = new Consumer(eventStore);
        new Thread(producer).start();
        new Thread(consumer).start();
    }

}

// 存放数据媒介
class EventStore {
    private int maxSize;
    private LinkedList<Object> store;

    public EventStore() {
        this.maxSize = 10;
        this.store = new LinkedList<>();
    }

    // 放入数据
    public synchronized void put() {
        while (store.size() ==maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        store.add(new Object());
        System.out.println("store has " + store.size() + " 个数据");
        notify();   // 每一次放入数据都会通知
    }

    // 拿出数据
    public synchronized void take() {
        while (store.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("拿到了 " + store.poll() + ", 现在还有 " + store.size() + " 个数据");
        notify(); // 每次拿出数据都会通知
    }
}

// 生产者
class Producer implements Runnable {
    private EventStore store;

    public Producer(EventStore store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            store.put();
        }
    }
}

// 消费者
class Consumer implements Runnable {

    private EventStore store;

    public Consumer(EventStore store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            store.take();
        }
    }
}