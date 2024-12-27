package online.qms198.springboot_stu.pojo.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.Recruitment;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDto {

    private Integer recruitmentId;

    private String publisherAccount;

    @Length(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]+$", message = "手机号必须为数字")
    private String publisherPhoneNumber;

    private LocalDateTime publishTime; // 招聘发布日期时间

    private LocalDateTime recruitmentDeadline; // 招聘截至日期时间

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

    private Integer status;

    private List<Long> tagIds;

    public RecruitmentDto(Recruitment recruitment, List<Long> tagIds){
        this.recruitmentId = recruitment.getRecruitmentId();
        this.publisherAccount = recruitment.getPublisherAccount();
        this.publisherPhoneNumber = recruitment.getPublisherPhoneNumber();
        this.publishTime = recruitment.getPublishTime();
        this.recruitmentDeadline = recruitment.getRecruitmentDeadline();
        this.publishTitle = recruitment.getPublishTitle();
        this.briefIntroduction = recruitment.getBriefIntroduction();
        this.maxMonthlySalary = recruitment.getMaxMonthlySalary();
        this.minMonthlySalary = recruitment.getMinMonthlySalary();
        this.numberOfDeliveries = recruitment.getNumberOfDeliveries();
        this.getNumberOfDeliveries = recruitment.getGetNumberOfDeliveries();
        this.status = recruitment.getStatus();
        this.tagIds = tagIds;
    }
}
