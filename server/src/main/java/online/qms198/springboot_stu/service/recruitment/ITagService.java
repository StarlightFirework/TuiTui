package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.dto.tag.TagClassificationDto;
import online.qms198.springboot_stu.pojo.tag.Tag;

import java.util.List;

public interface ITagService {

    // 根据ID列表获取标签
    List<Tag> getTagsByIds(List<Long> tagIds);

    // 创建标签
    Tag createTag(TagClassificationDto tagClassificationDto);

    // 根据ID获取标签
    Tag getTagById(Long id);

    // 更新标签
    Tag updateTag(TagClassificationDto tagClassificationDto);

    // 删除标签
    void deleteTag(Long id);

    // 模糊搜索标签
    List<Tag> searchTagsByName(String keyword);
}
