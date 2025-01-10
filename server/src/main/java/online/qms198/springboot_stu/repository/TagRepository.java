package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.tag.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    @Query("select t from Tag t where t.id = :id")
    Optional<Tag> findById(@Param("id") Long id);

    List<Tag> findByNameContaining(String keyword); // 根据名称模糊查询

    boolean existsByNameAndIdNot(String name, Long tagId);

    boolean existsByName(String name);

    Tag findByName(String name);
}
