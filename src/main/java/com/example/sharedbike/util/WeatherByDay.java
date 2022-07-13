package com.example.sharedbike.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.*;
import com.example.sharedbike.util.JudgeHoliday;


//用于获取明日的天气情况 返回一个哈希表以供调用

public class WeatherByDay {
    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    private String TIANQI_API_SECRET_KEY = "SwIOdXtWvr5ZslZ6T"; //

    private String TIANQI_API_USER_ID = "P_R5qLPxlLQFS68ab"; //

    Map<String,Object> dailyMap = new HashMap<>();//传值用的map


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
     * @param days
     * @return
     */

    public String generateGetDiaryWeatherURL(
            String location,
            String language,
            String unit,
            String start,
            String days
    )  throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=1800&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        System.out.println(TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location
                + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days);
        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location
                + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
    }

    public List<Map<String,Object>> getDailyWeather(String location) {
        WeatherByDay dailyData = new WeatherByDay();
        List<Map<String, Object>> list = null;
        try {
            list = null;
            String url = dailyData.generateGetDiaryWeatherURL(
                    location,
                    "zh-Hans",
                    "c",
                    "1",
                    "15"
            );
//            System.out.println("URL:" + url);
            URL url1 = new URL(url);
            URLConnection connectionData = url1.openConnection();
            connectionData.setConnectTimeout(1000);
            BufferedReader br = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String data = sb.toString(); //每日的数据
//            System.out.println(data);
            JSONObject jsonData = JSON.parseObject(data);
            JSONArray results = jsonData.getJSONArray("results");
            JSONArray daily = results.getJSONObject(0).getJSONArray("daily");
            //获取一天内所有json数据
//            System.out.println(daily);
            list = new ArrayList<>();
            JSONObject jsonObject = null;
            for (int i = 0; i < daily.size(); i++) {
                jsonObject = daily.getJSONObject(i);
                Map<String, Object> dailyMap = new HashMap<>();//传值用的map
//                System.out.println(jsonObject);
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    dailyMap.put(entry.getKey(), entry.getValue());
                }
                list.add(dailyMap);
            }

            for(Map<String,Object>map:list){
                Object tempHigh = map.get("high");
                Object tempLow = map.get("low");
                double high = Double.parseDouble(String.valueOf(tempHigh));
                double low = Double.parseDouble(String.valueOf(tempLow));
                float temp = (float) ((high+low)/2);
                map.put("temp",temp);
                map.put("location",location);
            }

            for (Map<String,Object>map:list){
                Object tempO = map.get("temp");
                double temp = Double.parseDouble(String.valueOf(tempO));
                Object windSpeedO =  map.get("wind_speed");
                double windSpeed = Double.parseDouble(String.valueOf(windSpeedO));
                Object humidityO =map.get("humidity");
                double humidity = Double.parseDouble(String.valueOf(humidityO));
                double e =humidity/100*6.105*Math.pow(2.7182818,1.27*temp/(237.7+temp));
                float atemp = (float) (1.07*temp+0.2*e-0.65*windSpeed-2.7);
                map.put("atemp",atemp);
//                System.out.println(atemp)s;
            }


            //用于气温标准化
//            double max = -100;
//            double min = 100;
//            double amax = -100;
//            double amin = 100;
//            for (Map<String,Object> map:list
//            ) {
//                Object tempO = map.get("temp");
//                double temp = Double.parseDouble(String.valueOf(tempO));
//                Object atempO = map.get("atemp");
//                double atemp = Double.parseDouble(String.valueOf(atempO));
//                System.out.println("amax:"+amax+"  amin:"+amin+" now:"+atemp);
//
//                if (temp>max)
//                    max = temp;
//                if(temp<min)
//                    min = temp;
//                if (atemp>amax)
//                    amax = atemp;
//                if(atemp<amin)
//                    amin = atemp;
//            }
//            System.out.println("amax:"+amax);
//            System.out.println("amin"+amin);


//            for(Map<String,Object>map:list){
//                //对气温标准化
//                Object tempO =map.get("temp");
//                double temp = Double.parseDouble(String.valueOf(tempO));
//                Object atempO =map.get("atemp");
//                double atemp = Double.parseDouble(String.valueOf(atempO));
//                System.out.println((temp-min)/(max-min));
//                map.put("temp",(temp-min)/(max-min));
//                //对体感温度标准化
//                map.put("atemp",(atemp-amin)/(amax-amin));
//            }


            for (Map<String,Object>map:list) {
                //格式标准化，判断是否为工作日
                String date = (String) map.get("date");
                date = date.replace("-", "/");
                map.put("date",date);
                DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
                Date bdate = format1.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(bdate);
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    map.put("workingday", 0);
                } else {
                    map.put("workingday", 1);
                }

                //判断季节
                String tempDate = date.replace("/","");
                String dataOfSeasonStr = tempDate.substring(4,8);
                int dateOfSeason = Integer.parseInt(dataOfSeasonStr);
                if(301 <= dateOfSeason && dateOfSeason <= 630){
                    map.put("season",1);
                } else if (701 <= dateOfSeason && dateOfSeason <= 831) {
                    map.put("season",2);
                } else if (901 <= dateOfSeason && dateOfSeason <= 1130) {
                    map.put("season",3);
                } else if ((1201 <= dateOfSeason && dateOfSeason <= 1231)
                        || (101 <= dateOfSeason && dateOfSeason <= 228)) {
                    map.put("season",4);
                }
                //判断是否为假期
                String resultStr = JudgeHoliday.request(tempDate);
                int tempData = Integer.parseInt(resultStr);
                if(tempData == 2){
                    //节假日
                    map.put("holiday",1);
                }else {
                    //非节假日
                    //工作日 周末
                    map.put("holiday",0);
                }

            }


            //白天天气代码标准化
            for (Map<String,Object> map:list
            ) {
                Object codeO = map.get("code_day");
                int code = Integer.parseInt(String.valueOf(codeO));
                switch (code){
                    case 0:map.put("code_day",1); break;
                    case 1:map.put("code_day",1); break;
                    case 2:map.put("code_day",1); break;
                    case 3:map.put("code_day",1); break;
                    case 4:map.put("code_day",1); break;
                    case 5:map.put("code_day",1); break;
                    case 6:map.put("code_day",1); break;
                    case 7:map.put("code_day",1); break;
                    case 8:map.put("code_day",1); break;
                    case 9:map.put("code_day",2); break;
                    case 10:map.put("code_day",3); break;
                    case 11:map.put("code_day",4); break;
                    case 12:map.put("code_day",4); break;
                    case 13:map.put("code_day",3); break;
                    case 14:map.put("code_day",4); break;
                    case 15:map.put("code_day",4); break;
                    case 16:map.put("code_day",4); break;
                    case 17:map.put("code_day",4); break;
                    case 18:map.put("code_day",4); break;
                    case 19:map.put("code_day",4); break;
                    case 20:map.put("code_day",4); break;
                    case 21:map.put("code_day",3); break;
                    case 22:map.put("code_day",3); break;
                    case 23:map.put("code_day",4); break;
                    case 24:map.put("code_day",4); break;
                    case 25:map.put("code_day",4); break;
                    case 26:map.put("code_day",2); break;
                    case 27:map.put("code_day",3); break;
                    case 28:map.put("code_day",4); break;
                    case 29:map.put("code_day",4); break;
                    case 30:map.put("code_day",2); break;
                    case 31:map.put("code_day",2); break;
                    case 32:map.put("code_day",2); break;
                    case 33:map.put("code_day",3); break;
                    case 34:map.put("code_day",4); break;
                    case 35:map.put("code_day",4); break;
                    case 36:map.put("code_day",4); break;
                    default:
                        map.put("code_day",1);
                }

            }

            //夜晚代码标准化
            for (Map<String,Object> map:list
            ) {
                Object codeO = map.get("code_night");
                int code = Integer.parseInt(String.valueOf(codeO));
                switch (code){
                    case 0:map.put("code_night",1); break;
                    case 1:map.put("code_night",1); break;
                    case 2:map.put("code_night",1); break;
                    case 3:map.put("code_night",1); break;
                    case 4:map.put("code_night",1); break;
                    case 5:map.put("code_night",1); break;
                    case 6:map.put("code_night",1); break;
                    case 7:map.put("code_night",1); break;
                    case 8:map.put("code_night",1); break;
                    case 9:map.put("code_night",2); break;
                    case 10:map.put("code_night",3); break;
                    case 11:map.put("code_night",4); break;
                    case 12:map.put("code_night",4); break;
                    case 13:map.put("code_night",3); break;
                    case 14:map.put("code_night",4); break;
                    case 15:map.put("code_night",4); break;
                    case 16:map.put("code_night",4); break;
                    case 17:map.put("code_night",4); break;
                    case 18:map.put("code_night",4); break;
                    case 19:map.put("code_night",4); break;
                    case 20:map.put("code_night",4); break;
                    case 21:map.put("code_night",3); break;
                    case 22:map.put("code_night",3); break;
                    case 23:map.put("code_night",4); break;
                    case 24:map.put("code_night",4); break;
                    case 25:map.put("code_night",4); break;
                    case 26:map.put("code_night",2); break;
                    case 27:map.put("code_night",3); break;
                    case 28:map.put("code_night",4); break;
                    case 29:map.put("code_night",4); break;
                    case 30:map.put("code_night",2); break;
                    case 31:map.put("code_night",2); break;
                    case 32:map.put("code_night",2); break;
                    case 33:map.put("code_night",3); break;
                    case 34:map.put("code_night",4); break;
                    case 35:map.put("code_night",4); break;
                    case 36:map.put("code_night",4); break;
                    default:
                        map.put("code_night",1);
                }
            }


            for (Map<String,Object>map:list){
                Object code_nightO = map.get("code_night");
                int code_night = Integer.parseInt(String.valueOf(code_nightO));
                Object code_dayO = map.get("code_day");
                int code_day = Integer.parseInt(String.valueOf(code_dayO));
                int codeInit = (code_night+code_day)/2;
                map.put("code",codeInit);
            }

            //键名标准化
            for(Map<String,Object> map:list) {
//                map.put("temp", map.remove("temperature"));
                map.put("weather", map.remove("code"));
                map.put("windspeed", map.remove("wind_speed"));
//                map.put("datetime", map.remove("date"));


                map.remove("code_day");
                map.remove("code_night");
                map.remove("text_day");
                map.remove("text_night");
                map.remove("rainfall");
                map.remove("wind_direction");
                map.remove("wind_direction_degree");
                map.remove("wind_scale");
                map.remove("precip");
                map.remove("high");
                map.remove("low");
            }

//            System.out.println("location:"+"北京");
//            for (Map<String,Object> map:list
//            ) {
//                System.out.println("location:" + map.get("location"));
//                System.out.println("datetime:"+map.get("date"));
//                System.out.println("weather:"+map.get("weather"));//标准化后的天气代码
//                System.out.println("temp:"+map.get("temp"));
//                System.out.println("atemp:"+map.get("atemp"));
//                System.out.println("humidity:"+map.get("humidity"));
//                System.out.println("windspeed:"+map.get("windspeed"));
//                System.out.println("workingday:"+map.get("workingday"));
//                System.out.println("season:"+map.get("season"));
//                System.out.println("holiday:"+map.get("holiday"));
//                System.out.println("-------");
//            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return list;
    }





}
