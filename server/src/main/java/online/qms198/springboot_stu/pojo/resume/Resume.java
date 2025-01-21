package online.qms198.springboot_stu.pojo.resume;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Integer resumeId;

    @Column(name = "user_account", nullable = false, insertable = false, updatable = false)
    private String userAccount;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "education")
    private String education; // 最高学历

    @Column(name = "school")
    private String school; // 毕业院校

    @Column(name = "major")
    private String major; // 专业

    @Column(name = "graduation_date")
    private LocalDate graduationDate;

    @Column(name = "work_experience", columnDefinition = "TEXT")
    private String workExperience;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills;

    @Column(name = "self_evaluation", columnDefinition = "TEXT")
    private String selfEvaluation;

    @Column(name = "create_time")
    private LocalDate createTime;

    @Column(name = "update_time")
    private LocalDate updateTime;

    @Column(name = "status", columnDefinition = "INT DEFAULT 0")
    private Integer status; // 0-正常 1-删除

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_account", referencedColumnName = "user_account")
    private User user;
} 