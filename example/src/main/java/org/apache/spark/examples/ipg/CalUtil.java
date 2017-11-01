package org.apache.spark.examples.ipg;

/**
 * Created by liangchaolei on 2017/5/26.
 */
public class CalUtil {

    public static int getTimes(int m){
        int Mr,Nr,M;//迭代次数
        for(Mr=1; !(Math.pow(2, Mr-1)<=m&&(M= (int) Math.pow(2, Mr))>m); Mr++)
            ;
        //扩展后行数
        return M;
    }

    public static void show(ComplexNumber [][] input){
        for (int i = 0; i <input.length ; i++) {
            for (int j=0;j<input[i].length;j++) {
                System.out.print(input[i][j].toString());
            }
            System.out.println("\n");
        }
    }
    public static void main(String[] args) {
        System.out.println(getTimes(17));
    }
}
