package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
