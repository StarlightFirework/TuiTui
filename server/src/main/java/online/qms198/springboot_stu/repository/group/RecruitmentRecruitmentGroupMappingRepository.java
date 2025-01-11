package online.qms198.springboot_stu.repository.group;


import online.qms198.springboot_stu.pojo.group.RecruitmentRecruitmentGroupMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRecruitmentGroupMappingRepository extends JpaRepository<RecruitmentRecruitmentGroupMapping,Integer> {

}
