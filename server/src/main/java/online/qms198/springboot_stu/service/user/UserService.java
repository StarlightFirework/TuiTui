package online.qms198.springboot_stu.service.user;

import online.qms198.springboot_stu.pojo.user.User;
import online.qms198.springboot_stu.dto.user.UserRegisterDto;
import online.qms198.springboot_stu.repository.user.UserRepository;
import online.qms198.springboot_stu.utils.EncryptionUtil;
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
        // 密码加密
        userPojo.setPassword(EncryptionUtil.advancedEncryption(userPojo.getPassword()));
        userPojo.setStatus(0);
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
        // 密码加密
        userPojo.setPassword(EncryptionUtil.advancedEncryption(userPojo.getPassword()));

        return userRepository.save(userPojo);
    }

    @Override
    @Transactional
    public boolean delete(Integer userId) {

        User user = userRepository.findByUserId(userId);
        if(user == null){
            return false;
        }
        user.setStatus(1);
        return userRepository.save(user) != null;
    }
    @Override
    public User authenticate(String userAccount, String password) {
        User user = userRepository.findByUserAccount(userAccount); // 确保使用正确的查询方法
        if (user != null) {
            // 如果没有加密，可以直接比较
//            if (user.getPassword().equals(password)) {
//                return user; // 登录成功
//            }
//
            //如果进行加密
            if (EncryptionUtil.checkCiphertext(password,user.getPassword())) {
                return user; // 登录成功
            }
        }
        return null; // 登录失败
    }

    @Override
    public User getUserByUserAccount(String userAccount) {
        try {
            User user = userRepository.findByUserAccount(userAccount);
            if (user == null) {
                throw new IllegalArgumentException("用户不存在，参数异常!");
            }
            return user;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while fetching user", e);
        }
    }

}