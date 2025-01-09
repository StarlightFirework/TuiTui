package online.qms198.springboot_stu.pojo.recruitment;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recruitment_group")
public class RecruitmentGroup {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "group_account")
    private Integer groupAccount;

    @Column(name = "status" , columnDefinition = "INT DEFAULT 0")
    private Integer status;
}
