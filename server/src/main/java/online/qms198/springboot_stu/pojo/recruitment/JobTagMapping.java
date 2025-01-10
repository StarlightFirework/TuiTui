package online.qms198.springboot_stu.pojo.recruitment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.tag.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_tag_mapping")
public class JobTagMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column(name = "status" , columnDefinition = "INT DEFAULT 0")
    private Integer status;
}