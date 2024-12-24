package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.Tag;
import online.qms198.springboot_stu.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getTagsByIds(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    public void validateTagsExist(List<Long> tagIds) {
        List<Tag> tags = getTagsByIds(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new IllegalArgumentException("部分标签不存在！");
        }
    }
}
