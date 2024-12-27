package online.qms198.springboot_stu.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import online.qms198.springboot_stu.pojo.user.PasswordMatch;
import online.qms198.springboot_stu.dto.user.UserRegisterDto;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegisterDto> {

    @Override
    public boolean isValid(UserRegisterDto userRegisterDto, ConstraintValidatorContext context) {
        if (userRegisterDto.getPassword() == null || userRegisterDto.getConfirmedPassword() == null) {
            return true; // 不进行校验，交给其他注解处理
        }
        boolean isValid = userRegisterDto.getPassword().equals(userRegisterDto.getConfirmedPassword());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("密码和确认密码不匹配")
                    .addPropertyNode("confirmedPassword") // 将错误信息绑定到 confirmedPassword 字段
                    .addConstraintViolation();
        }
        return isValid;
    }
}
