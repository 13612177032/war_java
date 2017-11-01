package com.chale.chart.service;

/**
 * Created by liangchaolei on 2017/10/18.
 */
public interface MessageService {

    void push(long pusher,long room,String message);


}
