package online.qms198.springboot_stu.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDto {
    private Integer page;
    private Integer size;
    private Integer recruitmentId;
}
