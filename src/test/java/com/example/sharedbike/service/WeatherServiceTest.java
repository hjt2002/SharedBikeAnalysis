package com.example.sharedbike.service;

import com.alibaba.fastjson.JSON;
import com.example.sharedbike.entity.Weather;
import com.example.sharedbike.mapper.WeatherMapper;
import com.example.sharedbike.util.WeatherByHour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WeatherServiceTest {

    @Autowired
    WeatherService weatherService;
    @Test
    void findWeather() {
        String datetime = "2022/07/11 16:00:00";
        String location = "beijing";
        Weather weather = weatherService.findWeather(datetime,location);
        System.out.println(weather);
    }

    @Autowired
    WeatherMapper weatherMapper;
    @Test
    void add() {
        String location = "beijing";
        //获得未来24小时的天气信息
        WeatherByHour weatherByHour = new WeatherByHour();
        List<Map<String,Object>> resultList = weatherByHour.getHourlyWeather(location);

        //添加未来24小时的天气数据到数据库
        for(Map<String,Object> map : resultList){
            Weather weather = JSON.parseObject(JSON.toJSONString(map),Weather.class);
            weatherMapper.add(weather);
        }
    }

    @Test
    void findWeatherByDate() {
        String location = "beijing";
        String date = "2022/07/11" + "%";
        weatherMapper.findWeatherByDate(date,location);
    }
}