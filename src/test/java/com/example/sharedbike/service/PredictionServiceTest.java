package com.example.sharedbike.service;

import com.example.sharedbike.entity.Prediction;
import com.example.sharedbike.entity.Weather;
import com.example.sharedbike.mapper.PredictionMapper;
import com.example.sharedbike.util.PythonInvoke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PredictionServiceTest {

    @Autowired
    WeatherService weatherService;
    @Autowired
    PredictionMapper predictionMapper;
    @Test
    void getPrediction() {
        //获取天气信息
        String datetime = "2022/07/11 16:00:00";
        String location = "beijing";
        //获取天气信息
        Weather weather = weatherService.findWeather(datetime,location);
        //进行预测
        PythonInvoke pythonInvoke = new PythonInvoke();
        pythonInvoke.invokePython(weather);
    }
}