package online.qms198.springboot_stu.repository.recruitment;


import online.qms198.springboot_stu.pojo.recruitment.RecruitmentStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RecruitmentStatisticsRepository extends JpaRepository<RecruitmentStatistics, Long> {
    @Modifying
    @Query("update RecruitmentStatistics rs SET rs.queryCount = rs.queryCount + 1 where rs.recruitment.recruitmentId = :recruitmentId")
    @Transactional
    public void updateQueryCount(Integer recruitmentId);

    @Modifying
    @Query("update RecruitmentStatistics rs SET rs.viewCount = rs.viewCount + 1 where rs.recruitment.recruitmentId = :recruitmentId")
    @Transactional
    public void updateViewCount(Integer recruitmentId);

    @Query("select rs.collectionCount from RecruitmentStatistics rs where rs.recruitment.recruitmentId = :recruitmentId")
    public Integer findCollectionCount(Integer recruitmentId);
}
