package com.chale.chart.service.impl;

import com.chale.chart.dao.UserMapper;
import com.chale.chart.model.User;
import com.chale.chart.model.UserExample;
import com.chale.chart.service.UserService;
import com.chale.chart.util.ChaleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liangchaolei on 2017/10/18.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public long Login(String user, String password) {
        UserExample um=new UserExample();
        um.createCriteria().andNameEqualTo(user);
        List<User> userOld = userMapper.selectByExample(um);
        if(userOld==null || userOld.size()==0){
            User record=new User();
            record.setName(user);
            record.setPassword(password);
            userMapper.insertSelective(record);
            userOld = userMapper.selectByExample(um);
        }
        if(userOld==null || userOld.size()==0){
            throw new ChaleException("9999","用户查询失败");
        }
        if(!password.equals(userOld.get(0).getPassword())){
            throw new ChaleException("1001","用户登录失败");
        }
        return  userOld.get(0).getId();
    }
}
