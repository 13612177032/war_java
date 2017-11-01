package com.chale.thread.demo;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by liangchaolei on 2016/8/2.
 */
public class GetCookie {

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        HttpHost httpHost = new HttpHost("localhost");
        HttpGet httpGet = new HttpGet("/https/");

        HttpResponse response = httpClient.execute(httpHost,httpGet);

        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
            //请求成功
            //取得请求内容
            HttpEntity entity = response.getEntity();
            //显示内容
            if (entity != null) {
                // 显示结果
                System.out.println(EntityUtils.toString(entity,"utf-8"));
            }
        }
        //模拟写cookie
        httpGet = new HttpGet("/https/index.jsp?cookie=write");
        response = httpClient.execute(httpHost,httpGet);
        FileWriter fw = new FileWriter("C:/cookie.txt");
        //读取cookie并保存文件
        List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
                fw.write(cookies.get(i).toString()+"\r\n");
            }
        }
        fw.close();

        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
            //请求成功
            //取得请求内容
            HttpEntity entity = response.getEntity();
            //显示内容
            if (entity != null) {
                // 显示结果
                System.out.println(EntityUtils.toString(entity,"utf-8"));
            }
        }

    }
}
