package online.qms198.springboot_stu.repository.tag;

import online.qms198.springboot_stu.pojo.tag.Tag;
import online.qms198.springboot_stu.pojo.tag.TagClassification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagClassificationRepository extends CrudRepository<TagClassification, Long> {
    boolean existsByCategoryName(String categoryName);

    boolean existsByCategoryNameAndIdNot(String categoryName, Long id);

    @Query("SELECT tcm.tag FROM TagClassificationMapping tcm WHERE tcm.tagClassification.id = :tagClassificationId")
    List<Tag> findTagsByTagClassificationId(@Param("tagClassificationId") Long tagClassificationId);
}
