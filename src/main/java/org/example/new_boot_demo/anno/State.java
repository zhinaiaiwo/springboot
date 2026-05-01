package org.example.new_boot_demo.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.new_boot_demo.Validation.StateValidation;

import java.lang.annotation.*;


@Documented // 元注解
@Constraint(validatedBy = {StateValidation.class}) // 指定提供校验规则的类
@Target({ElementType.FIELD}) // 元注解，在什么时候生效
@Retention(RetentionPolicy.RUNTIME)  // 元注解，到什么时候失效
public @interface State {
    // 提供校验失后的提示信息
    String message() default "state 参数只能是 “已发布” 或者 “草稿”";

    // 指定分组
    Class<?>[] groups() default {};

    // 负载， 获取到 State 注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
