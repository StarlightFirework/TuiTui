package online.qms198.springboot_stu.repository.group;

import online.qms198.springboot_stu.dto.group.GroupUserDto;
import online.qms198.springboot_stu.dto.group.RecruitmentGroupDto;
import online.qms198.springboot_stu.pojo.group.UserRecruitmentGroupMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupMappingRepository extends JpaRepository<UserRecruitmentGroupMapping,Integer> {
    @Query("select ug.recruitmentGroup.groupAccount from UserRecruitmentGroupMapping ug where ug.user.userAccount = :userAccount and ug.deleteStatus = 0")
    public List<Integer> findUserGroupAccount(String userAccount);

    @Query("select ug from UserRecruitmentGroupMapping ug where ug.recruitmentGroup.groupAccount = :groupAccount and ug.user.userAccount = :userAccount")
    public UserRecruitmentGroupMapping findByGroupAccountAndUserAccount(Integer groupAccount , String userAccount);

    @Query("select new online.qms198.springboot_stu.dto.group.GroupUserDto (u.userName, u.userAccount, u.email, u.userIdentity, ugp.roleStatus) from UserRecruitmentGroupMapping ugp left join ugp.user u on ugp.user.userAccount = u.userAccount where ugp.recruitmentGroup.groupAccount = :groupAccount and ugp.deleteStatus = 0 and u.status = 0")
    public Page<GroupUserDto> findGroupUsersByGroupAccountPage(Integer groupAccount , Pageable pageable);

    @Modifying
    @Query("update UserRecruitmentGroupMapping ugm set ugm.roleStatus = :roleStatus where ugm.user.userAccount = :userAccount and ugm.recruitmentGroup.groupAccount = :groupAccount and ugm.deleteStatus = 0")
    public void updateUserGroupMappingRoleStatus(String userAccount , Integer groupAccount , Integer roleStatus);

    @Query("select new online.qms198.springboot_stu.dto.group.GroupUserDto (u.userName, u.userAccount, u.email, u.userIdentity, ugp.roleStatus) from UserRecruitmentGroupMapping ugp left join ugp.user u on ugp.user.userAccount = u.userAccount where ugp.recruitmentGroup.groupAccount = :groupAccount and ugp.deleteStatus = 0 and u.status = 0 and ugp.roleStatus = 1")
    public List<GroupUserDto> findGroupAdminsByGroupAccount(Integer groupAccount);

    @Query("select new online.qms198.springboot_stu.dto.group.RecruitmentGroupDto (rg.groupAccount,rg.creatorAccount,rg.groupName,rg.visibleStatus) from UserRecruitmentGroupMapping urg left join urg.recruitmentGroup rg on urg.recruitmentGroup.groupAccount = rg.groupAccount where urg.deleteStatus = 0 and urg.user.userAccount = :userAccount")
    public Page<RecruitmentGroupDto> findUserRecruitmentGroup(String userAccount , Pageable pageable);
}
