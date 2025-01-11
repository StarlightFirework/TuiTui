package online.qms198.springboot_stu.dto.group;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentGroupDto {
    private Integer groupAccount;
    private String creatorAccount;
    private String groupName;
    private Integer visibleStatus;
}
