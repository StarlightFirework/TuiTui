package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.JobTagMapping;
import online.qms198.springboot_stu.pojo.Recruitment;
import online.qms198.springboot_stu.pojo.Tag;
import online.qms198.springboot_stu.pojo.dto.RecruitmentDto;
import online.qms198.springboot_stu.pojo.RecruitmentPage;
import online.qms198.springboot_stu.repository.JobTagMappingRepository;
import online.qms198.springboot_stu.repository.RecruitmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@Service
public class RecruitmentService implements IRecruitmentService{

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Autowired
    private JobTagMappingRepository jobTagMappingRepository;

    @Autowired
    private TagService tagService;

    @Override
    public Recruitment getRecruitment(Integer recruitmentId) {
        return null;
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

        if(recruitmentPojo.getMinMonthlySalary() > recruitmentPojo.getMaxMonthlySalary()){
            throw new Exception("招聘信息的最小薪资不得大于最大薪资！");
        }

        // 设置发布时间
        recruitmentPojo.setPublishTime(LocalDateTime.now());

        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern); // 生成时间解析对象
        recruitmentPojo.setRecruitmentDeadline(LocalDateTime.parse(recruitmentDto.getRecruitmentDeadlineStr(),formatter));

        Recruitment savedRecruitment = recruitmentRepository.save(recruitmentPojo);

        if (recruitmentDto.getTagIds() != null && !recruitmentDto.getTagIds().isEmpty()) {
            List<Tag> tags = tagService.getTagsByIds(recruitmentDto.getTagIds());
            saveJobTagMappingsBatch(savedRecruitment, tags);
        }

        return savedRecruitment;
    }

    private void saveJobTagMappingsBatch(Recruitment savedRecruitment, List<Tag> tags) {
        for (Tag tag : tags) {
            JobTagMapping mapping = new JobTagMapping();
            mapping.setRecruitment(savedRecruitment);  // 设置招聘信息
            mapping.setTag(tag);  // 设置标签
            jobTagMappingRepository.save(mapping);  // 保存关联
        }
    }
    @Override
    public RecruitmentPage getRecruitmentsByPage(Integer page , Integer size) {
        Pageable pageable = (Pageable) PageRequest.of(page,size);
        Page<Recruitment> recruitmentPage = recruitmentRepository.findAll(pageable);
        return new RecruitmentPage((int)recruitmentPage.getTotalElements(),recruitmentPage.getContent());
    }

//    @Override
//    public RecruitmentPage getRecruitmentsByTagIds(Integer page , Integer size) {
//        Pageable pageable = (Pageable) PageRequest.of(page,size);
//        Page<Recruitment> recruitmentPage = recruitmentRepository.findAll(pageable);
//        return new RecruitmentPage((int)recruitmentPage.getTotalElements(),recruitmentPage.getContent());
//    }

    @Override
    public Recruitment editRecruitment(RecruitmentDto recruitmentDto) {
        return null;
    }

    @Override
    public void delete(Integer recruitmentId) {

    }
}
