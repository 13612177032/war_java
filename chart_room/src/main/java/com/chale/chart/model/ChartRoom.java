package com.chale.chart.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartRoom {


    public static void main(String[] args) throws ClassNotFoundException {

        String source="[{\"id\":111,\"name\":\"xxxxx\"},{\"id\":222,\"name\":\"SSS\"},{\"id\":333,\"name\":\"yyy\"}]";
        String config="com.chale.chart.model.ChartRoom";
        boolean isArray=true;



        Object  o=JSONObject.parseObject(source, new TypeReference<List<ChartRoom>>(){});

        System.out.println(o);
    }

    private Long id;


    private String name;

    private String password;

    private Long userId;

    private Date createTime;

    private Integer userNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }
}