package com.chale.check;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liangchaolei on 2017/6/13.
 */
public class MapTest {


    public static void main(String[] args) {
        final Map<String, Integer> map=new ConcurrentHashMap<String, Integer>();
        final AtomicInteger atomicInteger=new AtomicInteger(1);

        map.put("1",atomicInteger.incrementAndGet());
        map.put("2",atomicInteger.incrementAndGet());
        map.put("3",atomicInteger.incrementAndGet());
        map.put("4",atomicInteger.incrementAndGet());
        map.put("5",atomicInteger.incrementAndGet());
        map.put("6",atomicInteger.incrementAndGet());
        map.put("7",atomicInteger.incrementAndGet());

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=7;
                while (true) {
//                    atomicInteger.incrementAndGet();
                    System.out.println("romove."+i);
                    map.remove(String.valueOf(i));
                    i--;

//                    map.put(atomicInteger.toString(), atomicInteger.incrementAndGet());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<String, Integer> e:map.entrySet()) {
                    System.out.println("map."+e.getKey());
                    try {
                        Thread.sleep(999);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();

    }


}
