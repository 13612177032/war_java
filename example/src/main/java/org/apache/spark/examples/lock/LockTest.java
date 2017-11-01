package org.apache.spark.examples.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liangchaolei on 2017/1/22.
 */
public class LockTest {
    private static final ReentrantLock lock1=new ReentrantLock();
    private static final ReentrantLock lock2=new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        final Student xiaoming=new Student("xiaoming");
        final Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                runS(xiaoming,lock1);
            }
        });
        t1.start();
        final Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                runS(xiaoming,lock1);
            }
        });
        lock1.newCondition();
        t2.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                t2.interrupt();
            }
        }).start();


    }

    private static void runS(Student s,ReentrantLock lock) {
        try {
            lock.lockInterruptibly();

        try {
                s.run();
                Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        } catch (InterruptedException e) {
            System.out.println("have been lockInterruptibly");
        }
    }

}

class Student{

    public Student(String name) {
        this.name = name;
    }
    String name;
    int i=0;

    public void run(){
        i++;
        System.out.println(name+".run."+i+"."+System.currentTimeMillis()/1000);
    }
}
