package com.xxxx.crm.config;

import java.lang.annotation.*;

/**
 * @author lwf
 * @title: Privalige
 * @projectName crm
 * @description: 权限注解
 * @date 2020/12/519:17
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Privalige {
    String value() default "";
}
