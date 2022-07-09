package com.example.sharedbike.service;

import com.example.sharedbike.mapper.WeatherMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WeatherService {
    final
    WeatherMapper weatherMapper;

    public WeatherService(WeatherMapper weatherMapper) {
        this.weatherMapper = weatherMapper;
    }
}
