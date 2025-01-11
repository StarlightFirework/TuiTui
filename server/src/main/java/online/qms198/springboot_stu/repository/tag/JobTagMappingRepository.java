package online.qms198.springboot_stu.repository.tag;

import online.qms198.springboot_stu.pojo.recruitment.JobTagMapping;
import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobTagMappingRepository extends JpaRepository<JobTagMapping, Integer> {
    @Query("SELECT DISTINCT j.recruitment FROM JobTagMapping j WHERE j.tag.id IN :tagIds and j.status = 0 and j.recruitment.status = 0 and j.tag.status = 0 GROUP BY j.recruitment.recruitmentId HAVING COUNT(DISTINCT j.tag.id) = :tagCount")
    public Page<Recruitment> findRecruitmentsByTagIds(@Param("tagIds") List<Long> tagIds, @Param("tagCount") long tagCount, Pageable pageable);

    @Query("select j from JobTagMapping j where j.recruitment.recruitmentId = :recruitmentId")
    public List<JobTagMapping> findByRecruitmentRecruitmentId(Integer recruitmentId);

    @Query("select j.tag.id from JobTagMapping j where j.recruitment.recruitmentId = :recruitmentId")
    public List<Long> getTagIdFindByRecruitmentRecruitmentId(Integer recruitmentId);
}
