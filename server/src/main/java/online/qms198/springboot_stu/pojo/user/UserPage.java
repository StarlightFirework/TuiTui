package online.qms198.springboot_stu.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.dto.user.UserDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPage {
    private Integer total;

    private List<UserDto> usersDto;
}
