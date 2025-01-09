package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.dto.recruitment.RecruitmentAuditDto;
import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentDto;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;

public interface IRecruitmentService {

    // 通过id查询单条记录
    RecruitmentDto getRecruitment(Integer recruitmentId);

//    RecruitmentDto getRecruitment(Integer recruitmentId, List<Long> tagIds);

    // 增加
    Recruitment addRecruitment(RecruitmentDto recruitmentDto) throws Exception;

    // 通过id分页查询记录
    RecruitmentPage getRecruitmentsByPage(Integer page, Integer size);
    // 修改
    RecruitmentDto editRecruitment(RecruitmentDto recruitmentEditDto) throws Exception;

    // 删除
    public boolean delete(Integer recruitmentId);

    // 获取待审核的招聘信息
    public RecruitmentPage getAuditRecruitmentsByPage(Integer page , Integer size);

    // 设置招聘信息审核结果
    public void updateAuditRecruitment(RecruitmentAuditDto recruitmentAuditDto);

}
