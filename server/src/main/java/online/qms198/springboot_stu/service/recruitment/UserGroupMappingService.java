package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.pojo.recruitment.UserRecruitmentGroupMapping;
import online.qms198.springboot_stu.repository.UserGroupMappingRepository;
import online.qms198.springboot_stu.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserGroupMappingService implements IUserGroupMappingService{

    @Autowired
    UserGroupMappingRepository userGroupMappingRepository;
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
}
