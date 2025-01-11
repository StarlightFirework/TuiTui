package online.qms198.springboot_stu.dto.tag;

import online.qms198.springboot_stu.pojo.tag.Tag;
import online.qms198.springboot_stu.pojo.tag.TagClassification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagClassificationWithTagsDto {
    private TagClassification tagClassification;  // 大类标签信息
    private List<Tag> tags;  // 小标签列表
}
