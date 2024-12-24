package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.RecruitmentPage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IJobTagMappingService {
    public RecruitmentPage getRecruitmentsByTagsIds(List<Long> tagIds, long tagCount, Integer page, Integer size);
}
