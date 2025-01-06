package com.example.sayHello;

public class HelloTarget implements Hello {
    public String sayHello(String name) {
        return "hello " + name;
    }

    public String sayHi(String name) {
        return "hi " + name;
    }

    public String sayThankYou(String name) {
        return "thank you " + name;
    }
}
