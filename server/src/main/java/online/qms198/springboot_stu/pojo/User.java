package online.qms198.springboot_stu.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data // 使用注解实现get、set方法
@NoArgsConstructor// 实现 无参construction 方法
@AllArgsConstructor// 实现 有参construction 方法
@Table(name = "tb_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    // 保持一致
    @Column(name = "user_name")
    private String userName;

    @Column(unique = true, name = "user_account")
    private String userAccount;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "user_identity")
    private String userIdentity;

    @Column(name = "status")
    private Integer status;
}
