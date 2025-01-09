package online.qms198.springboot_stu.pojo.recruitment;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentAuditDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recruitment_audit")
public class RecruitmentAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @Column(name = "description")
    private String description;

    @Column(name = "status" , columnDefinition = "INT DEFAULT 0")
    private Integer status;

    public RecruitmentAudit(RecruitmentAuditDto recruitmentAuditDto , Recruitment recruitment){
        this.description = recruitmentAuditDto.getDescription();
        this.recruitment = recruitment;
        this.status = 0;
    }
}
