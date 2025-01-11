package online.qms198.springboot_stu.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecruitmentGroupPageDto {
    private Integer userGroupTotal;

    private List<RecruitmentGroupDto> recruitmentGroupDtos;
}
