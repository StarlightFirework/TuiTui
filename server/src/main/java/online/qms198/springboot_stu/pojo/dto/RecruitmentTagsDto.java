package online.qms198.springboot_stu.pojo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.Recruitment;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentTagsDto {

    private Integer recruitmentId;

    private String publisherAccount;

    private String publisherPhoneNumber;

    private LocalDateTime publishTime; // 招聘发布日期时间

    private LocalDateTime recruitmentDeadline;

    private String publishTitle; // 招聘标题

    private String briefIntroduction; // 简介

    private Integer maxMonthlySalary;

    private Integer minMonthlySalary;

    private Integer numberOfDeliveries;

    private Integer getNumberOfDeliveries;

    private Integer status; // 0：有效（默认值） 1：无效

    private List<Long> tagIds;

    public RecruitmentTagsDto(Recruitment recruitment,List<Long> tagIds){
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
