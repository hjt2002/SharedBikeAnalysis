package com.example.sharedbike.entity;


public class Prediction {

  private String location;
  private String datetime;
  private long prediction;


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public String getDatetime() {
    return datetime;
  }

  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }


  public long getPrediction() {
    return prediction;
  }

  public void setPrediction(long prediction) {
    this.prediction = prediction;
  }

}
