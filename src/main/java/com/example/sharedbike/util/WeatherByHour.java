package com.example.sharedbike.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherByHour {
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
        WeatherByHour weatherByHour = new WeatherByHour();

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
            list = new ArrayList<>();
            for (int i = 0; i < daily.size(); i++) {
                JSONObject jsonObject = daily.getJSONObject(i);
                Map <String,Object> dailyMap = new HashMap<>();//传值用的map
//                System.out.println(jsonObject);
                for(Map.Entry<String,Object>entry:jsonObject.entrySet()){
                    dailyMap.put(entry.getKey(),entry.getValue());
                }
                list.add(dailyMap);
            }

            //计算体感温度
            for (Map<String,Object> map:list
            ) {
                Object tempO =  map.get("temperature");
                double temp = Double.parseDouble(String.valueOf(tempO));
                Object windSpeedO =  map.get("wind_speed");
                double windSpeed = Double.parseDouble(String.valueOf(windSpeedO));
                Object humidityO =map.get("humidity");
                double humidity = Double.parseDouble(String.valueOf(humidityO));

                double e =humidity/100*6.105*Math.pow(2.7182818,1.27*temp/(237.7+temp));
                double atemp = 1.07*temp+0.2*e-0.65*windSpeed-2.7;
                map.put("atemp",temp);
//                System.out.println(atemp);
            }
//            System.out.println("flag139");
//            //用于气温标准化
//            double max = -100;
//            double min = 100;
//            double amax = -100;
//            double amin = 100;
//            for (Map<String,Object> map:list
//            ) {
//                Object tempO = map.get("temperature");
//                Object atempO = map.get("atemp");
//                double temp = Double.parseDouble(String.valueOf(tempO));
//                double atemp = Double.parseDouble(String.valueOf(tempO));
//                if (temp>max)
//                    max = temp;
//                if(temp<min);
//                    min = temp;
//                if (atemp>amax)
//                    amax = temp;
//                if(atemp<min)
//                    amin = temp;
//            }
//            System.out.println("max:"+max);
//            System.out.println("min"+min);
//            System.out.println(list);

            //划分天气状况
            for (Map<String,Object> map:list
            ) {
                Object codeO = map.get("code");
                int code = Integer.parseInt(String.valueOf(codeO));
                switch (code){
                    case 0:map.put("code",1); break;
                    case 1:map.put("code",1); break;
                    case 2:map.put("code",1); break;
                    case 3:map.put("code",1); break;
                    case 4:map.put("code",1); break;
                    case 5:map.put("code",1); break;
                    case 6:map.put("code",1); break;
                    case 7:map.put("code",1); break;
                    case 8:map.put("code",1); break;
                    case 9:map.put("code",2); break;
                    case 10:map.put("code",3); break;
                    case 11:map.put("code",4); break;
                    case 12:map.put("code",4); break;
                    case 13:map.put("code",3); break;
                    case 14:map.put("code",4); break;
                    case 15:map.put("code",4); break;
                    case 16:map.put("code",4); break;
                    case 17:map.put("code",4); break;
                    case 18:map.put("code",4); break;
                    case 19:map.put("code",4); break;
                    case 20:map.put("code",4); break;
                    case 21:map.put("code",3); break;
                    case 22:map.put("code",3); break;
                    case 23:map.put("code",4); break;
                    case 24:map.put("code",4); break;
                    case 25:map.put("code",4); break;
                    case 26:map.put("code",2); break;
                    case 27:map.put("code",3); break;
                    case 28:map.put("code",4); break;
                    case 29:map.put("code",4); break;
                    case 30:map.put("code",2); break;
                    case 31:map.put("code",2); break;
                    case 32:map.put("code",2); break;
                    case 33:map.put("code",3); break;
                    case 34:map.put("code",4); break;
                    case 35:map.put("code",4); break;
                    case 36:map.put("code",4); break;
                    default:
                        map.put("code",1);
                }

            }

            //日期格式统一及工作日的判断
            for (Map<String,Object> map:list
            ) {
                //对日期进行格式统一
                String dateinit = (String) map.get("time");
                String date = dateinit.substring(0,10);
                //判断每个日期是否是节假日
                String tempDate = date.replace("-","");
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
                //判断当前日期属于那个季节
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

                date = date.replace("-","/");
                String timeHourly = dateinit.substring(11,19);
                map.put("time",date +" "+ timeHourly);
                DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
                Date bdate = format1.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(bdate);
                if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    map.put("workingday",0);
                } else{
                    map.put("workingday",1);
                }

                //对气温标准化
//                    Object tempO =map.get("temperature");
//                    double temp = Double.parseDouble(String.valueOf(tempO));
//                    Object atempO =map.get("temperature");
//                    double atemp = Double.parseDouble(String.valueOf(atempO));
//                     System.out.println((temp-min)/(max-min));
//                    map.put("temperature",(temp-min)/(max-min));
//                    //对体感温度标准化
//                    map.put("atemp",(atemp-amin)/(amax-amin));
//                System.out.println("-------");
            }
        //更换键的名称，删除不需要的键
            for(Map<String,Object> map:list){
                map.put("temp",map.remove("temperature"));
                map.put("weather",map.remove("code"));
                map.put("windspeed",map.remove("wind_speed"));
                map.put("datetime",map.remove("time"));
                map.remove("text");
                map.remove("wind_direction");
            }
        //处理datatime类型的数据
//            for(Map<String,Object>map :list){
//                String tempS = map.get("datetime").toString();
//                SimpleDateFormat dataParser = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
//                Date date = dataParser.parse(tempS);
//                map.replace("datetime",date);
//            }
        }catch (Exception e) {
            System.out.println("Exception:" + e);
        }
//        return (HashMap<String, Object>) dailyMap;
        return list;
    }
}
