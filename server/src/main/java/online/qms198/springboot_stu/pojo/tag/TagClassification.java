package online.qms198.springboot_stu.pojo.tag;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag_classification")
public class TagClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String categoryName;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer status;

//    @OneToMany(mappedBy = "tagClassification", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TagClassificationMapping> mappings = new ArrayList<>();

}
