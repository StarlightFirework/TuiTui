package online.qms198.springboot_stu.pojo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.PasswordMatch;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatch
public class UserRegisterDto {
    private Integer userId;

    @NotBlank(message = "用户名不能为空")//拦截空内容
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 12)
    private String password;

    @NotBlank(message = "确认密码不能为空")
    @Length(min = 6, max = 12)
    private String confirmedPassword;

    @Email(message = "email格式不正确")
    private String email;

    @Length(min = 8, max = 12)
    @Pattern(regexp = "^[0-9]+$", message = "userAccount必须为数字")
    private String userAccount;

    @NotBlank(message = "身份信息不能为空")//拦截空内容
    @Pattern(regexp = "^[0-1]+$", message = "身份信息必须为0或1")
    private String userIdentity;
}
