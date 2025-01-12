package online.qms198.springboot_stu.controller;


import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.dto.group.*;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentDto;
import online.qms198.springboot_stu.dto.recruitment.RecruitmentPageDto;
import online.qms198.springboot_stu.pojo.common.ResponseMessage;
import online.qms198.springboot_stu.pojo.group.RecruitmentGroup;
import online.qms198.springboot_stu.pojo.group.UserRecruitmentGroupMapping;
import online.qms198.springboot_stu.pojo.recruitment.RecruitmentPage;
import online.qms198.springboot_stu.service.group.RecruitmentGroupService;
import online.qms198.springboot_stu.service.group.RecruitmentRecruitmentGroupMappingService;
import online.qms198.springboot_stu.service.group.UserGroupMappingService;
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
    @Autowired
    RecruitmentRecruitmentGroupMappingService recruitmentRecruitmentGroupMappingService;
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

    @GetMapping("/selectUser")
    public ResponseMessage<GroupUserPageDto> selectGroupUser(Integer groupAccount , Integer page , Integer size){
        return ResponseMessage.success(userGroupMappingService.findGroupUser(groupAccount,page,size));
    }

    @PostMapping("/addAdmin")
    public ResponseMessage<RecruitmentGroup> setGroupUserMappingAdminRole(@RequestBody RecruitmentUserGroupDto recruitmentUserGroupDto) throws Exception {
        userGroupMappingService.addGroupAdmin(recruitmentUserGroupDto.getGroupAccount(),recruitmentUserGroupDto.getUserAccount());
        return ResponseMessage.success();
    }

    @DeleteMapping("/deleteAdmin")
    public ResponseMessage<RecruitmentGroup> setGroupUserMappingMemberRole(@RequestBody RecruitmentUserGroupDto recruitmentUserGroupDto) throws Exception {
        userGroupMappingService.deleteGroupAdmin(recruitmentUserGroupDto.getGroupAccount(),recruitmentUserGroupDto.getUserAccount());
        return ResponseMessage.success();
    }

    @GetMapping("/selectAdmin")
    public ResponseMessage<List<GroupUserDto>> findGroupAdmin(Integer groupAccount){
        return ResponseMessage.success(userGroupMappingService.findGroupAdmin(groupAccount));
    }

    @GetMapping("/selectUserGroup")
    public ResponseMessage<UserRecruitmentGroupPageDto> findUserGroup(String userAccount, Integer page, Integer size){
        return ResponseMessage.success(userGroupMappingService.findUserGroup(userAccount,page,size));
    }

    @PostMapping("/selectRecruitment")
    public ResponseMessage<RecruitmentPage> findUserGroupRecruitment(@RequestBody RecruitmentPageDto recruitmentPageDto){
        return ResponseMessage.success(recruitmentRecruitmentGroupMappingService.findUserGroupRecruitment(recruitmentPageDto));
    }
}
