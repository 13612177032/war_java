package com.chale.chart.service;

import com.chale.chart.model.ChartRoom;

import java.util.List;

/**
 * Created by liangchaolei on 2017/10/18.
 */
public interface ChartRoomService {

    List<ChartRoom> queryRoom(String name);

    long in(Long userId, String name, String password);
}
