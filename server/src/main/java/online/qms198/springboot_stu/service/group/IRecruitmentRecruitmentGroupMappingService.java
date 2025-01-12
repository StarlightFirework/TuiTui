package online.qms198.springboot_stu.service.group;

import online.qms198.springboot_stu.dto.recruitment.RecruitmentPageDto;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;

import java.util.List;

public interface IRecruitmentRecruitmentGroupMappingService {
    public void batchAddRecruitmentGroupMapping(List<Integer> groupAccounts , Integer recruitmentId);

    public RecruitmentPage findUserGroupRecruitment(RecruitmentPageDto recruitmentPageDto);

    public void batchDeleteRecruitmentGroupMapping(List<Integer> groupAccounts , Integer recruitmentId);
}
