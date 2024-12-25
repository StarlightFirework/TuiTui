package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.pojo.User;
import online.qms198.springboot_stu.pojo.dto.UserRegisterDto;

public interface IUserService {
    // 插入用户
    User add(UserRegisterDto user);

    // 查询用户
    User getUser(Integer userId);

    // 修改用户
    User edit(UserRegisterDto user);

    // 删除用户
    boolean delete(Integer userId);

    User authenticate(String userAccount, String password);

    User getUserByUserAccount(String userAccount);
}
