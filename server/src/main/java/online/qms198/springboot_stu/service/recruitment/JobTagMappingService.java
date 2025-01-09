package online.qms198.springboot_stu.service.recruitment;


import online.qms198.springboot_stu.dto.recruitment.RecruitmentDto;
import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;
import online.qms198.springboot_stu.repository.JobTagMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobTagMappingService implements IJobTagMappingService {

    @Autowired
    private JobTagMappingRepository jobTagMappingRepository;

    @Autowired
    private RecruitmentStatisticsService recruitmentStatisticsService;

    @Override
    public RecruitmentPage getRecruitmentsByTagsIds(List<Long> tagIds, long tagCount, Integer page, Integer size) {
        Pageable pageable = (Pageable) PageRequest.of(page,size);
        Page<Recruitment> recruitmentPage =  jobTagMappingRepository.findRecruitmentsByTagIds(tagIds,tagCount,pageable);

        // 获得分页查询的招聘信息
        List<Recruitment> recruitments = recruitmentPage.getContent();
        // 提取分页查询的招聘信息的Id
        List<Integer> recruitmentIds = new ArrayList<>();
        for(Recruitment recruitment : recruitments){
            recruitmentIds.add(recruitment.getRecruitmentId());
        }
        // 增加这些招聘信息的查询次数
        recruitmentStatisticsService.batchUpdateQueryCount(recruitmentIds);

        return new RecruitmentPage((int)recruitmentPage.getTotalElements(),recruitmentChangeRecruitmentDto(recruitments));
    }

    private List<RecruitmentDto> recruitmentChangeRecruitmentDto(List<Recruitment> recruitments){
        List<RecruitmentDto> recruitmentDtos = new ArrayList<RecruitmentDto>();
        // 将recruitment转为recruitmentDto
        for(Recruitment recruitment : recruitments){
            recruitmentDtos.add(new RecruitmentDto(recruitment,jobTagMappingRepository.getTagIdFindByRecruitmentRecruitmentId(recruitment.getRecruitmentId())));
        }
        return recruitmentDtos;
    }
}
