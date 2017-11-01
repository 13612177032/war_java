package com.chale.check;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liangchaolei on 2016/8/10.
 */
public class TestList

{

    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd0000");
        System.out.println(sdf.format(new Date()));
    }
}
