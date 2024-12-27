package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.*;
import online.qms198.springboot_stu.pojo.dto.RecruitmentDto;
import online.qms198.springboot_stu.repository.JobTagMappingRepository;
import online.qms198.springboot_stu.repository.RecruitmentRepository;
import online.qms198.springboot_stu.repository.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class RecruitmentService implements IRecruitmentService{

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Autowired
    private JobTagMappingRepository jobTagMappingRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Recruitment getRecruitment(Integer recruitmentId) {
        return recruitmentRepository.findByRecruitmentId(recruitmentId);
    }

    @Override
    public Recruitment getRecruitment(Integer recruitmentId, List<Long> tagIds) {
        return recruitmentRepository.findByRecruitmentId(recruitmentId);
    }

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

        // 设置截止时间
//        String pattern = "yyyy-MM-dd HH:mm:ss";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern); // 生成时间解析对象
        recruitmentPojo.setRecruitmentDeadline(recruitmentDto.getRecruitmentDeadline());

        // 设置有效状态字
        recruitmentPojo.setStatus(0);

        // 保存招聘信息
        Recruitment savedRecruitment = recruitmentRepository.save(recruitmentPojo);

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
        Page<Recruitment> recruitmentPage = recruitmentRepository.findByStatus(0,pageable);
        return new RecruitmentPage((int)recruitmentPage.getTotalElements(),recruitmentPage.getContent());
    }
    @Override
    @Transactional
    public RecruitmentDto editRecruitment(RecruitmentDto recruitmentDto) throws Exception {

        Recruitment recruitmentOld = recruitmentRepository.findByRecruitmentId(recruitmentDto.getRecruitmentId());
        if(recruitmentOld == null){
            throw new Exception("编辑的招聘信息不存在!");
        }

        Recruitment recruitment = new Recruitment(recruitmentDto,recruitmentOld);
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
}
