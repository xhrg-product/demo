package com.github.xhrg.java.agent.jar.demo;

public class Person {

    public void sayHello() {
        try {
            Thread.sleep(1234);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
    }
}
