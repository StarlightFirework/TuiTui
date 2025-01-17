package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.dto.recruitment.RecruitmentDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentPageDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentStatisticsDto;
import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentCollectionsMapping;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;
import online.qms198.springboot_stu.repository.group.RecruitmentRecruitmentGroupMappingRepository;
import online.qms198.springboot_stu.repository.recruitment.RecruitmentCollectionsMappingRepository;
import online.qms198.springboot_stu.repository.recruitment.RecruitmentRepository;
import online.qms198.springboot_stu.repository.recruitment.RecruitmentStatisticsRepository;
import online.qms198.springboot_stu.repository.tag.JobTagMappingRepository;
import online.qms198.springboot_stu.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitmentStatisticsService implements IRecruitmentStatisticsService{
    @Autowired
    private RecruitmentRepository recruitmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecruitmentStatisticsRepository recruitmentStatisticsRepository;
    @Autowired
    RecruitmentCollectionsMappingRepository recruitmentCollectionsMappingRepository;
    @Autowired
    JobTagMappingRepository jobTagMappingRepository;
    @Autowired
    RecruitmentRecruitmentGroupMappingRepository recruitmentRecruitmentGroupMappingRepository;
    @Override
    public void batchUpdateQueryCount(List<Integer> recruitmentIds) {
        for(Integer recruitmentId : recruitmentIds){
            recruitmentStatisticsRepository.updateQueryCount(recruitmentId);
        }
    }

    @Override
    public void updateViewCount(Integer recruitmentId){
        recruitmentStatisticsRepository.updateViewCount(recruitmentId);
    }

    @Override
    public void addCollection(RecruitmentStatisticsDto recruitmentStatisticsDto){
        RecruitmentCollectionsMapping recruitmentCollectionsMapping = recruitmentCollectionsMappingRepository.findByRecruitmentIdAndUserAccount(recruitmentStatisticsDto.getRecruitmentId(),recruitmentStatisticsDto.getUserAccount());
        if(recruitmentCollectionsMapping == null){
            recruitmentCollectionsMappingRepository.save(new RecruitmentCollectionsMapping(null,recruitmentRepository.findByRecruitmentId(recruitmentStatisticsDto.getRecruitmentId()),userRepository.findByUserAccount(recruitmentStatisticsDto.getUserAccount()),0));
        }else{
            recruitmentCollectionsMapping.setStatus(0);
            recruitmentCollectionsMappingRepository.save(recruitmentCollectionsMapping);
        }
    }

    @Override
    public void cancelCollection(RecruitmentStatisticsDto recruitmentStatisticsDto) {
        RecruitmentCollectionsMapping recruitmentCollectionsMapping = recruitmentCollectionsMappingRepository.findByRecruitmentIdAndUserAccount(recruitmentStatisticsDto.getRecruitmentId(),recruitmentStatisticsDto.getUserAccount());
        recruitmentCollectionsMapping.setStatus(1);
        recruitmentCollectionsMappingRepository.save(recruitmentCollectionsMapping);
    }

    @Override
    public Integer findCollectionCount(Integer recruitmentId){
        return recruitmentStatisticsRepository.findCollectionCount(recruitmentId);
    }

    public RecruitmentPage findCollectionRecruitments(RecruitmentPageDto recruitmentPageDto){
        Pageable pageable = (Pageable) PageRequest.of(recruitmentPageDto.getPage(),recruitmentPageDto.getSize());
        Page<Recruitment> oldRecruitmentPage = recruitmentCollectionsMappingRepository.findRecruitmentByUserAccount(recruitmentPageDto.getUserAccount(),pageable);
        return new RecruitmentPage((int)oldRecruitmentPage.getTotalElements() , recruitmentChangeRecruitmentDto(oldRecruitmentPage.getContent()));
    }
    private List<RecruitmentDto> recruitmentChangeRecruitmentDto(List<Recruitment> recruitments){
        List<RecruitmentDto> recruitmentDtos = new ArrayList<RecruitmentDto>();
        // 将recruitment转为recruitmentDto
        for(Recruitment recruitment : recruitments){
            recruitmentDtos.add(new RecruitmentDto(recruitment,jobTagMappingRepository.getTagIdFindByRecruitmentRecruitmentId(recruitment.getRecruitmentId()) , recruitmentRecruitmentGroupMappingRepository.findRecruitmentGroups(recruitment.getRecruitmentId())));
        }
        return recruitmentDtos;
    }
}