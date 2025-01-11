package online.qms198.springboot_stu.dto.group;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUserDto {

    private String userName;

    private String userAccount;

    private String email;

    private String userIdentity;

    private Integer roleStatus;
}
