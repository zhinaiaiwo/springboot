package org.example.new_boot_demo.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.new_boot_demo.anno.State;

public class StateValidation implements ConstraintValidator</*给哪个注解提供校验规则, 校验的数据类型*/State, String> {

    /**
     *
     * @param s 将来需要校验的数据
     * @param constraintValidatorContext
     * @return
     */

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 提供校验规则
        if (s == null) {
            return false;
        }
        if (s.equals("已发布") || s.equals("草稿")) {
            return true;
        }

        return false;
    }
}
