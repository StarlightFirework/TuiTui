package online.qms198.springboot_stu.repository.recruitment;

import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentCollectionsMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentCollectionsMappingRepository extends JpaRepository<RecruitmentCollectionsMapping,Long> {
    @Query("select rcm from RecruitmentCollectionsMapping rcm where rcm.recruitment.recruitmentId = :recruitmentId and rcm.user.userAccount = :userAccount")
    public RecruitmentCollectionsMapping findByRecruitmentIdAndUserAccount(Integer recruitmentId , String userAccount);

    @Query("select rcm.recruitment from RecruitmentCollectionsMapping rcm where rcm.user.userAccount = :userAccount and rcm.status = 0")
    public Page<Recruitment> findRecruitmentByUserAccount(String userAccount , Pageable pageable);
}
