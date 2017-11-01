package com.chale.java.base;

/**
 * Created by liangchaolei on 2016/6/22.
 */
public class HelloWorld {
    private static HelloWorld ourInstance = new HelloWorld();

    public static HelloWorld getInstance() {
        return ourInstance;
    }

    private HelloWorld() {
    }


    public static void main(String[] args) {
        System.out.println("111");
    }
}
