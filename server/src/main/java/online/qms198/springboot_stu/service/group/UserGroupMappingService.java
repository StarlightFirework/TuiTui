package online.qms198.springboot_stu.service.group;


import online.qms198.springboot_stu.dto.group.GroupUserDto;
import online.qms198.springboot_stu.dto.group.GroupUserPageDto;
import online.qms198.springboot_stu.dto.group.RecruitmentGroupDto;
import online.qms198.springboot_stu.dto.group.UserRecruitmentGroupPageDto;
import online.qms198.springboot_stu.pojo.group.UserRecruitmentGroupMapping;
import online.qms198.springboot_stu.repository.group.RecruitmentGroupRepository;
import online.qms198.springboot_stu.repository.group.UserGroupMappingRepository;
import online.qms198.springboot_stu.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserGroupMappingService implements IUserGroupMappingService {

    @Autowired
    UserGroupMappingRepository userGroupMappingRepository;
    @Autowired
    RecruitmentGroupRepository recruitmentGroupRepository;
    @Autowired
    UserService userService;
    @Autowired
    RecruitmentGroupService recruitmentGroupService;

    @Override
    @Transactional
    public void batchAddUserGroupMapping(List<String> userAccounts , Integer groupAccount) throws Exception {
        // 再增加原数据库中没有的信息
        for(String userAccount : userAccounts){
            UserRecruitmentGroupMapping userRecruitmentGroupMapping = userGroupMappingRepository.findByGroupAccountAndUserAccount(groupAccount,userAccount);
            if(userRecruitmentGroupMapping == null){
                userGroupMappingRepository.save(new UserRecruitmentGroupMapping(null,userService.getUserByUserAccount(userAccount),recruitmentGroupService.getRecruitmentGroupByAccount(groupAccount),0,0));
            }else{
                userRecruitmentGroupMapping.setDeleteStatus(0);
                userGroupMappingRepository.save(userRecruitmentGroupMapping);
            }
        }
    }

    @Override
    @Transactional
    public void batchDeleteUserGroupMapping(List<String> userAccounts , Integer groupAccount) throws Exception {
        for(String userAccount : userAccounts){
            UserRecruitmentGroupMapping userRecruitmentGroupMapping = userGroupMappingRepository.findByGroupAccountAndUserAccount(groupAccount,userAccount);
            if(userRecruitmentGroupMapping == null){
                throw new Exception("圈子中不存在该用户或已删除，圈子账号：" + groupAccount + "用户账号" + userAccount);
            }
            userRecruitmentGroupMapping.setDeleteStatus(1);
            userGroupMappingRepository.save(userRecruitmentGroupMapping);
        }
    }

    @Override
    public GroupUserPageDto findGroupUser(Integer groupAccount , Integer page , Integer size){
        Pageable pageable = (Pageable) PageRequest.of(page,size).withSort(Sort.Direction.DESC,"roleStatus");
        Page<GroupUserDto> groupUsers=  userGroupMappingRepository.findGroupUsersByGroupAccountPage(groupAccount,pageable);
        return new GroupUserPageDto((int)groupUsers.getTotalElements(),groupUsers.getContent());
    }
    @Override
    @Transactional
    public void addGroupAdmin(Integer groupAccount , String userAccount) throws Exception {

        if(recruitmentGroupRepository.findByGroupAccount(groupAccount) == null){
            throw new Exception("删除圈子管理员失败，圈子账号不存在，圈子账号：" + groupAccount);
        }

        if(userGroupMappingRepository.findGroupAdminsByGroupAccount(groupAccount).size() >= 5){
            throw new Exception("圈子管理员已达到上限，圈子账号：" + groupAccount);
        }
        userGroupMappingRepository.updateUserGroupMappingRoleStatus(userAccount,groupAccount,1);
    }

    @Override
    @Transactional
    public void deleteGroupAdmin(Integer groupAccount , String userAccount) throws Exception {

        if(recruitmentGroupRepository.findByGroupAccount(groupAccount) == null){
            throw new Exception("删除圈子管理员失败，圈子账号不存在，圈子账号：" + groupAccount);
        }

        userGroupMappingRepository.updateUserGroupMappingRoleStatus(userAccount,groupAccount,0);
    }

    @Override
    public List<GroupUserDto> findGroupAdmin(Integer groupAccount){
        return userGroupMappingRepository.findGroupAdminsByGroupAccount(groupAccount);
    }

    public UserRecruitmentGroupPageDto findUserGroup(String userAccount, Integer page, Integer size){
        Pageable pageable = (Pageable) PageRequest.of(page,size);
        Page<RecruitmentGroupDto> recruitmentGroupDtos = userGroupMappingRepository.findUserRecruitmentGroup(userAccount,pageable);
        return new UserRecruitmentGroupPageDto((int)recruitmentGroupDtos.getTotalElements(),recruitmentGroupDtos.getContent());
    }

}
