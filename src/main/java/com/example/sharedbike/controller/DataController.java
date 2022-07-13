package com.example.sharedbike.controller;

import com.example.sharedbike.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataController {

    final
    DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    //统计租用车辆会员人数
    @RequestMapping("/findVip")
    public List<Map<String,Object>> countVipNum(){
        return dataService.countVipNum();
    }
    @RequestMapping("/findVipByMonth")
    public List<Map<String,Object>> countVipNumByMonth(){
        return dataService.countVipNumByMonth();
    }
    @RequestMapping("/findVipBySeason")
    public List<Map<String,Object>> countVipNumBySeason(){
        return dataService.countVipNumBySeason();
    }
    //统计租车数量
    //每周每天租车人数0.1.2.3.4.5.6
    @RequestMapping("/findCountByWeekday")
    public List<Map<String,Object>> countPeopleNumByWeekday(){
        return dataService.countRentNumByWeekday();
    }
    //不同天气下的租车人数
    @RequestMapping("/findCountByWeather")
    public List<Map<String,Object>> countPeopleNumByWeather(){
        return dataService.countRentNumByWeather();
    }

    @RequestMapping("findCountByWeatherIndex")
    public List<Map<String,Object>> countPeopleNumByWeatherIndex(){
        return dataService.countRentNumByWeatherIndex();
    }

    @RequestMapping("findCountByWeatherIndexOnPage")
    public List<Map<String,Object>> findByPage(@RequestBody Map<String,Object> map){
        String pageNow = map.get("pageNow").toString();
        String pageSize = map.get("pageSize").toString();
        int page = 0;
        int size = 0;
        try{
            page = Integer.parseInt(pageNow);
            size = Integer.parseInt(pageSize);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return dataService.findByPage(page,size);
    }
}
