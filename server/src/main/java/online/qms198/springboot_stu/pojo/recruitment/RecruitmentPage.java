package online.qms198.springboot_stu.pojo.recruitment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentPage {
    private Integer recruitmentTotal;
    private List<RecruitmentDto> recruitments;
}
