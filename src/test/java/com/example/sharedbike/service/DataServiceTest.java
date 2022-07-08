package com.example.sharedbike.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DataServiceTest {

    @Autowired
    DataService dataService;

    @Test
    void countRentNumByWeatherIndex() {
        dataService.countRentNumByWeatherIndex();
    }

    @Test
    void countVipNum() {
        dataService.countVipNum();
    }

    @Test
    void countVipNumByMonth() {
    }

    @Test
    void countVipNumBySeason() {
    }

    @Test
    void countRentNumByWeekday() {
    }

    @Test
    void countRentNumByWeather() {
    }

}