package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Weather;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeatherMapper {
    void add(Weather weather);
    Weather findWeather(String datetime,String location);
}
