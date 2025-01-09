package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.recruitment.JobTagMapping;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentAuditRepository extends JpaRepository<RecruitmentAudit, Integer> {
    @Query("select r from RecruitmentAudit r where r.recruitment.recruitmentId = :recruitmentId and r.status = 0")
    public RecruitmentAudit findByRecruitmentId(Integer recruitmentId);
}
