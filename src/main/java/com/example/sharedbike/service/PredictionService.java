package com.example.sharedbike.service;

import com.example.sharedbike.entity.Prediction;
import com.example.sharedbike.entity.Weather;
import com.example.sharedbike.mapper.PredictionMapper;
import com.example.sharedbike.util.PythonInvoke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    //获得预测
    public Prediction getPrediction(String location,String datetime){
        try {
            if (predictionMapper.find(datetime, location) != null) {
                //数据库中已经存在该记录
                return predictionMapper.find(datetime, location);

            } else {
                //获取天气信息
                Weather weather = weatherService.findWeather(datetime, location);
                //进行预测
                PythonInvoke pythonInvoke = new PythonInvoke();
                Prediction prediction = pythonInvoke.invokePython(weather);
                //将预测结果写入数据库中
                predictionMapper.add(prediction);
                return prediction;
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    //查询某条预测
    public Prediction find(String location,String datetime){
        return predictionMapper.find(datetime,location);
    }

}
