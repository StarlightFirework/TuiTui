package online.qms198.springboot_stu.service.group;

import online.qms198.springboot_stu.dto.group.RecruitmentGroupDto;
import online.qms198.springboot_stu.pojo.group.RecruitmentGroup;

public interface IRecruitmentGroupService {
    public RecruitmentGroup addRecruitmentGroup(RecruitmentGroupDto recruitmentGroupDto);

    public RecruitmentGroup editRecruitmentGroup(RecruitmentGroupDto recruitmentGroupDto) throws Exception;

    public void deleteRecruitmentGroup(Integer groupAccount) throws Exception;

    public RecruitmentGroup getVisibleRecruitmentGroup(Integer groupAccount) throws Exception;

    public RecruitmentGroup getRecruitmentGroupByAccount(Integer groupAccount) throws Exception;
}
