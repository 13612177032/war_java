package com.chale.chart.service.impl;

import com.chale.chart.dao.ChartRoomMapper;
import com.chale.chart.model.ChartRoom;
import com.chale.chart.model.ChartRoomExample;
import com.chale.chart.model.User;
import com.chale.chart.model.UserExample;
import com.chale.chart.service.ChartRoomService;
import com.chale.chart.util.ChaleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liangchaolei on 2017/10/18.
 */
@Service
public class ChartRoomServiceImpl implements ChartRoomService{

    @Autowired
    private ChartRoomMapper chartRoomMapper;

    @Override
    public List<ChartRoom> queryRoom(String name) {
        ChartRoomExample ex=new ChartRoomExample();
        ex.createCriteria().andNameLike(name+"%");
        ex.setOrderByClause("create_time desc");

        return chartRoomMapper.selectByExample(ex);
    }

    @Override
    public long in(Long userId, String name, String password) {
        ChartRoomExample um=new ChartRoomExample();
        um.createCriteria().andNameEqualTo(name);
        List<ChartRoom> chartRoom = chartRoomMapper.selectByExample(um);
        if(chartRoom==null || chartRoom.size()==0){
            ChartRoom record=new ChartRoom();
            record.setName(name);
            record.setPassword(password);
            record.setUserId(userId);
            chartRoomMapper.insertSelective(record);
            chartRoom = chartRoomMapper.selectByExample(um);
        }
        if(chartRoom==null || chartRoom.size()==0){
            throw new ChaleException("9999","房间查询失败");
        }
        if(!password.equals(chartRoom.get(0).getPassword())){
            throw new ChaleException("1001","房间进入失败：密码错误");
        }
        return  chartRoom.get(0).getId();
    }
}
