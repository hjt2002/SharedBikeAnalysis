package com.example.sharedbike.service;

import com.alibaba.fastjson.JSON;
import com.example.sharedbike.entity.WeatherDay;
import com.example.sharedbike.mapper.WeatherDayMapper;
import com.example.sharedbike.util.WeatherByDay;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WeatherDayService{

    final WeatherDayMapper weatherDayMapper;
    public WeatherDayService(WeatherDayMapper weatherDayMapper) {
        this.weatherDayMapper = weatherDayMapper;
    }

    public void addWeatherDay(String location){
        WeatherByDay weatherByDay = new WeatherByDay();
        List<Map<String,Object>> resultList = weatherByDay.getDailyWeather(location);

        //添加未来24小时的天气数据到数据库
        for(Map<String,Object> map : resultList){
            WeatherDay weatherDay = JSON.parseObject(JSON.toJSONString(map),WeatherDay.class);
            weatherDayMapper.add(weatherDay);
        }
    }
    public WeatherDay findByPk(String date,String location){
        return weatherDayMapper.findByPk(location,date);
    }
}
