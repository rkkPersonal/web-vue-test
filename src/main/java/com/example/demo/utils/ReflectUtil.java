package com.example.demo.utils;

import com.example.demo.annotation.NotNull;
import com.example.demo.annotation.Valid;
import com.example.demo.vo.Order;
import com.example.demo.vo.Report;
import com.example.demo.vo.Response;
import com.example.demo.vo.Storage;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author Steven
 */
public class ReflectUtil {


    public static void main(String[] args) throws Exception {
        Report report = getOrder();
        Response validator = validator(report);
        System.out.println(validator);

    }

    private static Response validator(Object report) throws Exception {
        for (Field declaredField : report.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            System.out.println(name);
            if (declaredField.isAnnotationPresent(NotNull.class)) {
                Object value = declaredField.get(report);
                NotNull annotation = declaredField.getAnnotation(NotNull.class);
                if (value == null || "".equals(value)) {
                    return Response.error(annotation.msg());
                }

            } else if (declaredField.isAnnotationPresent(Valid.class)) {
                Object o = declaredField.get(report);
                Response validator = validator(o);
                if (validator.getCode().intValue() == 0) {
                    continue;
                }

            } else {



                AnnotatedType annotatedType = declaredField.getAnnotatedType();
                if (annotatedType instanceof AnnotatedParameterizedType){
                    AnnotatedParameterizedType listType = (AnnotatedParameterizedType) declaredField.getAnnotatedType();

                    for (AnnotatedType annotatedActualTypeArgument : listType.getAnnotatedActualTypeArguments()) {

                        if (annotatedActualTypeArgument.isAnnotationPresent(Valid.class)) {
                            Object o = declaredField.get(report);
                            Response validator = validator(o);
                            if (validator.getCode().intValue()==0){
                                continue;
                            }
                        }
                    }
                }
            }
        }
        return Response.ok();
    }

    private static Report getOrder() {
        Report report = new Report();

        report.setReportId(UUID.randomUUID().toString());

        Order order = new Order();
        order.setOrderId(1321L);

        report.setOrder(order);
        List<Storage> storageList = new ArrayList<>();

        Storage storage = new Storage();

        storageList.add(storage);

        report.setStorages(storageList);

        return report;
    }
}
