/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.spark.examples.ipg;

/**
 *
 * @author tatian
 */
//复数类，用于傅里叶变换计算
public class ComplexNumber {
    public double real;//实部
    public double img;//虚部
    
    public  ComplexNumber(){
        real=0;
        img=0;
    }
    public  ComplexNumber(double r,double i){
        real=r;
        img=i;
    }
    //复数加
    public ComplexNumber add(ComplexNumber t){
        ComplexNumber tmp=new ComplexNumber();
        tmp.real=this.real+t.real;
        tmp.img=this.img+t.img;
        return tmp;
    }
    //复数减
    public ComplexNumber sub(ComplexNumber t){
        ComplexNumber tmp=new ComplexNumber();
        tmp.real=this.real-t.real;
        tmp.img=this.img-t.img;
        return tmp;
    }
    //复数乘
    public ComplexNumber mul(ComplexNumber t){
        ComplexNumber tmp=new ComplexNumber();
        tmp.real=this.real*t.real-this.img*t.img;
        tmp.img=this.real*t.img+this.img*t.real;
        return tmp;
    }

    @Override
    public String toString() {
        return "{" +
                "" + real +
                "," + img +
                '}';
    }

    //显示
    public void show(){
        System.out.println(""+real+" "+img);
    }
}
