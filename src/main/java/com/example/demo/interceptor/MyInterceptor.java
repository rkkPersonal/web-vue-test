package com.example.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.annotation.Authorized;
import com.example.demo.vo.Order;
import com.example.demo.vo.Shipping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Steven
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

/*        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Authorized methodAnnotation = handlerMethod.getMethodAnnotation(Authorized.class);

            //获取请求参数
            String queryString = request.getQueryString();
            if (queryString != null || !queryString.equals("") ) {

                String[] split = queryString.split("&");
                for (String s : split) {
                    if (s==null|| s.equals("")){
                        continue;
                    }
                    String decode = URLDecoder.decode(s, StandardCharsets.UTF_8.toString());
                    log.info("请求参数:{}", decode);
                }


            } else {
                if (methodAnnotation != null) {

                    Class<?> requestBody = methodAnnotation.requestBody();

                    if (requestBody != null) {
                        //获取请求body
                        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
                        String body = new String(bodyBytes, request.getCharacterEncoding());
                        Order order = JSONObject.parseObject(body, Order.class);
                        Shipping shipping = order.getShipping();
                        log.info("shipping：{}", shipping);


                    }
                }
            }
            return true;
        }*/

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
