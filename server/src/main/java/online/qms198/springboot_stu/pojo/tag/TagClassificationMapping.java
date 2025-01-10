package online.qms198.springboot_stu.pojo.tag;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag_classification_mapping")
public class TagClassificationMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 多对一关联到 TagClassification
    @JoinColumn(name = "tag_classification_id", nullable = false)
    private TagClassification tagClassification;

    @ManyToOne(fetch = FetchType.LAZY) // 多对一关联到 Tag
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Column(name = "status" , columnDefinition = "INT DEFAULT 0")
    private Integer status;
}
