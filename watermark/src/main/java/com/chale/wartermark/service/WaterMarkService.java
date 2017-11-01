package com.chale.wartermark.service;

import com.chale.wartermark.exception.WaterMarkException;

/**
 * Created by liangchaolei on 2017/6/17.
 */
public interface WaterMarkService {

    void parseWatermarkForRedScale(String in, String out);

    void joinWaterMark(String input, String mark, String output) throws WaterMarkException;

}
