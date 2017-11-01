package com.chale.chart.util;

/**
 * Created by liangchaolei on 2017/10/18.
 */
public class ChaleException extends RuntimeException{

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChaleException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ChaleException(String code, String message,Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }
}
