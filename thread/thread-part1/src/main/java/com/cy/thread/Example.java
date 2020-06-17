package com.cy.thread;

@FunctionalInterface
public interface Example {
    void run(int a);
    default void run2(){
        System.out.println("default");
    }
}


class AA{
    public static void main(String[] args) {
        Example example = a -> {
            System.out.println("this is run, param: " + a);
        };
        example.run(2);
        example.run2();
    }
}