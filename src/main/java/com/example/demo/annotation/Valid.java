package com.example.demo.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Steven
 */
@Target({ElementType.FIELD,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Valid {
}
