package online.qms198.springboot_stu.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.service.user.UserService;
import online.qms198.springboot_stu.pojo.user.User;
import online.qms198.springboot_stu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_HEADER_TYPE = "Bearer";

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取 token
        String authHeader = request.getHeader(AUTH_HEADER);
        log.info("Authorization Header: {}", authHeader);
        if (Objects.isNull(authHeader) || !authHeader.startsWith(AUTH_HEADER_TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authToken = authHeader.substring(AUTH_HEADER_TYPE.length()).trim();
        log.info("Extracted Token: {}", authToken);

        // 验证 token 是否有效
        try {
            if (!JwtUtil.verifyToken(authToken)) {
                log.warn("Invalid token");
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            log.error("Token verification failed: ", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        // 从 token 中解析用户名和用户身份
        final String userAccount = JwtUtil.getUserAccountFromToken(authToken);
        final String userIdentity = JwtUtil.getUserIdentityFromToken(authToken);

        log.info("User Account: {}, User Identity: {}", userAccount, userIdentity);

        // 使用 UserService 获取用户信息
        User user = userService.getUserByUserAccount(userAccount); // 使用 UserService 查找用户
        if (user == null) {
            log.warn("User not found: {}", userAccount);
            filterChain.doFilter(request, response);
            return;
        }

        // 映射身份数字到角色
        String role = mapIdentityToRole(userIdentity);
        log.info("Mapped Role: {}", role);

        // 创建权限对象
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        // 创建认证对象
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUserAccount(), null, Collections.singletonList(authority));

        // 将认证信息放入 Spring Security 的上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    /**
     * 将数字身份映射为角色
     */
    private String mapIdentityToRole(String userIdentity) {
        switch (userIdentity) {
            case "0":
                return "ROLE_SEEKER"; // 求职者
            case "1":
                return "ROLE_INCUMBENT"; // 在职者
            case "2":
                return "ROLE_ADMIN"; // 管理员
            default:
                return "ROLE_GUEST"; // 默认角色
        }
    }
}
