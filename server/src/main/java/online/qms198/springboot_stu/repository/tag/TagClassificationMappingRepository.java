package online.qms198.springboot_stu.repository.tag;

import online.qms198.springboot_stu.pojo.tag.TagClassificationMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagClassificationMappingRepository extends CrudRepository<TagClassificationMapping, Long> {
    Optional<TagClassificationMapping> findByTagIdAndTagClassificationId(Long tagId, Long tagClassificationId);

    List<TagClassificationMapping> findByTagId(Long id);
}
