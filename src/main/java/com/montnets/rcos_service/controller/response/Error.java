package com.montnets.rcos_service.controller.response;
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
  
/**
 *  自定义错误码注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
public @interface Error {  
    String msg() default "";  
} 