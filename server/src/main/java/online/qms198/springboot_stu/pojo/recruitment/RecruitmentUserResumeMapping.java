package online.qms198.springboot_stu.pojo.recruitment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recruitment_user_resume_mapping")
public class RecruitmentUserResumeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account" , referencedColumnName = "user_account")
    private User user;

    @Column(name = "status" , columnDefinition = "INT DEFAULT 0")
    private Integer status;// 状态位：0 表示有效
}
