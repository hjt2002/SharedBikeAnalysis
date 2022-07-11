package com.example.sharedbike.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PythonInvoke {

    public void invokePython(){

        //数据准备
        int a = 1;
        int b = 2;
        List <Float> dataList;
        Process proc;
        try {
            String[] pargs = new String[]{"python","E:\\pythonProject\\model\\Model-Prediction.py",String.valueOf(a), String.valueOf(b)};
            proc = Runtime.getRuntime().exec(pargs);// 执行py文件

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
