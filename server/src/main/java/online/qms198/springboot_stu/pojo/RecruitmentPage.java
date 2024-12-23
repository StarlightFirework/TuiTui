package online.qms198.springboot_stu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.Recruitment;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentPage {

    private Integer recruitmentTotal;

    private List<Recruitment> recruitments;
}
