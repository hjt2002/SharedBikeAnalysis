package com.example.sharedbike.controller;

import com.example.sharedbike.entity.Weather;
import com.example.sharedbike.service.WeatherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    final
    WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //通过地址访问24小时之内的天气数据
    @PostMapping("/addWeather")
    public void addWeather(@RequestBody Map<String,Object>map){

        String location = map.get("location").toString();
        weatherService.add(location);

    }
}
