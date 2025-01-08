package online.qms198.springboot_stu.pojo.recruitment;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 递增策略
    private Long id;

    @Column(nullable = false, length = 50) // 不为空 长度50
    private String name;

    @Column(name = "status" , columnDefinition = "INT DEFAULT 0")
    private Integer status;

}
