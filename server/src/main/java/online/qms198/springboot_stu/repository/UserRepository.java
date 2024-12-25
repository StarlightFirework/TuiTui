package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository // spring bean
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserAccount(String userAccount);

    User findByUserId(Integer userId);
}
