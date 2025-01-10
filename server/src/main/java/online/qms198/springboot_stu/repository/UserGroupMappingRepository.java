package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.recruitment.UserRecruitmentGroupMapping;
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
}
