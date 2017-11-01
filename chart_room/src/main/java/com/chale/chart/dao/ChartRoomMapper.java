package com.chale.chart.dao;

import com.chale.chart.model.ChartRoom;
import com.chale.chart.model.ChartRoomExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChartRoomMapper {
    long countByExample(ChartRoomExample example);

    int deleteByExample(ChartRoomExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChartRoom record);

    int insertSelective(ChartRoom record);

    List<ChartRoom> selectByExample(ChartRoomExample example);

    ChartRoom selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChartRoom record, @Param("example") ChartRoomExample example);

    int updateByExample(@Param("record") ChartRoom record, @Param("example") ChartRoomExample example);

    int updateByPrimaryKeySelective(ChartRoom record);

    int updateByPrimaryKey(ChartRoom record);
}