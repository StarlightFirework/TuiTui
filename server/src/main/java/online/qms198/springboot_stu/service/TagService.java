package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.Tag;
import online.qms198.springboot_stu.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getTagsByIds(List<Long> tagIds) {
        List<Tag> tags = new ArrayList<>((Collection) tagRepository.findAllById(tagIds));
        // 删除状态位为1的Tag
        tags.removeIf(tag -> tag.getStatus() == 1);
        return tags;
    }
//    public Tag getTagById(Long tagId){
//        tagRepository.findById(tagId);
//    }
    public void validateTagsExist(List<Long> tagIds) {
        List<Tag> tags = getTagsByIds(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new IllegalArgumentException("部分标签不存在！");
        }
    }
}
