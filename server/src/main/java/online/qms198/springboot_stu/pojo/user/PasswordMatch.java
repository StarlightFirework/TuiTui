package online.qms198.springboot_stu.pojo.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class) // 指定校验器类
@Target({ TYPE }) // 应用到类上
@Retention(RUNTIME) // 运行时保留
public @interface PasswordMatch {
    String message() default "密码和确认密码必须相同"; // 默认错误消息
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
