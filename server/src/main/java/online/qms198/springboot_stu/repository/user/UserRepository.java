package online.qms198.springboot_stu.repository.user;

import online.qms198.springboot_stu.pojo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // spring bean
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserAccount(String userAccount);

    User findByUserId(Integer userId);

    List<User> findAllByStatus(Integer status);


}
