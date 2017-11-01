package com.test;

/**
 * Created by liangchaolei on 2017/8/15.
 */
public class JvmTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
    }
}
