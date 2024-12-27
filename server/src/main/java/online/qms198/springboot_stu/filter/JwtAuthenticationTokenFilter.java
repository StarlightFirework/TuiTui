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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
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
        logger.info(authHeader);
        if (Objects.isNull(authHeader) || !authHeader.startsWith(AUTH_HEADER_TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authToken = authHeader.split(" ")[1];
        log.info("authToken: {}", authToken);

        // 验证 token 是否有效
        try {
            if (!JwtUtil.verifyToken(authToken)) {
                log.info("Invalid token");
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            log.error("Token verification failed: ", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }


        // 从 token 中解析用户名（假设你存储了用户的用户名在 JWT 中）
        final String userAccount = JwtUtil.getUserAccountFromToken(authToken);


        // 使用 UserService 获取用户信息
        User user = userService.getUserByUserAccount(userAccount); // 使用 UserService 查找用户
        if (user == null) {
            log.info("User not found: {}", userAccount);
            filterChain.doFilter(request, response);
            return;
        }

        // 创建认证对象
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUserAccount(), user.getPassword(), null); // null for authorities

        // 将认证信息放入 Spring Security 的上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
        return;
    }


}
