package online.qms198.springboot_stu.dto.recruitment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentPageDto {

    private Integer page;
    private Integer size;
    private List<Long> tagIds;
}
