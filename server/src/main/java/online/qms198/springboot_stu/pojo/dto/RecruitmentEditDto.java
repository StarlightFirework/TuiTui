package online.qms198.springboot_stu.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentEditDto {

    private Integer recruitmentId;

    private String publisherAccount;

    @Length(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]+$", message = "手机号必须为数字")
    private String publisherPhoneNumber;

    private String recruitmentDeadlineStr; // 招聘截至日期时间

    @NotBlank(message = "招聘标题不能为空")
    @Length(max = 30)
    private String publishTitle; // 招聘标题

    @NotBlank(message = "招聘简介不能为空")
    @Length(max = 150)
    private String briefIntroduction; // 简介

    private Integer maxMonthlySalary;

    private Integer minMonthlySalary;

    private Integer numberOfDeliveries;

    private Integer getNumberOfDeliveries;

    private List<Long> tagIds;
}
