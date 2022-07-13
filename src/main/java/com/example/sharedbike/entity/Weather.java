package com.example.sharedbike.entity;


public class Weather {

  private String datetime;
  private long season;
  private long holiday;
  private long weather;
  private long workingday;
  private double temp;
  private double atemp;
  private long humidity;
  private double windspeed;
  private String location;


  public String getDatetime() {
    return datetime;
  }

  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }


  public long getSeason() {
    return season;
  }

  public void setSeason(long season) {
    this.season = season;
  }


  public long getHoliday() {
    return holiday;
  }

  public void setHoliday(long holiday) {
    this.holiday = holiday;
  }


  public long getWeather() {
    return weather;
  }

  public void setWeather(long weather) {
    this.weather = weather;
  }


  public long getWorkingday() {
    return workingday;
  }

  public void setWorkingday(long workingday) {
    this.workingday = workingday;
  }


  public double getTemp() {
    return temp;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }


  public double getAtemp() {
    return atemp;
  }

  public void setAtemp(double atemp) {
    this.atemp = atemp;
  }


  public long getHumidity() {
    return humidity;
  }

  public void setHumidity(long humidity) {
    this.humidity = humidity;
  }


  public double getWindspeed() {
    return windspeed;
  }

  public void setWindspeed(double windspeed) {
    this.windspeed = windspeed;
  }


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
