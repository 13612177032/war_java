package org.apache.spark.examples.ipg;

import java.util.Arrays;

/**
 * Created by liangchaolei on 2017/5/25.
 */
public class FFT {
    public static ComplexNumber[] fft(ComplexNumber[] c) {
//        double pr[], double pi[], double fr[], double fi[];
        int N = c.length;
        ComplexNumber [] res=new ComplexNumber[N];
         for (int i = 0; i < N; i++) {
            double fr = 0;
            double fi = 0;
            for (int j = 0; j < N; j++) {
                fr += c[j].real * Math.cos(2 * Math.PI * i * j / N) + c[j].img
                        * Math.sin(2 * Math.PI * i * j / N);
                fi += (-c[j].real * Math.sin(2 * Math.PI * i * j / N)) + c[j].img
                        * Math.cos(2 * Math.PI * i * j / N);
            }
            res[i]=new ComplexNumber();
            res[i].real=fr;
            res[i].img=fi;
        }
         return res;
    }

    public static ComplexNumber[] ifft(ComplexNumber[] c  ) {
        int N = c.length;
        ComplexNumber [] res=new ComplexNumber[N];

        for (int i = 0; i < N; i++) {
            double pr = 0;
            double pi = 0;
            for (int j = 0; j < N; j++) {
                pr += c[j].real * Math.cos(2 * Math.PI * i * j / N) / N - c[j].img
                        * Math.sin(2 * Math.PI * i * j / N) / N;
                pi += c[j].real * Math.sin(2 * Math.PI * i * j / N) / N + c[j].img
                        * Math.cos(2 * Math.PI * i * j / N) / N;
            }
            res[i]=new ComplexNumber();
            res[i].real=pr;
            res[i].img=pi;
        }
         return res;

    }

    public static int greater2p2(int n) {
        for (int i = 1;; i++) {
            if (n < Math.pow(2, i)) {
                return (int) Math.pow(2, i);
            }
        }
    }

    public static void main(String[] args) {
        double pr[] = { 1, 2, 3, 4, 5 };
        double pi[] = { 0, 0, 0, 0, 0 };

        int KK = greater2p2(pr.length);
        double pr2[] = new double[KK];
        double pi2[] = new double[KK];
        double fr2[] = new double[KK];
        double fi2[] = new double[KK];
        for (int i = 0; i < KK; i++) {
            pr2[i] = 0;
            pi2[i] = 0;
            if (i < pr.length) {
                pr2[i] = pr[i];
                pi2[i] = pi[i];
            }

        }

        ComplexNumber[] complexNumber=new ComplexNumber[10];
        for (int i = 0; i < 10; i++) {
            complexNumber[i]=new ComplexNumber(i,10-i);
        }
        System.out.println(Arrays.toString(complexNumber));
        ComplexNumber[] res = fft(complexNumber);
        System.out.println(Arrays.toString(res));
        System.out.println(Arrays.toString(ifft(res)));
    }

}
