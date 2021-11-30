/*
package com.example.demo.interceptor.servlet;

import com.example.demo.interceptor.MyHttpServletRequestWrapper;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * @author Steven
 *//*

public class MyDispatcherServlet extends DispatcherServlet {

    */
/**
     * 包装成我们自定义的request
     * @param request
     * @param response
     * @throws Exception
     *//*

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new MyHttpServletRequestWrapper(request), response);
    }
}
*/
