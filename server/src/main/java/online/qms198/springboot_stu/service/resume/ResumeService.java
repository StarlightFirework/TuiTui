package online.qms198.springboot_stu.service.resume;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import online.qms198.springboot_stu.dto.resume.ResumeDto;
import online.qms198.springboot_stu.pojo.resume.Resume;
import online.qms198.springboot_stu.repository.resume.ResumeRepository;
import online.qms198.springboot_stu.repository.user.UserRepository;
import online.qms198.springboot_stu.pojo.user.User;

import java.time.LocalDate;

@Service
public class ResumeService implements IResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Resume createResume(ResumeDto resumeDto) throws Exception {
        // 检查用户是否存在
        User user = userRepository.findByUserAccount(resumeDto.getUserAccount());
        if (user == null) {
            throw new Exception("用户不存在!");
        }

        // 查找用户的简历（包括已删除的）
        Resume existingResume = resumeRepository.findByUserAccount(resumeDto.getUserAccount());
        
        if (existingResume != null) {
            // 如果简历存在且状态为0（正常），则不允许创建
            if (existingResume.getStatus() == 0) {
                throw new Exception("用户已存在有效简历!");
            }
            // 如果简历存在但状态为1（已删除），则重新激活并更新
            Integer resumeId = existingResume.getResumeId(); // 保存原有的ID
            LocalDate createTime = existingResume.getCreateTime(); // 保存原有的创建时间
            
            BeanUtils.copyProperties(resumeDto, existingResume);
            
            // 恢复原有的ID和创建时间
            existingResume.setResumeId(resumeId);
            existingResume.setCreateTime(createTime);
            existingResume.setStatus(0);
            existingResume.setUpdateTime(LocalDate.now());
            existingResume.setUser(user);
            user.setResume(existingResume);
            return resumeRepository.save(existingResume);
        }

        // 如果不存在简历，创建新的
        Resume resume = new Resume();
        BeanUtils.copyProperties(resumeDto, resume);

        // 设置关联关系
        resume.setUser(user);
        user.setResume(resume);

        // 设置其他字段
        resume.setCreateTime(LocalDate.now());
        resume.setUpdateTime(LocalDate.now());
        resume.setStatus(0);

        return resumeRepository.save(resume);
    }

    @Override
    @Transactional
    public Resume updateResume(ResumeDto resumeDto) throws Exception {
        if (resumeDto.getUserAccount() == null || resumeDto.getUserAccount().trim().isEmpty()) {
            throw new Exception("用户账号不能为空!");
        }

        Resume existingResume = resumeRepository.findByUserAccount(resumeDto.getUserAccount());
        if (existingResume == null) {
            throw new Exception("简历不存在!");
        }

        // 保存原有的一些字段
        Integer resumeId = existingResume.getResumeId();
        LocalDate createTime = existingResume.getCreateTime();
        Integer status = existingResume.getStatus();

        // 复制新的属性
        BeanUtils.copyProperties(resumeDto, existingResume);

        // 恢复原有的一些字段
        existingResume.setResumeId(resumeId);
        existingResume.setCreateTime(createTime);
        existingResume.setStatus(status);
        existingResume.setUpdateTime(LocalDate.now());

        return resumeRepository.save(existingResume);
    }

    @Override
    public Resume getResumeByUserAccount(String userAccount) throws Exception {
        Resume resume = resumeRepository.findByUserAccount(userAccount);
        if (resume == null) {
            throw new Exception("简历不存在!");
        }
        return resume;
    }

    @Override
    public Resume getResumeById(Integer resumeId) throws Exception {
        Resume resume = resumeRepository.findByResumeId(resumeId);
        if (resume == null) {
            throw new Exception("简历不存在!");
        }
        return resume;
    }

    @Override
    @Transactional
    public void deleteResume(Integer resumeId) throws Exception {
        Resume resume = resumeRepository.findByResumeId(resumeId);
        if (resume == null) {
            throw new Exception("简历不存在!");
        }
        resume.setStatus(1);
        resumeRepository.save(resume);
    }

    @Override
    @Transactional
    public void deleteResumeByUserAccount(String userAccount) throws Exception {
        Resume resume = resumeRepository.findByUserAccount(userAccount);
        if (resume == null) {
            throw new Exception("简历不存在!");
        }
        resume.setStatus(1);
        resumeRepository.save(resume);
    }
}
