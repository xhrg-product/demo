package com.github.xhrg.java.agent.jar.demo;

public class Main {

    //-javaagent:D:\temp\java-agent-jar-0.0.5.jar=params
    public static void main(String[] args) {
        new Person().sayHello();
    }
}