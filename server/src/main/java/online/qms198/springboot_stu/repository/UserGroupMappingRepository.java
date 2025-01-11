package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.dto.group.GroupUser;
import online.qms198.springboot_stu.pojo.group.UserRecruitmentGroupMapping;
import online.qms198.springboot_stu.pojo.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupMappingRepository extends JpaRepository<UserRecruitmentGroupMapping,Integer> {
    @Query("select ug from UserRecruitmentGroupMapping ug where ug.recruitmentGroup.groupAccount = :groupAccount")
    public List<UserRecruitmentGroupMapping> findByGroupAccount(Integer groupAccount);

    @Query("select ug from UserRecruitmentGroupMapping ug where ug.recruitmentGroup.groupAccount = :groupAccount and ug.user.userAccount = :userAccount")
    public UserRecruitmentGroupMapping findByGroupAccountAndUserAccount(Integer groupAccount , String userAccount);

    @Query("select new online.qms198.springboot_stu.dto.group.GroupUser (u.userName, u.userAccount, u.email, u.userIdentity, ugp.roleStatus) from UserRecruitmentGroupMapping ugp left join ugp.user u on ugp.user.userAccount = u.userAccount where ugp.recruitmentGroup.groupAccount = :groupAccount and ugp.deleteStatus = 0 and u.status = 0")
    public Page<GroupUser> findGroupUsersByGroupAccountPage(Integer groupAccount , Pageable pageable);
}
