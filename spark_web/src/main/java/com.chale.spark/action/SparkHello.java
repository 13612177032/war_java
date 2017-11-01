package com.chale.spark.action;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangchaolei on 2016/7/11.
 */
public class SparkHello {

    static final String USER = "wyc";

    public static void main(String[] args) throws Exception {
        System.setProperty("user.name", USER); // 设置访问Spark使用的用户名
        System.setProperty("HADOOP_USER_NAME", USER); // 设置访问Hadoop使用的用户名
        Map envs = new HashMap();
        envs.put("HADOOP_USER_NAME", USER); // 为Spark环境中服务于本App的各个Executor程序设置访问Hadoop使用的用户名

        System.setProperty("spark.executor.memory", "512m"); // 为Spark环境中服务于本App的各个Executor程序设置使用内存量的上限

        // 以下构造sc对象的构造方法各参数意义依次为：
        //   Spark Master的地址；
        //   App的名称；
        //   Spark Worker的部署位置；
        //   需要提供给本App的各个Executor程序下载的jar包的路径列表，这些jar包将出现在Executor程序的类路径中；
        //   传递给本App的各个Executor程序的环境信息。

        JavaSparkContext sc = new JavaSparkContext("spark://node01:7077", "Spark App 0", "/home/wyc/spark", new String[0], envs);

        String file = "hdfs://node01:8020/user/wyc/a.txt";
        JavaRDD data = sc.textFile(file, 4).cache();
        System.out.println(data.count());
    }

}
