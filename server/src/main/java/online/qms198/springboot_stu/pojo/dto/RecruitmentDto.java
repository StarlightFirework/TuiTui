package online.qms198.springboot_stu.pojo.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

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

    private String publishTitle; // 招聘标题

    private String briefIntroduction; // 简介

    private Integer maxMonthlySalary;

    private Integer minMonthlySalary;

    private Integer numberOfDeliveries;

    private Integer getNumberOfDeliveries;
}
