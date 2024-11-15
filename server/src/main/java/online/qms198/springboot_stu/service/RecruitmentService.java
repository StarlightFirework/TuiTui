package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.controller.RecruitmentController;
import online.qms198.springboot_stu.pojo.Recruitment;
import online.qms198.springboot_stu.pojo.dto.RecruitmentDto;
import online.qms198.springboot_stu.pojo.dto.RecruitmentPageDto;
import online.qms198.springboot_stu.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecruitmentService implements IRecruitmentService{

    @Autowired
    RecruitmentRepository recruitmentRepository;
    @Override
    public Recruitment getRecruitment(Integer recruitmentId) {
        return null;
    }

    @Override
    public Recruitment addRecruitment(RecruitmentDto recruitmentDto) {
        return null;
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
