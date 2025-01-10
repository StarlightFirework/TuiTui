package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.tag.TagClassification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITagClassificationService {
    TagClassification createTagClassification(TagClassification tagClassification);
    TagClassification updateTagClassification(TagClassification tagClassification);
    void deleteTagClassification(Long id);
    List<TagClassification> getAllTagClassifications();
    TagClassification getTagClassificationById(Long id);
}
