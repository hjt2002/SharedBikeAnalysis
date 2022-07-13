package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.WeatherDay;
import com.example.sharedbike.util.WeatherByDay;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface WeatherDayMapper {

    void add (WeatherDay weatherDay);
    WeatherDay findByPk(String location, String date);
}
