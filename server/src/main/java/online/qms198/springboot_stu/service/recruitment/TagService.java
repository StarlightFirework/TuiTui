package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.pojo.recruitment.Tag;
import online.qms198.springboot_stu.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public Tag createTag(Tag tag) {
        tag.setStatus(0);
        return tagRepository.save(tag);
    }

    // 根据ID查询Tag
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found with ID: " + id));
    }

    // 更新Tag
    public Tag updateTag(Long id, Tag updatedTag) {
        Tag tag = getTagById(id);
        tag.setName(updatedTag.getName());
        return tagRepository.save(tag);
    }

    // 删除Tag
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found with ID: " + id);
        }
        tagRepository.deleteById(id);
    }

    // 模糊搜索Tag
    public List<Tag> searchTagsByName(String keyword) {
        return tagRepository.findByNameContaining(keyword);
    }
}
