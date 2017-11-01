package com.chale.sort.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liangchaolei on 2016/11/11.
 */
public class ThreadTest {
    private  static   AtomicInteger    count=  new AtomicInteger(0);
    private final  static int   threadNum=100;
    private final  static int   times=1000000;
    private static void inc(){
        count.incrementAndGet();
    }
    public static void main(String[] args) {
        List<Thread> ts=new ArrayList<Thread>();
        for (int i = 0; i < threadNum; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    for (int j = 0; j < times; j++) {
                        ThreadTest.inc();
                    }
                }
            });
            ts.add(t);
        }
        long time=System.currentTimeMillis();
        for (Thread t:ts) {
            t.start();
        }
        while (true) {
            int num = 0;
            for (Thread t : ts) {
                if(t.isAlive()) num++;
            }
            if(num==0) break;
            System.out.println("live thread :" +num);
            sleep(1000);
        }
        System.out.println("costTime:"+(System.currentTimeMillis()-time));
        System.out.println("you want num is " +(times*threadNum));
        System.out.println("the real num is " +(count.get()));
    }

    private static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

//    public   static int   count=0;
//    public   static int   syncCount=0;
//    public static AtomicInteger autoCount = new AtomicInteger(0);
//
//    public static void inc(){
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        count++;
//        syncInc();
//        autoCount.incrementAndGet();
//
//    }
//    private static synchronized void syncInc(){
//        syncCount++;
//    }
//    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 1000; i++) {
//            Thread t = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    ThreadTest.inc();
//                }
//            });
//            t.start();
//        }
//        Thread.sleep(1000);
//        System.out.println("运行结果:Counter.count=" + ThreadTest.count);
//        System.out.println("运行结果:Counter.syncCount=" + ThreadTest.syncCount);
//        System.out.println("运行结果:Counter.autoCount=" + ThreadTest.autoCount);
//    }