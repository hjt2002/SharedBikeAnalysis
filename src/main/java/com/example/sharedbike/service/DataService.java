package com.example.sharedbike.service;

import com.example.sharedbike.mapper.DataMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DataService {

    final
    DataMapper dataMapper;

    public DataService(DataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }

    public List<Map<String,Object>> countVipNum(){
        return dataMapper.countVipNum();
    }
    public List<Map<String,Object>> countVipNumByMonth(){
        return dataMapper.countVipNumByMonth();
    }
    public List<Map<String,Object>> countVipNumBySeason(){
        return dataMapper.countVipNumBySeason();
    }
    public List<Map<String,Object>> countRentNumByWeekday(){
        return dataMapper.countRentNumByWeekday();
    }
    public List<Map<String,Object>> countRentNumByWeather(){
        return dataMapper.countRentNumByWeather();
    }
    public List<Map<String,Object>> countRentNumByWeatherIndex(){
        return dataMapper.countRentNumByWeatherIndex();
    }

    public List<Map<String,Object>> findByPage(Integer pageNow, Integer rows) {
        int start = (pageNow-1)*rows;
        return dataMapper.findByPage(start,rows);
    }
}
