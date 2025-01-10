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
@Table(name = "tag")
public class Tag {

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 递增策略
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "status" , columnDefinition = "INT DEFAULT 0")
    private Integer status;

}
