package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.dto.recruitment.RecruitmentAuditDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentPageDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentStatisticsDto;
import online.qms198.springboot_stu.dto.user.UserDto;
import online.qms198.springboot_stu.dto.user.UserPageDto;
import online.qms198.springboot_stu.pojo.tag.Tag;
import online.qms198.springboot_stu.pojo.recruitment.*;
import online.qms198.springboot_stu.pojo.user.UserPage;
import online.qms198.springboot_stu.repository.group.RecruitmentRecruitmentGroupMappingRepository;
import online.qms198.springboot_stu.repository.recruitment.RecruitmentUserResumeMappingRepository;
import online.qms198.springboot_stu.repository.tag.JobTagMappingRepository;
import online.qms198.springboot_stu.repository.recruitment.RecruitmentAuditRepository;
import online.qms198.springboot_stu.repository.recruitment.RecruitmentRepository;
import online.qms198.springboot_stu.repository.tag.TagRepository;
import online.qms198.springboot_stu.repository.user.UserRepository;
import online.qms198.springboot_stu.service.group.IRecruitmentRecruitmentGroupMappingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecruitmentService implements IRecruitmentService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Autowired
    private JobTagMappingRepository jobTagMappingRepository;

    @Autowired
    private ITagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private IRecruitmentStatisticsService recruitmentStatisticsService;

    @Autowired
    private RecruitmentAuditRepository recruitmentAuditRepository;

    @Autowired
    private IRecruitmentRecruitmentGroupMappingService recruitmentRecruitmentGroupMappingService;

    @Autowired
    RecruitmentRecruitmentGroupMappingRepository recruitmentRecruitmentGroupMappingRepository;

    @Autowired
    RecruitmentUserResumeMappingRepository recruitmentUserResumeMappingRepository;
    @Override
    public RecruitmentDto getRecruitment(Integer recruitmentId) {
        RecruitmentDto recruitmentDto = new RecruitmentDto(recruitmentRepository.findByRecruitmentId(recruitmentId), jobTagMappingRepository.getTagIdFindByRecruitmentRecruitmentId(recruitmentId));

        // 设置招聘信息圈子账号
        recruitmentDto.setGroupAccounts(recruitmentRecruitmentGroupMappingRepository.findRecruitmentGroups(recruitmentId));

        recruitmentDto.setMinMonthlySalary(recruitmentDto.getMinMonthlySalary()/1000);
        recruitmentDto.setMaxMonthlySalary(recruitmentDto.getMaxMonthlySalary()/1000);

        // 增加该条招聘信息的查询次数
        List<Integer> recruitmentIds = new ArrayList<Integer>();
        recruitmentIds.add(recruitmentId);
        recruitmentStatisticsService.batchUpdateQueryCount(recruitmentIds);

        return recruitmentDto;
    }

