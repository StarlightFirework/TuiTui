package online.qms198.springboot_stu.dto.recruitment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentStatisticsDto {
    private Integer recruitmentId;
    private String userAccount;
}
