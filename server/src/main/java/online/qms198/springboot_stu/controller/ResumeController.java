package online.qms198.springboot_stu.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import online.qms198.springboot_stu.dto.resume.ResumeDto;
import online.qms198.springboot_stu.pojo.common.ResponseMessage;
import online.qms198.springboot_stu.pojo.resume.Resume;
import online.qms198.springboot_stu.service.resume.IResumeService;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    
    @Autowired
    private IResumeService resumeService;
    
    @PostMapping("/create")
    public ResponseMessage<Resume> createResume(@Valid @Validated @RequestBody ResumeDto resumeDto) {
        try {
            Resume resume = resumeService.createResume(resumeDto);
            return ResponseMessage.success(resume);
        } catch (Exception e) {
            return ResponseMessage.error(500,e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public ResponseMessage<Resume> updateResume(@Valid @Validated @RequestBody ResumeDto resumeDto) {
        try {
            Resume resume = resumeService.updateResume(resumeDto);
            return ResponseMessage.success(resume);
        } catch (Exception e) {
            return ResponseMessage.error(500,e.getMessage());
        }
    }
    
    @GetMapping("/get/account")
    public ResponseMessage<Resume> getResumeByUserAccount(@RequestParam String userAccount) {
        try {
            Resume resume = resumeService.getResumeByUserAccount(userAccount);
            return ResponseMessage.success(resume);
        } catch (Exception e) {
            return ResponseMessage.error(500,e.getMessage());
        }
    }
    
    @DeleteMapping("/delete")
    public ResponseMessage<String> deleteResume(@RequestParam String userAccount) {
        try {
            resumeService.deleteResumeByUserAccount(userAccount);
            return ResponseMessage.success("简历删除成功");
        } catch (Exception e) {
            return ResponseMessage.error(500,e.getMessage());
        }
    }
} 