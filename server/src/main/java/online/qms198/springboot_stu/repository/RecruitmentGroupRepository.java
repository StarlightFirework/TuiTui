package online.qms198.springboot_stu.repository;

import online.qms198.springboot_stu.pojo.recruitment.JobTagMapping;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentGroupRepository extends JpaRepository<RecruitmentGroup, Integer> {

}
