package online.qms198.springboot_stu.security;

import online.qms198.springboot_stu.pojo.User;
import online.qms198.springboot_stu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查找用户
        User user = userService.getUserByUserAccount(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 返回一个 UserDetails 实现类
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserAccount())
                .password(user.getPassword())  // 根据需要设置密码
                .authorities("ROLE_USER")  // 或 .roles("USER")，根据需求配置
                .build();
    }
}

