package com.example.demo.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Steven
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Authorized {

    String[] permission() default {};

    Class<?> requestBody() default void.class;
}
