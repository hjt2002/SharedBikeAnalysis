package com.example.sharedbike.controller;

import com.example.sharedbike.entity.Weather;
import com.example.sharedbike.service.WeatherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public List<Map<String,Object>> addWeather(@RequestBody Map<String,Object>map){
        String location = map.get("location").toString();
        return weatherService.add(location);
    }

    //通过时间datetime格式和地址的天气信息
    @PostMapping("findWeather")
    public Weather findWeather(@RequestBody Map<String,Object> map){
        String datetime = map.get("datetime").toString();
        String location = map.get("location").toString();
        return weatherService.findWeather(datetime,location);
    }
    @PostMapping("findWeatherByDate")
    public Weather findWeatherByDate(@RequestBody Map<String,Object> map){
        String date = map.get("date").toString();
        date = date +  "%";
        String location = map.get("location").toString();
        return weatherService.findWeatherByDate(date,location);
    }
}
