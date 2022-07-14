package com.example.sharedbike.util;

import com.example.sharedbike.entity.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonInvoke {

    public boolean invokePython(String location){
        boolean flag = false;
        Process proc;
        String line = null;
        try {
            String[] pargs = new String[]{"python","E:\\pythonProject\\Model\\Prediction.py",location};

            proc = Runtime.getRuntime().exec(pargs);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                flag = true;
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
