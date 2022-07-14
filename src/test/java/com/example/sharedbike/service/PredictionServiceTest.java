package com.example.sharedbike.service;


import com.example.sharedbike.util.PythonInvoke;
import org.junit.jupiter.api.Test;

class PredictionServiceTest {

    @Test
    void getPrediction() {
        String location = "beijing";
        PythonInvoke pythonInvoke = new PythonInvoke();
        System.out.println(pythonInvoke.invokePython(location));
    }
}