//    @Override
//    public RecruitmentDto getRecruitment(Integer recruitmentId, List<Long> tagIds) {
//        return recruitmentRepository.findByRecruitmentId(recruitmentId);
//    }

    // 添加招聘信息
    @Override
    @Transactional
    public Recruitment addRecruitment(RecruitmentDto recruitmentDto) throws Exception {

        Recruitment recruitmentPojo = new Recruitment();
        BeanUtils.copyProperties(recruitmentDto, recruitmentPojo);

        // 薪资异常拦截
        if(recruitmentPojo.getMinMonthlySalary() > recruitmentPojo.getMaxMonthlySalary()){
            throw new Exception("招聘信息的最小薪资不得大于最大薪资！");
        }

        // 设置发布时间
        recruitmentPojo.setPublishTime(LocalDateTime.now());

        // 设置编辑时间
        recruitmentPojo.setEditTime(LocalDateTime.now());

        // 设置截止时间
        recruitmentPojo.setRecruitmentDeadline(recruitmentDto.getRecruitmentDeadline());

        // 设置待审核状态字
        recruitmentPojo.setStatus(2);

        // 设置权限状态字
        if(!recruitmentDto.getGroupAccounts().isEmpty()){
            recruitmentPojo.setPermissionStatus(1);
        }else{
            recruitmentPojo.setPermissionStatus(0);
        }

        // 保存招聘信息
        Recruitment savedRecruitment = recruitmentRepository.save(recruitmentPojo);

        if(!recruitmentDto.getGroupAccounts().isEmpty()){
            // 添加私有招聘信息映射圈子信息
            recruitmentRecruitmentGroupMappingService.batchAddRecruitmentGroupMapping(recruitmentDto.getGroupAccounts(),savedRecruitment.getRecruitmentId());
        }

        if (recruitmentDto.getTagIds() != null && !recruitmentDto.getTagIds().isEmpty()) {
            List<Tag> tags = tagService.getTagsByIds(recruitmentDto.getTagIds());
            saveJobTagMappingsBatch(savedRecruitment, tags);
        }

        return savedRecruitment;
    }

    private void saveJobTagMappingsBatch(Recruitment savedRecruitment, List<Tag> tags) {

        if(tags.isEmpty()){
            return;
        }
        for (Tag tag : tags) {
            JobTagMapping mapping = new JobTagMapping();
            mapping.setRecruitment(savedRecruitment);  // 设置招聘信息
            mapping.setTag(tag);  // 设置标签
            mapping.setStatus(0); // 设置标志位：0 表示有效

            jobTagMappingRepository.save(mapping);  // 保存关联
        }
    }

    @Override
    public RecruitmentPage getRecruitmentsByPage(Integer page , Integer size) {
        Pageable pageable = (Pageable) PageRequest.of(page,size);
        Page<Recruitment> recruitmentPage = recruitmentRepository.findByStatusAndPermissionStatus(0,0,pageable);

        // 获得分页查询的招聘信息
        List<Recruitment> recruitments = recruitmentPage.getContent();
        // 提取分页查询的招聘信息的Id
        List<Integer> recruitmentIds = new ArrayList<>();
        for(Recruitment recruitment : recruitments){
            recruitmentIds.add(recruitment.getRecruitmentId());
            // 处理薪资显示
            recruitment.setMinMonthlySalary(recruitment.getMinMonthlySalary()/1000);
            recruitment.setMaxMonthlySalary(recruitment.getMaxMonthlySalary()/1000);
        }
        // 增加这些招聘信息的查询次数
        recruitmentStatisticsService.batchUpdateQueryCount(recruitmentIds);

        return new RecruitmentPage((int)recruitmentPage.getTotalElements(),recruitmentChangeRecruitmentDto(recruitments));
    }

    @Override
    @Transactional
    public RecruitmentDto editRecruitment(RecruitmentDto recruitmentDto) throws Exception {

        Recruitment recruitmentOld = recruitmentRepository.findByRecruitmentId(recruitmentDto.getRecruitmentId());
        if(recruitmentOld == null){
            throw new Exception("编辑的招聘信息不存在!");
        }

        Recruitment recruitment = new Recruitment(recruitmentDto,recruitmentOld);
        // 设置编辑时间
        recruitment.setEditTime(LocalDateTime.now());
        // 设置状态为审核
        recruitment.setStatus(2);
        // 覆盖原招聘信息
        Recruitment recruitmentNew = recruitmentRepository.save(recruitment);
        // 获取原招聘信息的标签映射对象
        List<JobTagMapping> jobTagMappingOld = jobTagMappingRepository.findByRecruitmentRecruitmentId(recruitmentDto.getRecruitmentId());
        // 提取原招聘信息的和标签映射对象中的标签id
        List<Long> tagsIdOld = new ArrayList<>();
        // 提取新招聘信息的标签id
        List<Long> tagsIdNew = recruitmentDto.getTagIds();
        List<Tag> TagsNew = new ArrayList<>();
        for(JobTagMapping jobTagMapping : jobTagMappingOld){
            tagsIdOld.add(jobTagMapping.getTag().getId());
            jobTagMappingRepository.save(new JobTagMapping(jobTagMapping.getId(),jobTagMapping.getRecruitment(),jobTagMapping.getTag(), tagsIdNew.contains(jobTagMapping.getTag().getId())?0:1));
        }

        for(Long tagId:tagsIdNew){
            if(!tagsIdOld.contains(tagId)){
                TagsNew.add(tagRepository.findById(tagId).orElse(null));
            }
        }

        saveJobTagMappingsBatch(recruitmentNew, TagsNew);
        return new RecruitmentDto(recruitmentOld,tagsIdNew);
    }
    @Override
    @Transactional
    public boolean delete(Integer recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findByRecruitmentId(recruitmentId);
        if(recruitment == null){
            return false;
        }
        recruitment.setStatus(1);
        return recruitmentRepository.save(recruitment) != null;
    }

    @Override
    public RecruitmentPage getAuditRecruitmentsByPage(Integer page , Integer size){
        Pageable pageable = (Pageable) PageRequest.of(page,size).withSort(Sort.Direction.ASC,"editTime");
        Page<Recruitment> recruitmentPage = recruitmentRepository.findByStatus(2,pageable);
        return new RecruitmentPage((int)recruitmentPage.getTotalElements(),recruitmentChangeRecruitmentDto(recruitmentPage.getContent()));
    }

    private List<RecruitmentDto> recruitmentChangeRecruitmentDto(List<Recruitment> recruitments){
        List<RecruitmentDto> recruitmentDtos = new ArrayList<RecruitmentDto>();
        // 将recruitment转为recruitmentDto
        for(Recruitment recruitment : recruitments){
            recruitmentDtos.add(new RecruitmentDto(recruitment,jobTagMappingRepository.getTagIdFindByRecruitmentRecruitmentId(recruitment.getRecruitmentId()) , recruitmentRecruitmentGroupMappingRepository.findRecruitmentGroups(recruitment.getRecruitmentId())));
        }
        return recruitmentDtos;
    }

    public void updateAuditRecruitment(RecruitmentAuditDto recruitmentAuditDto) throws Exception {
        if(recruitmentAuditDto.getAuditCode() == 0){
            recruitmentRepository.updateRecruitmentApproved(recruitmentAuditDto.getRecruitmentId());
        }else{

            if(recruitmentAuditDto.getDescription().isEmpty()){
                throw new Exception("审核驳回未提交理由，审核招聘信息Id: " + recruitmentAuditDto.getRecruitmentId());
            }

            recruitmentRepository.updateRecruitmentReject(recruitmentAuditDto.getRecruitmentId());
            RecruitmentAudit recruitmentAudit = recruitmentAuditRepository.findByRecruitmentId(recruitmentAuditDto.getRecruitmentId());
            if( recruitmentAudit == null ){
                recruitmentAuditRepository.save(new RecruitmentAudit(recruitmentAuditDto,recruitmentRepository.findByRecruitmentId(recruitmentAuditDto.getRecruitmentId())));
                return;
            }
            recruitmentAudit.setDescription(recruitmentAuditDto.getDescription());
            recruitmentAuditRepository.save(recruitmentAudit);
        }
    }

    @Override
    public void addRecruitmentDeliver(RecruitmentStatisticsDto recruitmentStatisticsDto){
        RecruitmentUserResumeMapping recruitmentUserResumeMapping = recruitmentUserResumeMappingRepository.findByRecruitmentIdAndUserAccount(recruitmentStatisticsDto.getRecruitmentId(),recruitmentStatisticsDto.getUserAccount());
        if(recruitmentUserResumeMapping == null){
            recruitmentUserResumeMappingRepository.save(new RecruitmentUserResumeMapping(null,recruitmentRepository.findByRecruitmentId(recruitmentStatisticsDto.getRecruitmentId()),userRepository.findByUserAccount(recruitmentStatisticsDto.getUserAccount()),0));
        }else{
            recruitmentUserResumeMapping.setStatus(0);
            recruitmentUserResumeMappingRepository.save(recruitmentUserResumeMapping);
        }
    }
    @Override
    public void cancelRecruitmentDeliver(RecruitmentStatisticsDto recruitmentStatisticsDto){
        RecruitmentUserResumeMapping recruitmentUserResumeMapping = recruitmentUserResumeMappingRepository.findByRecruitmentIdAndUserAccount(recruitmentStatisticsDto.getRecruitmentId(),recruitmentStatisticsDto.getUserAccount());
        recruitmentUserResumeMapping.setStatus(1);
        recruitmentUserResumeMappingRepository.save(recruitmentUserResumeMapping);
    }
    @Override
    public RecruitmentPage findUserDeliverRecruitment(RecruitmentPageDto recruitmentPageDto){
        Pageable pageable = (Pageable) PageRequest.of(recruitmentPageDto.getPage(),recruitmentPageDto.getSize());
        Page<Recruitment> oldRecruitmentPage = recruitmentUserResumeMappingRepository.findDeliverRecruitmentByUserAccount(recruitmentPageDto.getUserAccount(),pageable);
        return new RecruitmentPage((int)oldRecruitmentPage.getTotalElements() , recruitmentChangeRecruitmentDto(oldRecruitmentPage.getContent()));
    }
    @Override
    public UserPage findRecruitmentDeliverUser(UserPageDto userPageDto){
        Pageable pageable = (Pageable) PageRequest.of(userPageDto.getPage(),userPageDto.getSize());
        Page<UserDto> userDtoPage = recruitmentUserResumeMappingRepository.findRecruitmentDeliverUser(userPageDto.getRecruitmentId(),pageable);
        return new UserPage((int)userDtoPage.getTotalElements(),userDtoPage.getContent());
    }
}
