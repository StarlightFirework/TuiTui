package online.qms198.springboot_stu.dto.recruitment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentAuditDto {
    private Integer recruitmentId;
    private Integer auditCode;
    private String description;
}
