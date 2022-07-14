package com.example.sharedbike.service;

import com.alibaba.fastjson.JSON;
import com.example.sharedbike.entity.Prediction;
import com.example.sharedbike.entity.Weather;
import com.example.sharedbike.mapper.PredictionMapper;
import com.example.sharedbike.util.PythonInvoke;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PredictionService {


    final
    WeatherService weatherService;

    public PredictionService(WeatherService weatherService, PredictionMapper predictionMapper) {
        this.weatherService = weatherService;
        this.predictionMapper = predictionMapper;
    }

    final
    PredictionMapper predictionMapper;


    //获取预测
    public Prediction getPrediction(String location,String datetime){
        //获取指定时间段的预测
        if(predictionMapper.find(datetime,location) != null){
            return predictionMapper.find(datetime,location);
        }else {
            return null;
        }
    }

    //获得预测
    public boolean updatePrediction(String location){
        //更新未来24小时的预测结果

        //更新未来24小时天气信息
        List<Map<String,Object>>list = weatherService.add(location);
//        //获取起始的天气数据
//        Map <String,Object> map = list.get(0);
//        Weather weather = JSON.parseObject(JSON.toJSONString(map),Weather.class);
//        //调用Python脚本
        PythonInvoke pythonInvoke = new PythonInvoke();
        return pythonInvoke.invokePython(location);

    }

    //获得建议
    public Map<String,String> getAdvice(String datetime,String location){
        Map<String,String>map = new HashMap<>();
        //查询该预测结果是否存在
        if(predictionMapper.find(datetime,location) != null){
            //存在该预测结果
            //获取该预测结果的天气信息
            Weather weather = weatherService.findWeather(datetime,location);
            //根据天气信息返回对应的建议
            if(weather.getWorkingday() == 1){
                //是工作日
                String firstAdvice = "早高峰7~9点 晚高峰16~19点 单车需求量增大、租用量达到峰值" +
                        "在该时段宜适当增大单车投放、单车调度也主要集中在该时段。";
                map.put("first",firstAdvice);
            } else if (weather.getWorkingday() == 0) {
                //非工作日
                String firstAdvice = "单车租用数量在6点~11点一直呈现上升趋势，11点~17点单车需求量为全天最高，17点后单车租用量呈现下降趋势在该时段宜适当减少单车投放";
                map.put("first",firstAdvice);
            }

            if(weather.getSeason() == 3){
                //当前季节为秋季
                String secondAdvice = "人们偏向于秋季骑单车出行" +
                        "建议在秋季增大单车储备以增大单车投放";
                map.put("second",secondAdvice);
            } else if (weather.getSeason() != 3) {
                map.put("second","null");
            }

            String thirdAdvice;
            if(weather.getWeather() == 1){
                //晴天
                thirdAdvice = "天气状况为晴天至多云范围内时，单车租用量最高";
            }else {
                thirdAdvice = "在较恶劣天气状况下例如暴雨天气 骑单车出行会淋湿且危险指数很高，人们偏向于坐公交、地铁或者打车出行" +
                        "建议结合我们网页获取的天气预报以及模型预测结果来决定单车投放量";
            }
            map.put("third",thirdAdvice);

            if ( 15 <= weather.getTemp() && weather.getTemp() <= 31){
                String forthAdvice = "室外温度在15~31℃范围内单车租用数量较高，在该温度范围内适宜人们骑单车出行,建议增大投放量";
                map.put("forth",forthAdvice);
            }else {
                map.put("forth","null");
            }

            if( weather.getWindspeed() > 50){
                String fifthAdvice = "风速过高时（一般是指风速在50~61 km/h）会令全树摇动，人们迎风步行感觉不便，伴随极端天气出现，此时骑单车出行危险指数很高，建议减少单车投放";
                map.put("fifth",fifthAdvice);
            }else {
                map.put("fifth","null");
            }

            String sixthAdvice;
            if( weather.getHumidity() >= 20 && weather.getHumidity() <= 60){
                sixthAdvice = "当湿度范围为20~60%时 单车租用数量和湿度是正相关的，当天气预测湿度在该范围内时湿度越大人们越偏向于骑单车出行，建议增大单车投放";
            }else {
                sixthAdvice = "当湿度范围过高时因湿度过大令人感到不适，建议减少单车投放";
            }
            map.put("sixth",sixthAdvice);

        }else{
            //不存在该时间的预测结果
            map.put("result","nullPrediction");
        }
        return map;
    }

}
