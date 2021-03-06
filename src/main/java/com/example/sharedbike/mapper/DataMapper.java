package com.example.sharedbike.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {

    //统计已经注册和未注册的总人数
    public List<Map<String,Object>> countVipNum();
    //按照月份统计已经注册和未注册总人数
    List<Map<String,Object>> countVipNumByMonth();
    //按照季度统计已经注册和未注册的人数
    List<Map<String,Object>> countVipNumBySeason();
    //统计每周每天的租用车辆人数
    List<Map<String,Object>> countRentNumByWeekday();
    //统计天气条件（1.晴 2.雨 3.雪）
    List<Map<String,Object>> countRentNumByWeather();
    //统计天气指标下的对应租用单车数量
    List<Map<String,Object>> countRentNumByWeatherIndex();

    List<Map<String,Object>> findByPage(@Param("start")Integer start,@Param("rows") Integer rows);

}
