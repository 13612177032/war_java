package com.chale.wartermark.model;

import com.chale.wartermark.exception.WaterMarkException;
import com.chale.wartermark.util.ImageUtil;

import java.io.IOException;

/**
 * Created by liangchaolei on 2017/6/17.
 */
public class WaterMarkRequest {

    private final byte[] inputBytes;
    private String input;
    private String mark;

    public WaterMarkRequest(String input, String mark) throws WaterMarkException {
        this.input = input;
        this.mark = mark;
        try {
            inputBytes=ImageUtil.image2byte(input);
        } catch (Exception e) {
            throw new WaterMarkException("获取水印字节数组失败");
        }
    }
}
