package com.example.sharedbike.service;

import com.alibaba.fastjson.JSON;
import com.example.sharedbike.entity.Weather;
import com.example.sharedbike.util.WeatherByHour;
import org.hibernate.boot.model.source.spi.SingularAttributeSourceToOne;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    @Test
    void add() {
        String location = "beijing";
        //获得未来24小时的天气信息
        WeatherByHour weatherByHour = new WeatherByHour();
        List<Map<String,Object>> resultList = weatherByHour.getHourlyWeather(location);
        System.out.println(resultList.get(0));
//        //添加未来24小时的天气数据到数据库
//        for(Map<String,Object> map : resultList){
////            Weather weather = JSON.parseObject(JSON.toJSONString(map),Weather.class);
////            weatherMapper.add(weather);
//            for (String s : map.keySet()) {
//                System.out.println("key:" + s + " " + "values:"  +map.get(s));
//            }
//            System.out.println("----");
//        }
    }
}