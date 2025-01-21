package online.qms198.springboot_stu.service.resume;

import online.qms198.springboot_stu.dto.resume.ResumeDto;
import online.qms198.springboot_stu.pojo.resume.Resume;
import org.springframework.transaction.annotation.Transactional;

public interface IResumeService {
    // 创建简历
    Resume createResume(ResumeDto resumeDto) throws Exception;
    
    // 更新简历
    Resume updateResume(ResumeDto resumeDto) throws Exception;
    
    // 获取简历
    Resume getResumeByUserAccount(String userAccount) throws Exception;

    Resume getResumeById(Integer resumeId) throws Exception;

    @Transactional
    void deleteResume(Integer resumeId) throws Exception;

    // 删除简历
    void deleteResumeByUserAccount(String userAccount) throws Exception;
} 