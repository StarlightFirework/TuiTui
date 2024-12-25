package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.Recruitment;
import online.qms198.springboot_stu.pojo.dto.RecruitmentDto;
import online.qms198.springboot_stu.pojo.RecruitmentPage;
import online.qms198.springboot_stu.pojo.dto.RecruitmentEditDto;
import online.qms198.springboot_stu.pojo.dto.RecruitmentTagsDto;

import java.util.List;

public interface IRecruitmentService {

    // 通过id查询单条记录
    Recruitment getRecruitment(Integer recruitmentId);

    Recruitment getRecruitment(Integer recruitmentId, List<Long> tagIds);

    // 增加
    Recruitment addRecruitment(RecruitmentDto recruitmentDto) throws Exception;

    // 通过id分页查询记录
    RecruitmentPage getRecruitmentsByPage(Integer page, Integer size);
    // 修改
    RecruitmentTagsDto editRecruitment(RecruitmentEditDto recruitmentEditDto) throws Exception;

    // 删除
    public boolean delete(Integer recruitmentId);
}
