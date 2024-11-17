package online.qms198.springboot_stu.pojo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 12)
    private String password;

    @Length(min = 8, max = 12)
    @Pattern(regexp = "^[0-9]+$", message = "userAccount必须为数字")
    private String userAccount;
    //swt令牌
    private String token;
}
