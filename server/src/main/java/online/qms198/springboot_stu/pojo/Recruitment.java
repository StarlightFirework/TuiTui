package online.qms198.springboot_stu.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

}
