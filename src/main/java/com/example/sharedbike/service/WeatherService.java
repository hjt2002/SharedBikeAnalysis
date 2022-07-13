package com.example.sharedbike.service;

import com.alibaba.fastjson.JSON;
import com.example.sharedbike.entity.User;
import com.example.sharedbike.entity.Weather;
import com.example.sharedbike.mapper.WeatherMapper;
import com.example.sharedbike.util.WeatherByHour;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WeatherService {
    final
    WeatherMapper weatherMapper;

    public WeatherService(WeatherMapper weatherMapper) {
        this.weatherMapper = weatherMapper;
    }

    public List<Map<String,Object>> add(String location){
        //获得未来24小时的天气信息
        WeatherByHour weatherByHour = new WeatherByHour();
        List<Map<String,Object>> resultList = weatherByHour.getHourlyWeather(location);

        //添加未来24小时的天气数据到数据库
        for(Map<String,Object> map : resultList){
            Weather weather = JSON.parseObject(JSON.toJSONString(map),Weather.class);
            weatherMapper.add(weather);
        }
        return resultList;
    }

    public Weather findWeather(String datetime,String location){
        return weatherMapper.findWeather(datetime,location);
    }

    public Weather findWeatherByDate(String date,String location){
        try{
            return weatherMapper.findWeatherByDate(date,location);
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
