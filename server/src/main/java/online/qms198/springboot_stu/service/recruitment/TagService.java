package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.dto.tag.TagClassificationDto;
import online.qms198.springboot_stu.pojo.tag.Tag;
import online.qms198.springboot_stu.pojo.tag.TagClassification;
import online.qms198.springboot_stu.pojo.tag.TagClassificationMapping;
import online.qms198.springboot_stu.repository.tag.TagClassificationMappingRepository;
import online.qms198.springboot_stu.repository.tag.TagClassificationRepository;
import online.qms198.springboot_stu.repository.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagService implements ITagService{
    private TagRepository tagRepository;
    private TagClassificationRepository tagClassificationRepository;
    private TagClassificationMappingRepository tagClassificationMappingRepository;
    @Autowired
    public TagService(
            TagRepository tagRepository,
            TagClassificationRepository tagClassificationRepository,
            TagClassificationMappingRepository tagClassificationMappingRepository) {
        this.tagRepository = tagRepository;
        this.tagClassificationRepository = tagClassificationRepository;
        this.tagClassificationMappingRepository = tagClassificationMappingRepository;
    }

    public List<Tag> getTagsByIds(List<Long> tagIds) {
        List<Tag> tags = new ArrayList<>((Collection) tagRepository.findAllById(tagIds));
        // 删除状态位为1的Tag
        tags.removeIf(tag -> tag.getStatus() == 1);
        return tags;
    }



    public Tag createTag(TagClassificationDto tagClassificationDto) {
        // 校验输入参数
        if (tagClassificationDto == null) {
            throw new IllegalArgumentException("TagClassificationDto cannot be null");
        }
        if (tagClassificationDto.getTag() == null) {
            throw new IllegalArgumentException("Tag object cannot be null");
        }
        if (tagClassificationDto.getTagClassificationIds() == null || tagClassificationDto.getTagClassificationIds().isEmpty()) {
            throw new IllegalArgumentException("TagClassification IDs cannot be null or empty");
        }

        Tag tag = tagClassificationDto.getTag();
        List<Long> tagClassificationIds = tagClassificationDto.getTagClassificationIds();

        // 检查数据库中是否存在同名标签
        Tag existingTag = tagRepository.findByName(tag.getName());
        if (existingTag != null) {
            if (existingTag.getStatus() == 1) {
                // 如果标签状态为无效（status=1），则重新激活标签（置为有效）
                existingTag.setStatus(0);
                tagRepository.save(existingTag);
            } else {
                // 如果标签已存在且状态为有效，抛出异常
                throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' already exists.");
            }
        } else {
            // 如果不存在同名标签，创建新标签
            existingTag = new Tag();
            existingTag.setName(tag.getName());
            existingTag.setStatus(tag.getStatus() != null ? tag.getStatus() : 0); // 默认状态为 0
            existingTag = tagRepository.save(existingTag);
        }

        // 获取当前 Tag 的父标签映射
        List<TagClassificationMapping> existingMappings = tagClassificationMappingRepository.findByTagId(existingTag.getId());

        // 将输入的父标签 ID 转为 Set 以便快速查找
        Set<Long> inputClassificationIds = new HashSet<>(tagClassificationIds);

        // 更新现有的映射状态
        for (TagClassificationMapping mapping : existingMappings) {
            if (!inputClassificationIds.contains(mapping.getTagClassification().getId())) {
                // 如果现有映射的父标签不在输入列表中，设置为无效
                mapping.setStatus(1); // 无效
                tagClassificationMappingRepository.save(mapping);
            } else {
                // 如果仍在输入列表中，确保状态为有效
                mapping.setStatus(0); // 有效
                tagClassificationMappingRepository.save(mapping);
                inputClassificationIds.remove(mapping.getTagClassification().getId());
            }
        }

        // 为剩余未关联的父标签创建新的映射
        for (Long newClassificationId : inputClassificationIds) {
            TagClassification tagClassification = tagClassificationRepository.findById(newClassificationId)
                    .orElseThrow(() -> new IllegalArgumentException("TagClassification not found with ID: " + newClassificationId));

            TagClassificationMapping newMapping = new TagClassificationMapping();
            newMapping.setTagClassification(tagClassification);
            newMapping.setTag(existingTag);
            newMapping.setStatus(0); // 设置为有效
            tagClassificationMappingRepository.save(newMapping);
        }

        // 返回创建或激活后的标签
        return existingTag;
    }





    // 根据ID查询Tag
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found with ID: " + id));
    }

    // 更新Tag
    public Tag updateTag(TagClassificationDto tagClassificationDto) {
        // 校验输入参数
        if (tagClassificationDto == null) {
            throw new IllegalArgumentException("TagClassificationDto cannot be null");
        }
        if (tagClassificationDto.getTagId() == null) {
            throw new IllegalArgumentException("Tag ID cannot be null");
        }
        if (tagClassificationDto.getTagClassificationIds() == null || tagClassificationDto.getTagClassificationIds().isEmpty()) {
            throw new IllegalArgumentException("TagClassification IDs cannot be null or empty");
        }
        if (tagClassificationDto.getTag() == null) {
            throw new IllegalArgumentException("Tag object cannot be null");
        }

        Long tagId = tagClassificationDto.getTagId();
        List<Long> tagClassificationIds = tagClassificationDto.getTagClassificationIds();
        Tag tag = tagClassificationDto.getTag();

        boolean isDuplicateName = tagRepository.existsByNameAndIdNot(tag.getName(), tagId);
        if (isDuplicateName) {
            throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' already exists.");
        }

        // 检查并更新 Tag 表中的记录
        Tag existingTag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + tagId));
        existingTag.setName(tag.getName()); // 假设 Tag 中包含 `name` 字段
        existingTag.setStatus(tag.getStatus());
        Tag updatedTag = tagRepository.save(existingTag);

        // 获取当前 Tag 的父标签映射
        List<TagClassificationMapping> existingMappings = tagClassificationMappingRepository.findByTagId(tagId);

        // 将输入的父标签 ID 转为 Set 以便快速查找
        Set<Long> inputClassificationIds = new HashSet<>(tagClassificationIds);

        // 更新现有的映射状态
        for (TagClassificationMapping mapping : existingMappings) {
            if (!inputClassificationIds.contains(mapping.getTagClassification().getId())) {
                // 如果现有映射的父标签不在输入列表中，设置为无效
                mapping.setStatus(1); // 无效
                tagClassificationMappingRepository.save(mapping);
            } else {
                // 如果仍在输入列表中，确保状态为有效
                mapping.setStatus(0); // 有效
                tagClassificationMappingRepository.save(mapping);
                inputClassificationIds.remove(mapping.getTagClassification().getId());
            }
        }

        // 为剩余未关联的父标签创建新的映射
        for (Long newClassificationId : inputClassificationIds) {
            TagClassification tagClassification = tagClassificationRepository.findById(newClassificationId)
                    .orElseThrow(() -> new IllegalArgumentException("TagClassification not found with ID: " + newClassificationId));

            TagClassificationMapping newMapping = new TagClassificationMapping();
            newMapping.setTagClassification(tagClassification);
            newMapping.setTag(updatedTag);
            newMapping.setStatus(0); // 设置为有效
            tagClassificationMappingRepository.save(newMapping);
        }

        // 返回更新后的标签
        return updatedTag;
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
