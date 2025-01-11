package online.qms198.springboot_stu.repository.tag;

import online.qms198.springboot_stu.pojo.tag.TagClassification;
import org.springframework.data.repository.CrudRepository;

public interface TagClassificationRepository extends CrudRepository<TagClassification, Long> {
    boolean existsByCategoryName(String categoryName);

    boolean existsByCategoryNameAndIdNot(String categoryName, Long id);
}
