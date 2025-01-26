package online.qms198.springboot_stu.dto.user;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;

    private String userName;

    private String userAccount;

    private String email;

    private String userIdentity;
}
