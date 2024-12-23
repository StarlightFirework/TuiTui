package online.qms198.springboot_stu.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.pojo.ResponseMessage;
import online.qms198.springboot_stu.pojo.dto.RecruitmentDto;
import online.qms198.springboot_stu.pojo.RecruitmentPage;
import online.qms198.springboot_stu.pojo.dto.RecruitmentPageDto;
import online.qms198.springboot_stu.repository.JobTagMappingRepository;
import online.qms198.springboot_stu.service.IRecruitmentService;
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
    JobTagMappingRepository jobTagMappingRepository;
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

//    @GetMapping
//    public ResponseMessage<RecruitmentPageDto> getRecruitments(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
//        RecruitmentPageDto recruitmentPageDto = recruitmentService.getRecruitments(page,size);
//        return ResponseMessage.success(recruitmentPageDto);
//    }

    @PostMapping("/getByTags")
    public ResponseMessage<RecruitmentPage> getRecruitments(@RequestBody RecruitmentPageDto recruitmentPageDto){
        RecruitmentPage recruitmentPage = new RecruitmentPage();

        if (recruitmentPageDto.getTagIds().isEmpty()) {
            recruitmentPage = recruitmentService.getRecruitments(recruitmentPageDto.getPage(), recruitmentPageDto.getSize());
        } else {
            List<Recruitment> recruitments = jobTagMappingRepository.findRecruitmentsByTagIds(recruitmentPageDto.getTagIds(), recruitmentPageDto.getTagIds().size());
            recruitmentPage.setRecruitmentTotal(recruitments.size());
            recruitmentPage.setRecruitments(recruitments);

        }
        return ResponseMessage.success(recruitmentPage);
    }
//
//    @PutMapping
//    public ResponseMessage<User> edit(@Validated @RequestBody UserRegisterDto user) {
//        Recruitment recruitmentNew = recruitmentService.edit(user);
//        return ResponseMessage.success(userNew);
//    }

//    // 删除
//    @DeleteMapping("/{userId}") // URL: localhost:8088/user/userId method: delete
//    public ResponseMessage<User> delete(@PathVariable Integer userId) {
//        recruitmentService(userId);
//        return ResponseMessage.success();
//    }

}
