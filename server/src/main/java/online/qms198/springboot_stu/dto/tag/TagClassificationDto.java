package online.qms198.springboot_stu.dto.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.qms198.springboot_stu.pojo.tag.Tag;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagClassificationDto {
    @NotBlank(message = "不能为空")
    private List<Long> tagClassificationIds;
    private Tag tag;
    private Long tagId;
}
