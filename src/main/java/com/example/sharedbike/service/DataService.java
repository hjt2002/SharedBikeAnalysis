package com.example.sharedbike.service;

import com.example.sharedbike.mapper.DataMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataService {

    final
    DataMapper dataMapper;

    public DataService(DataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }

    public List<Map<String,Object>> countVipNum(){
        return dataMapper.countVipNum();
    }
    public List<Map<String,Integer>> countVipNumByMonth(){
        return dataMapper.countVipNumByMonth();
    }
    public List<Map<String,Integer>> countVipNumBySeason(){
        return dataMapper.countVipNumBySeason();
    }
    public List<Map<String,Integer>> countRentNumByWeekday(){
        return dataMapper.countRentNumByWeekday();
    }
    public List<Map<String,Integer>> countRentNumByWeather(){
        return dataMapper.countRentNumByWeather();
    }
    public List<Map<String,Object>> countRentNumByWeatherIndex(){
        return dataMapper.countRentNumByWeatherIndex();
    }
}
