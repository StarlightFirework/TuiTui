package online.qms198.springboot_stu.dto.recruitment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentUserGroupDto {
    private Integer groupAccount;

    private List<String> userAccounts;
}
