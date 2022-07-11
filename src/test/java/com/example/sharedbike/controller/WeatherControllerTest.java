package com.example.sharedbike.controller;

import com.example.sharedbike.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WeatherControllerTest {

    @Autowired
    WeatherService weatherService;
    @Test
    void addWeather() {
//        String location = map.get("location").toString();
        String location = "beijing";
        weatherService.add(location);
    }
}