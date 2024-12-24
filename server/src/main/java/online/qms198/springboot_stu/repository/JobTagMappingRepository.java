package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.JobTagMapping;
import online.qms198.springboot_stu.pojo.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobTagMappingRepository extends JpaRepository<JobTagMapping, Integer> {
    @Query("SELECT DISTINCT j.recruitment FROM JobTagMapping j WHERE j.tag.id IN :tagIds GROUP BY j.recruitment.recruitmentId HAVING COUNT(DISTINCT j.tag.id) = :tagCount")
    public Page<Recruitment> findRecruitmentsByTagIds(@Param("tagIds") List<Long> tagIds, @Param("tagCount") long tagCount, Pageable pageable);
}
