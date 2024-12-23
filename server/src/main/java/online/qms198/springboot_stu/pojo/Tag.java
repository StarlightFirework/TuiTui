package online.qms198.springboot_stu.pojo;


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

}
