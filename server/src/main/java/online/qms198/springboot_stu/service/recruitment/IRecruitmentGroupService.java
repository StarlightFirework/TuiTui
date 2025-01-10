package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.dto.recruitment.RecruitmentGroupDto;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentGroup;

public interface IRecruitmentGroupService {
    public RecruitmentGroup addRecruitmentGroup(RecruitmentGroupDto recruitmentGroupDto);

    public RecruitmentGroup editRecruitmentGroup(RecruitmentGroupDto recruitmentGroupDto) throws Exception;

    public void deleteRecruitmentGroup(Integer groupAccount) throws Exception;

    public RecruitmentGroup getVisibleRecruitmentGroup(Integer groupAccount) throws Exception;

    public RecruitmentGroup getRecruitmentGroupByAccount(Integer groupAccount) throws Exception;
}
