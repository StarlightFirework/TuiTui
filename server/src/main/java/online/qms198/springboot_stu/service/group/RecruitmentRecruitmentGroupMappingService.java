package online.qms198.springboot_stu.service.group;

import online.qms198.springboot_stu.dto.recruitment.RecruitmentDto;
import online.qms198.springboot_stu.repository.tag.JobTagMappingRepository;
import online.qms198.springboot_stu.service.recruitment.RecruitmentStatisticsService;
import org.springframework.data.domain.Page;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentPageDto;
import online.qms198.springboot_stu.pojo.group.RecruitmentRecruitmentGroupMapping;
import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;
import online.qms198.springboot_stu.repository.group.RecruitmentGroupRepository;
import online.qms198.springboot_stu.repository.group.RecruitmentRecruitmentGroupMappingRepository;
import online.qms198.springboot_stu.repository.group.UserGroupMappingRepository;
import online.qms198.springboot_stu.repository.recruitment.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruitmentRecruitmentGroupMappingService implements IRecruitmentRecruitmentGroupMappingService{

    @Autowired
    RecruitmentRecruitmentGroupMappingRepository recruitmentRecruitmentGroupMappingRepository;

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Autowired
    RecruitmentGroupRepository recruitmentGroupRepository;

    @Autowired
    UserGroupMappingRepository userGroupMappingRepository;

    @Autowired
    JobTagMappingRepository jobTagMappingRepository;

    @Autowired
    RecruitmentStatisticsService recruitmentStatisticsService;
    @Override
    @Transactional
    public void batchAddRecruitmentGroupMapping(List<Integer> groupAccounts , Integer recruitmentId){
        Recruitment recruitment = recruitmentRepository.findByRecruitmentId(recruitmentId);
        for (Integer groupAccount : groupAccounts) {

            RecruitmentRecruitmentGroupMapping recruitmentRecruitmentGroupMapping = recruitmentRecruitmentGroupMappingRepository.findRecruitmentRecruitmentGroupMappingByRecruitmentIdAndGroupAccount(recruitmentId,groupAccount);
            if(recruitmentRecruitmentGroupMapping == null){
                recruitmentRecruitmentGroupMappingRepository.save(new RecruitmentRecruitmentGroupMapping(null,recruitment,recruitmentGroupRepository.findByGroupAccount(groupAccount),0));
            }else{
                recruitmentRecruitmentGroupMapping.setStatus(0);
                recruitmentRecruitmentGroupMappingRepository.save(recruitmentRecruitmentGroupMapping);
            }

        }
    }

    @Override
    public RecruitmentPage findUserGroupRecruitment(RecruitmentPageDto recruitmentPageDto){
        // 查询出用户所在的所有圈子账号
        List<Integer> groupAccounts = userGroupMappingRepository.findUserGroupAccount(recruitmentPageDto.getUserAccount());
        // 再分页查询圈子中的所有招聘分页信息
        Pageable pageable = (Pageable)PageRequest.of(recruitmentPageDto.getPage(),recruitmentPageDto.getSize());
        Page<Recruitment> recruitmentsPage = recruitmentRecruitmentGroupMappingRepository.findUserGroupRecruitments(groupAccounts,pageable);

        // 提取出招聘信息并转换为Dto类
        List<Recruitment> recruitments = recruitmentsPage.getContent();
        List<RecruitmentDto> recruitmentDtos = new ArrayList<>();

        List<Integer> recruitmentIds = new ArrayList<>();

        for(Recruitment recruitment : recruitments){
            recruitmentIds.add(recruitment.getRecruitmentId());
            recruitmentDtos.add(new RecruitmentDto(recruitment,jobTagMappingRepository.getTagIdFindByRecruitmentRecruitmentId(recruitment.getRecruitmentId()) , recruitmentRecruitmentGroupMappingRepository.findRecruitmentGroups(recruitment.getRecruitmentId())));
        }

        // 增加查询次数
        recruitmentStatisticsService.batchUpdateQueryCount(recruitmentIds);

        return new RecruitmentPage((int)recruitmentsPage.getTotalElements(),recruitmentDtos);
    }

    @Override
    @Transactional
    public void batchDeleteRecruitmentGroupMapping(List<Integer> groupAccounts , Integer recruitmentId){
        Recruitment recruitment = recruitmentRepository.findByRecruitmentId(recruitmentId);
        for(Integer groupAccount : groupAccounts){
            RecruitmentRecruitmentGroupMapping recruitmentRecruitmentGroupMapping = recruitmentRecruitmentGroupMappingRepository.findRecruitmentRecruitmentGroupMappingByRecruitmentIdAndGroupAccount(recruitmentId,groupAccount);
            if(recruitmentRecruitmentGroupMapping == null){
                continue;
            }
            recruitmentRecruitmentGroupMapping.setStatus(1);
            recruitmentRecruitmentGroupMappingRepository.save(recruitmentRecruitmentGroupMapping);
        }
    }

}
