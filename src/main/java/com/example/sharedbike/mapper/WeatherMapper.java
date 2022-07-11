package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Weather;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherMapper {
    public void add(Weather weather);
}
