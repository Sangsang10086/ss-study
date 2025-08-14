package com.aop.handle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 10599
 */
// 注解作用在字段上
@Target(ElementType.FIELD)
// 运行时生效
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskSensitive {
    // 可以定义注解的属性，用于配置脱敏规则
    int prefix() default 1;  // 保留前缀长度
    int suffix() default 1;  // 保留后缀长度
    char maskChar() default '*';  // 替换字符
}
