package online.qms198.springboot_stu.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.dto.RecruitmentEditDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_recruitment")
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Integer recruitmentId;

    @Column(name = "publisher_account")
    private String publisherAccount;

    @Column(name = "publisher_phone_number")
    private String publisherPhoneNumber;

    @Column(name = "publish_time")
    private LocalDateTime publishTime; // 招聘发布日期时间

    @Column(name = "recruitment_deadline") // 招聘截止时间
    private LocalDateTime recruitmentDeadline;

    @Column(name = "publish_title")
    private String publishTitle; // 招聘标题

    @Column(name = "brief_introduction")
    private String briefIntroduction; // 简介

    @Column(name = "max_monthly_salary")
    private Integer maxMonthlySalary;

    @Column(name = "min_monthly_salary")
    private Integer minMonthlySalary;

    @Column(name = "number_of_deliveries")
    private Integer numberOfDeliveries;

    @Column(name = "number_of_recruits")
    private Integer getNumberOfDeliveries;

    @Column(name = "status")
    private Integer status; // 0：有效（默认值） 1：无效

    public Recruitment(RecruitmentEditDto recruitmentEditDto, Recruitment recruitment){
        this.recruitmentId = recruitmentEditDto.getRecruitmentId();
        this.publisherAccount = recruitmentEditDto.getPublisherAccount();
        this.publisherPhoneNumber = recruitmentEditDto.getPublisherPhoneNumber();
        this.publishTime = recruitment.getPublishTime();

        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern); // 生成时间解析对象
        this.recruitmentDeadline = LocalDateTime.parse(recruitmentEditDto.getRecruitmentDeadlineStr(),formatter);

        this.publishTitle = recruitmentEditDto.getPublishTitle();
        this.briefIntroduction = recruitmentEditDto.getBriefIntroduction();
        this.maxMonthlySalary = recruitmentEditDto.getMaxMonthlySalary();
        this.minMonthlySalary = recruitmentEditDto.getMinMonthlySalary();
        this.numberOfDeliveries = recruitmentEditDto.getNumberOfDeliveries();
        this.getNumberOfDeliveries = recruitmentEditDto.getGetNumberOfDeliveries();
        this.status = recruitment.getStatus();
    }
}
