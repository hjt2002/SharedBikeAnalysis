package com.example.sharedbike.controller;

import com.example.sharedbike.entity.Prediction;
import com.example.sharedbike.service.PredictionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/prediction")
public class PredictionController {


    final
    PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }


    @PostMapping("updateByLocation")
    public boolean update(@RequestBody Map<String,Object> map){
        String location = map.get("location").toString();
        return predictionService.updatePrediction(location);
    }

    @PostMapping("/getPrediction")
    public Prediction getPrediction(@RequestBody Map<String,Object> map){
        String datetime = map.get("datetime").toString();
        String location = map.get("location").toString();
        return predictionService.getPrediction(location,datetime);
    }
    @PostMapping("/getAdvice")
    public Map<String,String> getAdvice(@RequestBody Map<String,Object>map){
        String datetime = map.get("datetime").toString();
        String location = map.get("location").toString();
        return predictionService.getAdvice(datetime,location);
    }
}
