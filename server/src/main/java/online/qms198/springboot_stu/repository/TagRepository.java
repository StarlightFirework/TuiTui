package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.recruitment.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findByName(String name);

    @Query("select t from Tag t where t.id = :id")
    Optional<Tag> findById(@Param("id") Long id);
}
