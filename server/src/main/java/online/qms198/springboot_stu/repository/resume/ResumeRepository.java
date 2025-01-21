package online.qms198.springboot_stu.repository.resume;

import online.qms198.springboot_stu.pojo.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    Resume findByResumeId(Integer resumeId);
    Resume findByUserAccount(String userAccount);
} 