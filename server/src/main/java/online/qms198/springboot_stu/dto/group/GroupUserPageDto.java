package online.qms198.springboot_stu.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.user.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUserPageDto {
    private Integer groupUserTotal;
    private List<GroupUserDto> groupUsers;
}
