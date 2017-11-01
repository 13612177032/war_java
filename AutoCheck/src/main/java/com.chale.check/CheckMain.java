package com.chale.check;

import org.codehaus.jackson.map.ObjectMapper;

import java.awt.*;
import java.net.URI;

/**
 * Created by liangchaolei on 2016/7/12.
 */
public class CheckMain {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper=new ObjectMapper();
        ClassLoader cl = objectMapper.getClass().getClassLoader();
        System.out.println("-------------------------------------------");
        System.out.println(cl);
        System.out.println(cl.getParent());
        if(cl.getParent()!=null){
            System.out.println(cl.getParent().getParent());
        }
        System.out.println("--------------------------------------------");
        try {
            objectMapper.writerWithDefaultPrettyPrinter();
            System.out.println("objectMapper.writerWithDefaultPrettyPrinter().success");
        } catch (Exception e) {
            System.out.println("objectMapper.writerWithDefaultPrettyPrinter().error");
            e.printStackTrace();
        }
    }



}
