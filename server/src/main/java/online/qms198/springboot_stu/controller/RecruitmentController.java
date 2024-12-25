package online.qms198.springboot_stu.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.pojo.ResponseMessage;
import online.qms198.springboot_stu.pojo.dto.RecruitmentDto;
import online.qms198.springboot_stu.pojo.RecruitmentPage;
import online.qms198.springboot_stu.pojo.dto.RecruitmentEditDto;
import online.qms198.springboot_stu.pojo.dto.RecruitmentPageDto;
import online.qms198.springboot_stu.pojo.dto.RecruitmentTagsDto;
import online.qms198.springboot_stu.repository.JobTagMappingRepository;
import online.qms198.springboot_stu.service.IJobTagMappingService;
import online.qms198.springboot_stu.service.IRecruitmentService;
import online.qms198.springboot_stu.service.JobTagMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeParseException;
import java.util.List;
import online.qms198.springboot_stu.pojo.Recruitment;
@Slf4j
@RestController // 标记请求处理类。接口方法返回对象，转换成json文本
@RequestMapping("/recruit") // localhost:8088/user/，标记拦截user URL前缀地址类
@CrossOrigin(origins = "http://localhost:8080/recruit")
public class RecruitmentController {

    @Autowired
    IRecruitmentService recruitmentService;
    @Autowired
    IJobTagMappingService jobTagMappingService;
    @PostMapping("/insert")
    public ResponseMessage<Recruitment> add(@Valid @Validated @RequestBody RecruitmentDto recruitmentDto) throws Exception{
            Recruitment recruitmentNew = recruitmentService.addRecruitment(recruitmentDto);

            return ResponseMessage.success(recruitmentNew);
    }

    @GetMapping("/{recruitmentId}")
    public ResponseMessage<Recruitment> getById(@PathVariable Integer recruitmentId) {
        Recruitment recruitmentNew = recruitmentService.getRecruitment(recruitmentId);
        return ResponseMessage.success(recruitmentNew);
    }
    @PostMapping()
    public ResponseMessage<RecruitmentPage> getRecruitments(@RequestBody RecruitmentPageDto recruitmentPageDto){
        RecruitmentPage recruitmentPage = new RecruitmentPage();

        if (recruitmentPageDto.getTagIds().isEmpty()) {
            recruitmentPage = recruitmentService.getRecruitmentsByPage(recruitmentPageDto.getPage(), recruitmentPageDto.getSize());
        } else {
            recruitmentPage = jobTagMappingService.getRecruitmentsByTagsIds(recruitmentPageDto.getTagIds(), recruitmentPageDto.getTagIds().size(), recruitmentPageDto.getPage(), recruitmentPageDto.getSize());
        }
        return ResponseMessage.success(recruitmentPage);
    }

    @PutMapping
    public ResponseMessage<RecruitmentTagsDto> edit(@RequestBody RecruitmentEditDto recruitmentEditDto) throws Exception {
        System.out.println("控制层招聘信息主键id: " + recruitmentEditDto.getRecruitmentId());
        RecruitmentTagsDto recruitmentNew = recruitmentService.editRecruitment(recruitmentEditDto);
        return ResponseMessage.success(recruitmentNew);
    }

    // 删除
    @DeleteMapping("/{recruitmentId}")
    public ResponseMessage<Recruitment> delete(@PathVariable Integer recruitmentId) {
        if(recruitmentService.delete(recruitmentId)){
            return ResponseMessage.success();
        }
        return ResponseMessage.error();
    }

}
