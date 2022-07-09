package com.example.sharedbike.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.jshell.execution.Util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.*;

public class weatherByHour {
    private String TIANQI_Hourly_WEATHER_URL = "https://api.seniverse.com/v3/weather/hourly.json";

    private String TIANQI_API_USER_ID = "P_R5qLPxlLQFS68ab"; //

    private String TIANQI_API_SECRET_KEY = "SwIOdXtWvr5ZslZ6T"; //


    /**
     * Generate HmacSHA1 signature with given data string and key
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */

    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            //原来的返回
//            result = new sun.misc.BASE64Encoder().encode(rawHmac);
            //
            //修改后的返回
            result = Base64.getEncoder().encodeToString(rawHmac);
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    /**
     * Generate the URL to get diary weather
     * @param location
     * @param language
     * @param unit
     * @param start

     * @return
     */

    public String generateGetHourlyWeatherURL(
            String location,
            String language,
            String unit,
            String start,
            String hours
    )  throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=1800&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        return TIANQI_Hourly_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location
                + "&language=" + language + "&unit=" + unit + "&start=" + start + "&hours=" + hours;
//        https://api.seniverse.com/v3/weather/hourly.json?key=your_api_key&location=beijing&language=zh-Hans&unit=c&start=0&hours=24
    }

    public List<Map<String,Object>> getHourlyWeather(String location) {
//        weatherDaily dailyData = new weatherDaily();
        weatherByHour weatherByHour = new weatherByHour();

        List<Map<String,Object>>list = null;
        try{String url = weatherByHour.generateGetHourlyWeatherURL(
                location,
                "zh-Hans",
                "c",
                "0",
                "24"
        );
            System.out.println("URL:" + url);
            URL url1 = new URL(url);
            URLConnection connectionData = url1.openConnection();
            connectionData.setConnectTimeout(1000);
            BufferedReader br = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            String data = sb.toString(); // 24小时的数据
//            System.out.println(data);
            JSONObject jsonData = JSON.parseObject(data);
            JSONArray results = jsonData.getJSONArray("results");
            JSONArray daily = results.getJSONObject(0).getJSONArray("hourly");
            //获取一天内所有json数据
//            System.out.println(daily);
            list = new ArrayList<Map<String,Object>>();
            for (int i = 0; i < daily.size(); i++) {
                JSONObject jsonObject = daily.getJSONObject(i);
                Map<String,Object> dailyMap = new HashMap<>();//传值用的map
//                System.out.println(jsonObject);
                for(Map.Entry<String,Object>entry:jsonObject.entrySet()){
                    dailyMap.put(entry.getKey(),entry.getValue());
                }
                list.add(dailyMap);
            }
//            System.out.println(list);
//            System.out.println("location:"+"北京");
//            System.out.println("date:"+dailyMap.get("date"));
//            System.out.println("weather:"+dailyMap.get("text_day"));
//            System.out.println("temp:"+dailyMap.get("high"));
//            System.out.println("humidity:"+dailyMap.get("humidity"));
//            System.out.println("wind_speed:"+dailyMap.get("wind_speed"));
//            System.out.println("");
        }catch (Exception e) {
            System.out.println("Exception:" + e);
        }
//        return (HashMap<String, Object>) dailyMap;
        return list;
    }
}
