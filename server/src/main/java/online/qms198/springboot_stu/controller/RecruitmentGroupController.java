package online.qms198.springboot_stu.controller;


import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentGroupDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentUserGroupDto;
import online.qms198.springboot_stu.pojo.common.ResponseMessage;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentGroup;
import online.qms198.springboot_stu.pojo.recruitment.UserRecruitmentGroupMapping;
import online.qms198.springboot_stu.service.recruitment.RecruitmentGroupService;
import online.qms198.springboot_stu.service.recruitment.UserGroupMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // 标记请求处理类。接口方法返回对象，转换成json文本
@RequestMapping("/group") // localhost:8088/user/，标记拦截user URL前缀地址类
public class RecruitmentGroupController {

    @Autowired
    RecruitmentGroupService recruitmentGroupService;
    @Autowired
    UserGroupMappingService userGroupMappingService;
    @PostMapping("/create")
    public ResponseMessage<RecruitmentGroup> addRecruitmentGroup(@RequestBody RecruitmentGroupDto recruitmentGroupDto) throws Exception {
        RecruitmentGroup recruitmentGroup = recruitmentGroupService.addRecruitmentGroup(recruitmentGroupDto);
        return ResponseMessage.success(recruitmentGroup);
    }

    @PostMapping("/edit")
    public ResponseMessage<RecruitmentGroup> editRecruitmentGroup(@RequestBody RecruitmentGroupDto recruitmentGroupDto) throws Exception {
        return ResponseMessage.success(recruitmentGroupService.editRecruitmentGroup(recruitmentGroupDto));
    }

    @DeleteMapping("/delete/{groupAccount}")
    public ResponseMessage<RecruitmentGroup> deleteRecruitmentGroup(@PathVariable Integer groupAccount) throws Exception {
        recruitmentGroupService.deleteRecruitmentGroup(groupAccount);
        return ResponseMessage.success();
    }

    @GetMapping("/get/{groupAccount}")
    public ResponseMessage<RecruitmentGroup> getVisibleRecruitmentGroup(@PathVariable Integer groupAccount) throws Exception {
        RecruitmentGroup recruitmentGroup = recruitmentGroupService.getVisibleRecruitmentGroup(groupAccount);
        return ResponseMessage.success(recruitmentGroup);
    }

    @PostMapping("/addUser")
    public ResponseMessage<RecruitmentGroup> batchAddUserGroupMapping(@RequestBody RecruitmentUserGroupDto recruitmentUserGroupDto) throws Exception {
        userGroupMappingService.batchAddUserGroupMapping(recruitmentUserGroupDto.getUserAccounts(),recruitmentUserGroupDto.getGroupAccount());
        return ResponseMessage.success();
    }

    @DeleteMapping("/deleteUser")
    public ResponseMessage<UserRecruitmentGroupMapping> batchDeleteUserGroupMapping(@RequestBody RecruitmentUserGroupDto recruitmentUserGroupDto) throws Exception {
        userGroupMappingService.batchDeleteUserGroupMapping(recruitmentUserGroupDto.getUserAccounts(),recruitmentUserGroupDto.getGroupAccount());
        return ResponseMessage.success();
    }
}
