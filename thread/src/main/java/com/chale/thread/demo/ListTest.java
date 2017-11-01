package com.chale.thread.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ListTest {
    public static void main(String[] args) throws InterruptedException {
        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
        final Vector<String> list = new Vector<String>(a);
        Thread t = new Thread(
                new Runnable() {
                    int count = -1;
                    @Override
                    public void run(){
                        while (true) {
                            list.add(count++ + "");
                        }
                    }
        });
        t.setDaemon(true);
        t.start();
        Thread.currentThread().sleep(3);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
