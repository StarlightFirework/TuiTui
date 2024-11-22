package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.Recruitment;
import online.qms198.springboot_stu.pojo.dto.RecruitmentDto;
import online.qms198.springboot_stu.pojo.dto.RecruitmentPageDto;
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


@Service
public class RecruitmentService implements IRecruitmentService{

    @Autowired
    RecruitmentRepository recruitmentRepository;
    @Override
    public Recruitment getRecruitment(Integer recruitmentId) {
        return recruitmentRepository.findByRecruitmentId(recruitmentId);
    }

    @Override
    @Transactional
    public Recruitment addRecruitment(RecruitmentDto recruitmentDto) throws DateTimeParseException{
        Recruitment recruitmentPojo = new Recruitment();
        BeanUtils.copyProperties(recruitmentDto, recruitmentPojo);
        recruitmentPojo.setPublishTime(LocalDateTime.now());

        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern); // 生成时间解析对象

        recruitmentPojo.setRecruitmentDeadline(LocalDateTime.parse(recruitmentDto.getRecruitmentDeadlineStr(),formatter));

        return recruitmentRepository.save(recruitmentPojo);
    }

    @Override
    public RecruitmentPageDto getRecruitments(Integer page ,Integer size) {
        Pageable pageable = (Pageable) PageRequest.of(page,size);
        Page<Recruitment> recruitmentPage = recruitmentRepository.findAll(pageable);
        return new RecruitmentPageDto((int)recruitmentPage.getTotalElements(),recruitmentPage.getContent());
    }

    @Override
    public Recruitment editRecruitment(RecruitmentDto recruitmentDto) {
        return null;
    }

    @Override
    public void delete(Integer recruitmentId) {

    }
}
