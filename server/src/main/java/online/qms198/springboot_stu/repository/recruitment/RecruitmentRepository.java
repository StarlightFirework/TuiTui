package online.qms198.springboot_stu.repository.recruitment;

import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {

    public Page<Recruitment> findByStatusAndPermissionStatus(Integer status, Integer permissionStatus, Pageable pageable);

    public Page<Recruitment> findByStatus(Integer status, Pageable pageable);
    //通过id查询
    public Recruitment findByRecruitmentId(Integer recruitmentId);

    @Modifying
    @Query("update Recruitment r set r.status = 0 where r.recruitmentId = :recruitmentId")
    @Transactional
    public void updateRecruitmentApproved(Integer recruitmentId);

    @Modifying
    @Query("update Recruitment r set r.status = 3 where r.recruitmentId = :recruitmentId")
    @Transactional
    public void updateRecruitmentReject(Integer recruitmentId);

}
