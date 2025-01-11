package online.qms198.springboot_stu.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentUserGroupDto {
    private Integer groupAccount;
    // 批量账号
    private List<String> userAccounts;
    // 单个账号
    private String userAccount;
}
