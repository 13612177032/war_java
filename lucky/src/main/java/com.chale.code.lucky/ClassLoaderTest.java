package com.chale.code.lucky;

import org.junit.jupiter.api.Test;

import java.net.URL;

/**
 * Created by liangchaolei on 2017/7/10.
 */
public class ClassLoaderTest {



    public static void main(String[] args) {
 //        bootstrap();
//        extension();
//        system();

//         System.out.println(Child.n);
//        launcher();
//        path();
        new Father().print();
    }
    public static void bootstrap() {
        URL[] urls=sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
    }

    public static void extension(){
        System.out.println(System.getProperty("java.ext.dirs"));
        ClassLoader extensionClassloader=ClassLoader.getSystemClassLoader().getParent();
        System.out.println("the parent of extension classloader : "+extensionClassloader.getParent());
    }

    public static void system(){
        System.out.println(System.getProperty("java.ext.dirs"));
        ClassLoader system=ClassLoader.getSystemClassLoader();
        System.out.println("the parent of system classloader : "+system.getParent());
    }

    public static void launcher(){
        System.out.println("the Launcher's classloader is "+sun.misc.Launcher.getLauncher().getClass().getClassLoader());
    }
    public static void path(){
        System.out.println("the java.ext.dirs is "+System.getProperty("java.ext.dirs"));
        System.out.println("the java.class.path is "+System.getProperty("java.class.path"));
    }
}
class Super{
    public int a=2;
    public final static Object m = new Object();
    static{
        System.out.println(m);
        System.out.println("执行了super类静态语句块");
    }
}


class Father extends Super{
//    public static int m = 33;
public int a=3;
    static{
        System.out.println("执行了父类静态语句块");
    }

    public void print(){

        a=5;
        System.out.println(super.a);

        System.out.println(a);
    }
}
class Other{
    static {
        System.out.println("Other is init");
    }
}
class Child extends Father{

    public static int n=123123123;
    public final static Other o=new Other();

    static{
        n=1231;
        System.out.println("执行了子类静态语句块");
    }

}
