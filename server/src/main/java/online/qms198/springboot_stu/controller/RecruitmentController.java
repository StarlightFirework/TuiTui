package online.qms198.springboot_stu.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.pojo.Recruitment;
import online.qms198.springboot_stu.pojo.ResponseMessage;
import online.qms198.springboot_stu.pojo.dto.RecruitmentDto;
import online.qms198.springboot_stu.pojo.dto.RecruitmentPageDto;
import online.qms198.springboot_stu.service.IRecruitmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeParseException;

@Slf4j
@RestController // 标记请求处理类。接口方法返回对象，转换成json文本
@RequestMapping("/recruit") // localhost:8088/user/，标记拦截user URL前缀地址类
@CrossOrigin(origins = "http://localhost:8080/recruit")
public class RecruitmentController {

    @Autowired
    IRecruitmentService recruitmentService;
    @PostMapping("/insert")
    public ResponseMessage<Recruitment> add(@Valid @Validated @RequestBody RecruitmentDto recruitmentDto) {
        try{
            Recruitment recruitmentNew = recruitmentService.addRecruitment(recruitmentDto);
            return ResponseMessage.success(recruitmentNew);
        }catch (DateTimeParseException e){
            log.error("招聘信息截止时间解析错误",e);
            return ResponseMessage.error("招聘信息截止时间解析错误");
        }
    }

    @GetMapping("/{recruitmentId}")
    public ResponseMessage<Recruitment> getById(@PathVariable Integer recruitmentId) {
        Recruitment recruitmentNew = recruitmentService.getRecruitment(recruitmentId);
        return ResponseMessage.success(recruitmentNew);
    }

    @GetMapping
    public ResponseMessage<RecruitmentPageDto> getRecruitments(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        RecruitmentPageDto recruitmentPageDto = recruitmentService.getRecruitments(page,size);
        return ResponseMessage.success(recruitmentPageDto);
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
