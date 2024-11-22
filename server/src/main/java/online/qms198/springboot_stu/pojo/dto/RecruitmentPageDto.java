package online.qms198.springboot_stu.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.Recruitment;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentPageDto {

    private Integer recruitmentTotal;

    private List<Recruitment> recruitments;
}
