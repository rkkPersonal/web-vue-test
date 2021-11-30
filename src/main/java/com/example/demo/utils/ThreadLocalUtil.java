package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadLocalUtil {

    public static ThreadLocal<HttpServletResponse> threadLocal = new ThreadLocal();



    public static void add(HttpServletResponse response) {
        threadLocal.set(response);
    }


    public static HttpServletResponse get() {
        return threadLocal.get();
    }
}
