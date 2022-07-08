package com.example.sharedbike.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@lombok.Data
public class Data {

  @Id
  private String dteday;
  private long season;
  private long yr;
  private long mnth;
  private long holiday;
  private long weekday;
  private long workingday;
  private long weathersit;
  private double temp;
  private double atemp;
  private double hum;
  private double windspeed;
  private long casual;
  private long registered;
  private String cnt;

  public Data(String dteday, long season, long yr, long mnth, long holiday, long weekday, long workingday, long weathersit, double temp, double atemp, double hum, double windspeed, long casual, long registered, String cnt) {
    this.dteday = dteday;
    this.season = season;
    this.yr = yr;
    this.mnth = mnth;
    this.holiday = holiday;
    this.weekday = weekday;
    this.workingday = workingday;
    this.weathersit = weathersit;
    this.temp = temp;
    this.atemp = atemp;
    this.hum = hum;
    this.windspeed = windspeed;
    this.casual = casual;
    this.registered = registered;
    this.cnt = cnt;
  }

  public Data() {

  }

  public String getDteday() {
    return dteday;
  }

  public void setDteday(String dteday) {
    this.dteday = dteday;
  }


  public long getSeason() {
    return season;
  }

  public void setSeason(long season) {
    this.season = season;
  }


  public long getYr() {
    return yr;
  }

  public void setYr(long yr) {
    this.yr = yr;
  }


  public long getMnth() {
    return mnth;
  }

  public void setMnth(long mnth) {
    this.mnth = mnth;
  }


  public long getHoliday() {
    return holiday;
  }

  public void setHoliday(long holiday) {
    this.holiday = holiday;
  }


  public long getWeekday() {
    return weekday;
  }

  public void setWeekday(long weekday) {
    this.weekday = weekday;
  }


  public long getWorkingday() {
    return workingday;
  }

  public void setWorkingday(long workingday) {
    this.workingday = workingday;
  }


  public long getWeathersit() {
    return weathersit;
  }

  public void setWeathersit(long weathersit) {
    this.weathersit = weathersit;
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


  public double getHum() {
    return hum;
  }

  public void setHum(double hum) {
    this.hum = hum;
  }


  public double getWindspeed() {
    return windspeed;
  }

  public void setWindspeed(double windspeed) {
    this.windspeed = windspeed;
  }


  public long getCasual() {
    return casual;
  }

  public void setCasual(long casual) {
    this.casual = casual;
  }


  public long getRegistered() {
    return registered;
  }

  public void setRegistered(long registered) {
    this.registered = registered;
  }


  public String getCnt() {
    return cnt;
  }

  public void setCnt(String cnt) {
    this.cnt = cnt;
  }

}
