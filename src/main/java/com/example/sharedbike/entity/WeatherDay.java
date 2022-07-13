package com.example.sharedbike.entity;


public class WeatherDay {

  private String date;
  private long weather;
  private double temp;
  private double atemp;
  private double humidity;
  private double windspeed;
  private long workingday;
  private long season;
  private long holiday;
  private String location;


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  public long getWeather() {
    return weather;
  }

  public void setWeather(long weather) {
    this.weather = weather;
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


  public double getHumidity() {
    return humidity;
  }

  public void setHumidity(double humidity) {
    this.humidity = humidity;
  }


  public double getWindspeed() {
    return windspeed;
  }

  public void setWindspeed(double windspeed) {
    this.windspeed = windspeed;
  }


  public long getWorkingday() {
    return workingday;
  }

  public void setWorkingday(long workingday) {
    this.workingday = workingday;
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


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
