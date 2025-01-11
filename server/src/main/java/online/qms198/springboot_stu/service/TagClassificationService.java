package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.tag.TagClassification;
import online.qms198.springboot_stu.repository.tag.TagClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagClassificationService implements ITagClassificationService {

    private final TagClassificationRepository tagClassificationRepository;

    @Autowired
    public TagClassificationService(TagClassificationRepository tagClassificationRepository) {
        this.tagClassificationRepository = tagClassificationRepository;
    }

    /**
     * 创建新的分类
     *
     * @param tagClassification 分类实体
     * @return 保存后的分类实体
     */
    @Override
    public TagClassification createTagClassification(TagClassification tagClassification) {
        if (tagClassification == null || tagClassification.getCategoryName() == null) {
            throw new IllegalArgumentException("Category name cannot be null.");
        }

        // 检查是否已存在同名分类
        boolean exists = tagClassificationRepository.existsByCategoryName(tagClassification.getCategoryName());
        if (exists) {
            throw new IllegalArgumentException("TagClassification with the same name already exists.");
        }

        // 设置默认状态为 0（有效）
        if (tagClassification.getStatus() == null) {
            tagClassification.setStatus(0);
        }

        return tagClassificationRepository.save(tagClassification);
    }

    /**
     * 更新分类
     *
     * @param tagClassification 分类实体
     * @return 更新后的分类实体
     */
    @Override
    public TagClassification updateTagClassification(TagClassification tagClassification) {
        if (tagClassification == null || tagClassification.getId() == null) {
            throw new IllegalArgumentException("TagClassification ID cannot be null.");
        }

        // 查找现有分类
        TagClassification existing = tagClassificationRepository.findById(tagClassification.getId())
                .orElseThrow(() -> new IllegalArgumentException("TagClassification not found with ID: " + tagClassification.getId()));

        // 更新名称
        if (tagClassification.getCategoryName() != null) {
            // 检查是否存在同名但不同 ID 的分类
            boolean exists = tagClassificationRepository.existsByCategoryNameAndIdNot(
                    tagClassification.getCategoryName(),
                    tagClassification.getId()
            );
            if (exists) {
                throw new IllegalArgumentException("Another TagClassification with the same name already exists.");
            }
            existing.setCategoryName(tagClassification.getCategoryName());
        }

        // 更新状态
        if (tagClassification.getStatus() != null) {
            existing.setStatus(tagClassification.getStatus());
        }

        return tagClassificationRepository.save(existing);
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    @Override
    public void deleteTagClassification(Long id) {
        if (!tagClassificationRepository.existsById(id)) {
            throw new IllegalArgumentException("TagClassification not found with ID: " + id);
        }
        tagClassificationRepository.deleteById(id);
    }

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    @Override
    public List<TagClassification> getAllTagClassifications() {
        return (List<TagClassification>) tagClassificationRepository.findAll();
    }

    /**
     * 根据ID获取分类
     *
     * @param id 分类ID
     * @return 分类实体
     */
    @Override
    public TagClassification getTagClassificationById(Long id) {
        return tagClassificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TagClassification not found with ID: " + id));
    }
}
