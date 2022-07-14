package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Prediction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PredictionMapper {

    void add(Prediction prediction);
    Prediction find(String datetime,String location);

}
