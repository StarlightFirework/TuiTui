package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.tag.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITagClassificationMappingService {
    List<Tag> findTagsByClassificationIdAndStatusNot(Long tagClassificationId);
}
