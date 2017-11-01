package com.chale.thread.demo;

/**
 * Created by liangchaolei on 2016/7/15.
 */
public class DemoTest {

    private static  int i=0;
    private static StringBuffer sb1=new StringBuffer();
    private static StringBuffer sb2=new StringBuffer();
    public static void main(String[] args) throws InterruptedException {





        Thread t=new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    try {
                        sb2.notify();
                    } catch (Exception e) {
                    }

                    sb1.append(i++ +"");

                    if(i%3==0) {
                        try {
                            sb1.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                 }

            }
        });

        Thread t2=new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        sb1.notify();
                    } catch (Exception e) {
                    }
                    sb2.append(i++ +"");


                    if(i%2==0) {
                        try {
                            sb2.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        Thread t3=new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    System.out.println(sb1.toString());
                    System.out.println(sb2.toString());
                    try {
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        t.start();
        t2.start();
        t3.start();


    }
}
