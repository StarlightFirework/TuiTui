package online.qms198.springboot_stu.service.recruitment;

import online.qms198.springboot_stu.pojo.user.User;

import java.util.List;

public interface IUserGroupMappingService {
    public void batchAddUserGroupMapping(List<String> userAccounts , Integer groupAccount) throws Exception;
    public void batchDeleteUserGroupMapping(List<String> userAccounts , Integer groupAccount) throws Exception;
}
