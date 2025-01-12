package online.qms198.springboot_stu.repository.group;


import org.springframework.data.domain.Page;
import online.qms198.springboot_stu.pojo.group.RecruitmentRecruitmentGroupMapping;
import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentRecruitmentGroupMappingRepository extends JpaRepository<RecruitmentRecruitmentGroupMapping,Long> {

    // 查询圈子中的所有招聘信息
    @Query("select rrgm.recruitment from RecruitmentRecruitmentGroupMapping rrgm left join Recruitment r on r.recruitmentId = rrgm.recruitment.recruitmentId where rrgm.recruitmentGroup.groupAccount in :groupAccounts and rrgm.recruitment.status = 0")
    public Page<Recruitment> findUserGroupRecruitments(List<Integer> groupAccounts , Pageable pageable);

    // 查询招聘信息所在的所有圈子
    @Query("select rrgm.recruitmentGroup.groupAccount from RecruitmentRecruitmentGroupMapping rrgm where rrgm.recruitment.recruitmentId = :recruitmentId")
    public List<Integer> findRecruitmentGroups(Integer recruitmentId);

    // 通过招聘Id和圈子账号查询
    @Query("select rrgm from RecruitmentRecruitmentGroupMapping rrgm where rrgm.recruitment.recruitmentId = :recruitmentId and rrgm.recruitmentGroup.groupAccount = :groupAccount")
    public RecruitmentRecruitmentGroupMapping findRecruitmentRecruitmentGroupMappingByRecruitmentIdAndGroupAccount(Integer recruitmentId , Integer groupAccount);
}
