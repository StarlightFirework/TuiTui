package online.qms198.springboot_stu.dto.resume;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private Integer resumeId;
    
    private String userAccount;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String gender;

    private LocalDate birthDate;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phoneNumber;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String education;
    
    private String school;
    
    private String major;
    
    private LocalDate graduationDate;
    
    private String workExperience;
    
    private String skills;
    
    private String selfEvaluation;
} 