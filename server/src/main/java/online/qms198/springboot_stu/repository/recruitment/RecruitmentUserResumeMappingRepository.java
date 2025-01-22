package online.qms198.springboot_stu.repository.recruitment;

import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentUserResumeMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentUserResumeMappingRepository extends JpaRepository<RecruitmentUserResumeMapping,Long> {
    // 根据招聘信息和用户账号查询招聘用户简历映射表
    @Query("select rurm from RecruitmentUserResumeMapping rurm where rurm.recruitment.recruitmentId = :recruitmentId and rurm.user.userAccount = :userAccount")
    public RecruitmentUserResumeMapping findByRecruitmentIdAndUserAccount(Integer recruitmentId , String userAccount);
    // 分页查找用户投递的所有招聘信息
    @Query("select rurm.recruitment from RecruitmentUserResumeMapping rurm where rurm.user.userAccount = :userAccount and rurm.status = 0")
    public Page<Recruitment> findDeliverRecruitmentByUserAccount(String userAccount , Pageable pageable);
}
