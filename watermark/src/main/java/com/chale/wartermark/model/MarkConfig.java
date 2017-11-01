package com.chale.wartermark.model;

/**
 * 水印设置
 * Created by liangchaolei on 2017/6/17.
 */
public class MarkConfig {

    private String fontSize;
    private String textLeft;
    private String textTop;


    public static MarkConfig getInitConfig(){
        return new MarkConfig("100","10","0");
    }

    public MarkConfig() {
    }

    public MarkConfig(String fontSize, String textLeft, String textTop) {

        this.fontSize = fontSize;
        this.textLeft = textLeft;
        this.textTop = textTop;
    }

    public String getFontSize() {

        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getTextLeft() {
        return textLeft;
    }

    public void setTextLeft(String textLeft) {
        this.textLeft = textLeft;
    }

    public String getTextTop() {
        return textTop;
    }

    public void setTextTop(String textTop) {
        this.textTop = textTop;
    }
}
