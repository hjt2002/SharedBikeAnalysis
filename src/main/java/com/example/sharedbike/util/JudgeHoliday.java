package com.example.sharedbike.util;

import com.alibaba.fastjson.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * 调用API接口判断日期是否是工作日 周末还是节假日
 * 工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
 */
public class JudgeHoliday {

    public static String request(String httpArg) {
//        String httpUrl = "http://api.goseek.cn/Tools/holiday";
        String httpUrl = "http://tool.bitefu.net/jiari/";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?d=" + httpArg;

        String d="0";//工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
            d = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        d = d.replace("\n","");
        d = d.replace("\r","");
//        String str = "aaa";

        return d;
    }
}
