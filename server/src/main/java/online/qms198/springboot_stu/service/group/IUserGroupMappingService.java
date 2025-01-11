package online.qms198.springboot_stu.service.group;

import online.qms198.springboot_stu.dto.group.GroupUserDto;
import online.qms198.springboot_stu.dto.group.GroupUserPageDto;
import online.qms198.springboot_stu.dto.group.UserRecruitmentGroupPageDto;

import java.util.List;

public interface IUserGroupMappingService {
    public void batchAddUserGroupMapping(List<String> userAccounts , Integer groupAccount) throws Exception;
    public void batchDeleteUserGroupMapping(List<String> userAccounts , Integer groupAccount) throws Exception;
    public GroupUserPageDto findGroupUser(Integer groupAccount , Integer page , Integer size);
    public void addGroupAdmin(Integer groupAccount , String userAccount) throws Exception;
    public void deleteGroupAdmin(Integer groupAccount , String userAccount) throws Exception;
    public List<GroupUserDto> findGroupAdmin(Integer groupAccount);
    public UserRecruitmentGroupPageDto findUserGroup(String userAccount, Integer page, Integer size);
}
