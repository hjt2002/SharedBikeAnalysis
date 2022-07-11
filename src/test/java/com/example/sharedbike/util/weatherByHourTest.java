package com.example.sharedbike.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class weatherByHourTest {

    @Test
    void getHourlyWeather() {
//        Map<String,Object> mapHours = new HashMap<>();
        WeatherByHour weatherByHour = new WeatherByHour();
//        mapHours = weatherByHour.getHourlyWeather("beijing");
        List<Map<String,Object>> list = weatherByHour.getHourlyWeather("beijing");
//        System.out.println(list);
        // 遍历输出
        for (Map<String,Object> map:list
        ) {
            for (String str:map.keySet()
            ) {
                System.out.print("key:"+str+"\t");
                System.out.println("value:"+map.get(str));
            }
            System.out.println("-------");
        }
//        System.out.println(mapHours);
//        for(Map.Entry<String, Object> entry : mapHours.entrySet()){
//            System.out.println("Key = "+entry.getKey()+",value="+entry.getValue());
//        }


//        String mapJson = JSON.toJSONString(mapHours);
//        System.out.println(mapJson);
    }
}