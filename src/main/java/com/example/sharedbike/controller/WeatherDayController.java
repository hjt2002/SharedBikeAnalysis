package com.example.sharedbike.controller;

import com.example.sharedbike.entity.WeatherDay;
import com.example.sharedbike.service.WeatherDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/weatherDay")
public class WeatherDayController {

//    @GetMapping("/add")
//    public String add(@RequestBody Map<String,Object> map){
//        String location = map.get("location").toString();
//        int result =
//
//    }
    @Autowired
    WeatherDayService weatherDayService;

    @PostMapping("/findByPk")
    public WeatherDay findByPk(@RequestBody Map<String,Object> map){
        String location = map.get("location").toString();
        String date = map.get("date").toString();

        WeatherDay weatherDay = weatherDayService.findByPk(date,location);
        try{
            if (weatherDay != null){
                //数据库存在该天气信息
                return weatherDay;

            }else {
                //数据库不存在该天天气信息
                //更新数据库
                weatherDayService.addWeatherDay(location);
                //重新查询
                return weatherDayService.findByPk(date,location);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
