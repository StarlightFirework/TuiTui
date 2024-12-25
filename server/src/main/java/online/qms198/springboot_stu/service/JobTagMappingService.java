package online.qms198.springboot_stu.service;


import online.qms198.springboot_stu.pojo.RecruitmentPage;
import online.qms198.springboot_stu.repository.JobTagMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobTagMappingService implements IJobTagMappingService {

    @Autowired
    private JobTagMappingRepository jobTagMappingRepository;

    @Override
    public RecruitmentPage getRecruitmentsByTagsIds(List<Long> tagIds, long tagCount, Integer page, Integer size) {
        Pageable pageable = (Pageable) PageRequest.of(page,size);
        return new RecruitmentPage((int)jobTagMappingRepository.findRecruitmentsByTagIds(tagIds,tagCount,pageable).getTotalElements(),jobTagMappingRepository.findRecruitmentsByTagIds(tagIds,tagCount,pageable).getContent());
    }
}
