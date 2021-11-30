package com.example.demo.test.mockito;


import java.io.Serializable;
import java.util.Collection;

public class SimpleService {


    public int method1(int i, String s , Collection<String> collection, Serializable serializable){
        throw new RuntimeException();
    }


    public void method2(int i, String s , Collection<String> collection, Serializable serializable){
        throw new RuntimeException();
    }

}
