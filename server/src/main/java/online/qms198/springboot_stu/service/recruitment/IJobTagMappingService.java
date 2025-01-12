package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;

import java.util.List;

public interface IJobTagMappingService {
    public RecruitmentPage getPublicRecruitmentsByTagsIds(List<Long> tagIds, long tagCount, Integer page, Integer size);
}
