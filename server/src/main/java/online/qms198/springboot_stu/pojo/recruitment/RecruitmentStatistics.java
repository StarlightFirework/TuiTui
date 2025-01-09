package online.qms198.springboot_stu.pojo.recruitment;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recruitment_statistics")
public class RecruitmentStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "query_count" , columnDefinition = "bigint DEFAULT 0 CHECK (query_count >= 0)")
    private Long queryCount;

    @Column(name = "view_count" , columnDefinition = "bigint DEFAULT 0 CHECK (view_count >= 0)")
    private Long viewCount;

    @Column(name = "collection_count" , columnDefinition = "bigint DEFAULT 0 CHECK (collection_count >= 0)")
    private Long collectionCount;

    @Column(name = "heat" , columnDefinition = "INT DEFAULT 0")
    private Integer heat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;
}