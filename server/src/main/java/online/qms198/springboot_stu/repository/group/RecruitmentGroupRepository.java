package online.qms198.springboot_stu.repository.group;

import online.qms198.springboot_stu.pojo.group.RecruitmentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RecruitmentGroupRepository extends JpaRepository<RecruitmentGroup, Integer> {
    // 屏蔽可见范围查询
    @Query("select r from RecruitmentGroup r where r.groupAccount = :groupAccount and r.deleteStatus = 0")
    public  RecruitmentGroup findByGroupAccount(Integer groupAccount);
    @Modifying
    @Query("update RecruitmentGroup r set r.deleteStatus = 1 where r.groupAccount = :groupAccount")
    @Transactional
    public void deleteByGroupAccount(Integer groupAccount);
    // 查询共开招聘圈
    @Query("select r from RecruitmentGroup r where r.groupAccount = :groupAccount and r.deleteStatus = 0 and r.visibleStatus = :visibleStatus")
    public RecruitmentGroup findVisibleRecruitmentByGroupAccount(Integer groupAccount, int visibleStatus);
}
