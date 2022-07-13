package com.example.sharedbike.entity;


public class Prediction {

  private String location;
  private String datetime;
  private float prediction;


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


  public float getPrediction() {
    return prediction;
  }

  public void setPrediction(float prediction) {
    this.prediction = prediction;
  }

}
