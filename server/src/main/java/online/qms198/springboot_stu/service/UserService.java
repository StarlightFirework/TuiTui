package online.qms198.springboot_stu.service;

import online.qms198.springboot_stu.controller.UserController;
import online.qms198.springboot_stu.pojo.User;
import online.qms198.springboot_stu.pojo.dto.UserRegisterDto;
import online.qms198.springboot_stu.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public User add(UserRegisterDto user) {
        User userPojo = new User();
        BeanUtils.copyProperties(user, userPojo);
        // dto对象转成pojo对象
        System.out.println("UserDto内容: " + user);
        System.out.println("User内容: " + userPojo);
        if (userRepository.findByUserAccount(userPojo.getUserAccount()) != null) {
            throw new RuntimeException("用户名已存在"); // 抛出自定义异常，触发事务回滚
        }
        return userRepository.save(userPojo);
    }

    @Override
    public User getUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            // 抛出异常
            try {
                throw new IllegalAccessException("用户不存在，参数异常!");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public User edit(UserRegisterDto user) {
        User userPojo = new User();
        BeanUtils.copyProperties(user, userPojo);

        return userRepository.save(userPojo);
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }
    @Override
    public User authenticate(String userAccount, String password) {
        User user = userRepository.findByUserAccount(userAccount); // 确保使用正确的查询方法
        if (user != null) {
            // 如果没有加密，可以直接比较
            if (user.getPassword().equals(password)) {
                return user; // 登录成功
            }
        }
        return null; // 登录失败
    }

    @Override
    public User getUserByUserAccount(String userAccount) {
        return userRepository.findByUserAccount(userAccount);
    }
}