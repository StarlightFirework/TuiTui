package online.qms198.springboot_stu.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentAuditDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentStatisticsDto;
import online.qms198.springboot_stu.pojo.common.ResponseMessage;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentDto;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentPageDto;
import online.qms198.springboot_stu.service.group.IRecruitmentRecruitmentGroupMappingService;
import online.qms198.springboot_stu.service.recruitment.IJobTagMappingService;
import online.qms198.springboot_stu.service.recruitment.IRecruitmentService;
import online.qms198.springboot_stu.service.recruitment.IRecruitmentStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import online.qms198.springboot_stu.pojo.recruitment.Recruitment;
@Slf4j
@RestController // 标记请求处理类。接口方法返回对象，转换成json文本
@RequestMapping("/recruit") // localhost:8088/user/，标记拦截user URL前缀地址类
//@CrossOrigin(
//        origins = {"http://localhost:3000", "https://qms198.online", "http://117.72.104.77"},
//        allowedHeaders = {"Authorization", "Content-Type"},
//        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS},
//        allowCredentials = "true"
//)
public class RecruitmentController {

    @Autowired
    IRecruitmentService recruitmentService;
    @Autowired
    IJobTagMappingService jobTagMappingService;
    @Autowired
    IRecruitmentStatisticsService recruitmentStatisticsService;
    @Autowired
    IRecruitmentRecruitmentGroupMappingService recruitmentRecruitmentGroupMappingService;
    @PostMapping("/insert")
    public ResponseMessage<Recruitment> add(@Valid @Validated @RequestBody RecruitmentDto recruitmentDto) throws Exception{
            Recruitment recruitmentNew = recruitmentService.addRecruitment(recruitmentDto);
            return ResponseMessage.success(recruitmentNew);
    }

    @GetMapping("/get/id/{recruitmentId}")
    public ResponseMessage<RecruitmentDto> getById(@PathVariable Integer recruitmentId) {
        RecruitmentDto recruitmentDtoNew = recruitmentService.getRecruitment(recruitmentId);
        return ResponseMessage.success(recruitmentDtoNew);
    }

    @PostMapping("/get/collection")
    public ResponseMessage<RecruitmentPage> findCollectionRecruitment(@RequestBody RecruitmentPageDto recruitmentPageDto){
        return ResponseMessage.success(recruitmentStatisticsService.findCollectionRecruitments(recruitmentPageDto));
    }

    @PostMapping("/get/all")
    public ResponseMessage<RecruitmentPage> getRecruitments(@RequestBody RecruitmentPageDto recruitmentPageDto){
        RecruitmentPage recruitmentPage = new RecruitmentPage();

        if (recruitmentPageDto.getTagIds().isEmpty()) {
            recruitmentPage = recruitmentService.getRecruitmentsByPage(recruitmentPageDto.getPage(), recruitmentPageDto.getSize());
        } else {
            recruitmentPage = jobTagMappingService.getPublicRecruitmentsByTagsIds(recruitmentPageDto.getTagIds(), recruitmentPageDto.getTagIds().size(), recruitmentPageDto.getPage(), recruitmentPageDto.getSize());
        }
        return ResponseMessage.success(recruitmentPage);
    }

    @PutMapping("/edit")
    public ResponseMessage<RecruitmentDto> edit(@RequestBody RecruitmentDto recruitmentDto) throws Exception {
        RecruitmentDto recruitmentNew = recruitmentService.editRecruitment(recruitmentDto);
        return ResponseMessage.success(recruitmentNew);
    }

    // 删除
    @DeleteMapping("/delete/{recruitmentId}")
    public ResponseMessage<Recruitment> delete(@PathVariable Integer recruitmentId) {
        if(recruitmentService.delete(recruitmentId)){
            return ResponseMessage.success();
        }
        return ResponseMessage.error();
    }
    @GetMapping("/statistics/view")
    public ResponseMessage<Recruitment> plusViewCount(Integer recruitmentId){
        recruitmentStatisticsService.updateViewCount(recruitmentId);
        return ResponseMessage.success();
    }

    @PostMapping("/statistics/addCollection")
    public ResponseMessage<Recruitment> addCollection(@RequestBody RecruitmentStatisticsDto recruitmentStatisticsDto){
        recruitmentStatisticsService.addCollection(recruitmentStatisticsDto);
        return ResponseMessage.success();
    }

    @PostMapping("/statistics/cancelCollection")
    public ResponseMessage<Recruitment> cancelCollection(@RequestBody RecruitmentStatisticsDto recruitmentStatisticsDto){
        recruitmentStatisticsService.cancelCollection(recruitmentStatisticsDto);
        return ResponseMessage.success();
    }

    @GetMapping("/statistics/collectionCount")
    public ResponseMessage<Integer> findCollectionCount(Integer recruitmentId){
        return ResponseMessage.success(recruitmentStatisticsService.findCollectionCount(recruitmentId));
    }

    @GetMapping("/audit/get")
    public ResponseMessage<RecruitmentPage> getAuditRecruitments(Integer page, Integer size){
        return ResponseMessage.success(recruitmentService.getAuditRecruitmentsByPage(page,size));
    }

    @PostMapping("/audit/update")
    public ResponseMessage<Recruitment> updateAuditRecruitment(@RequestBody RecruitmentAuditDto recruitmentAuditDto) throws Exception {
        recruitmentService.updateAuditRecruitment(recruitmentAuditDto);
        return ResponseMessage.success();
    }

    @PostMapping("/addGroup")
    public ResponseMessage<Recruitment> addRecruitmentGroup(@RequestBody RecruitmentDto recruitmentDto){
        recruitmentRecruitmentGroupMappingService.batchAddRecruitmentGroupMapping(recruitmentDto.getGroupAccounts(),recruitmentDto.getRecruitmentId());
        return ResponseMessage.success();
    }
    @PostMapping("/deleteGroup")
    public ResponseMessage<Recruitment> deleteRecruitmentGroup(@RequestBody RecruitmentDto recruitmentDto){
        recruitmentRecruitmentGroupMappingService.batchDeleteRecruitmentGroupMapping(recruitmentDto.getGroupAccounts(),recruitmentDto.getRecruitmentId());
        return ResponseMessage.success();
    }
}
