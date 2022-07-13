package com.example.sharedbike.controller;

import com.example.sharedbike.entity.Prediction;
import com.example.sharedbike.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/getPrediction")
    public Prediction getPrediction(@RequestBody Map map){
        String datetime = map.get("datetime").toString();
        String location = map.get("location").toString();
        Prediction prediction = predictionService.getPrediction(location,datetime);
        if(prediction != null){
            return prediction;
        }else{
            return null;
        }
    }
}
