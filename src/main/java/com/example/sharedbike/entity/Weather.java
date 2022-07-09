package com.example.sharedbike.entity;


public class Weather {

  private java.sql.Timestamp datatime;
  private long season;
  private long holiday;
  private long workingday;
  private long weather;
  private double temp;
  private double atemp;
  private long humidity;
  private double windspeed;

  public java.sql.Timestamp getDatatime() {
    return datatime;
  }

  public void setDatatime(java.sql.Timestamp datatime) {
    this.datatime = datatime;
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


  public long getWorkingday() {
    return workingday;
  }

  public void setWorkingday(long workingday) {
    this.workingday = workingday;
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

}
