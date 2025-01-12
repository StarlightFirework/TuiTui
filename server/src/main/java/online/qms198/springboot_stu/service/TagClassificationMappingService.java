package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.tag.Tag;
import online.qms198.springboot_stu.repository.tag.TagClassificationMappingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagClassificationMappingService implements ITagClassificationMappingService {

    private final TagClassificationMappingRepository repository;

    public TagClassificationMappingService(TagClassificationMappingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tag> findTagsByClassificationIdAndStatusNot(Long tagClassificationId) {
        return repository.findTagsByClassificationIdAndStatusNot(tagClassificationId);
    }
}
