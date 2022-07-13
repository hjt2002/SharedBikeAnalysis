package com.example.sharedbike.util;

import com.example.sharedbike.entity.Prediction;
import com.example.sharedbike.entity.Weather;

import javax.sound.midi.Soundbank;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PythonInvoke {

    public Prediction invokePython(Weather tempWeather){

        //数据准备
        String datetime = tempWeather.getDatetime();
        String location = tempWeather.getLocation();
        long season = tempWeather.getSeason();
        long holiday = tempWeather.getHoliday();
        long weather = tempWeather.getWeather();
        long workingday = tempWeather.getWorkingday();
        double temp = tempWeather.getTemp();
        double atemp = tempWeather.getAtemp();
        long humidity = tempWeather.getHumidity();
        double windspeed = tempWeather.getWindspeed();



        Process proc;
        String line = null;
        float predict = 0;
        try {
            String[] pargs = new String[]{"python","E:\\pythonProject\\Model\\Prediction.py",
                    datetime, String.valueOf(season),String.valueOf(holiday),
                    String.valueOf(weather), String.valueOf(workingday),String.valueOf(temp),
                    String.valueOf(atemp), String.valueOf(humidity),String.valueOf(windspeed)};

            proc = Runtime.getRuntime().exec(pargs);// 执行py文件

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((line = in.readLine()) != null) {
//                System.out.println(line);
                line = line.substring(1,line.length()-1);
                predict = Float.parseFloat(line);
                System.out.println(predict);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //获取预测数据
//        line = "111";

        Prediction prediction = new Prediction();
        prediction.setDatetime(datetime);
        prediction.setLocation(location);
        prediction.setPrediction(predict);
        return prediction;
    }
}
