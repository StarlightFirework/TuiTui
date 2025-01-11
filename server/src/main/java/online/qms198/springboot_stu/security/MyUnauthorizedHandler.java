package online.qms198.springboot_stu.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 未认证时回调，也就是说没有登录
 */
@Slf4j
@Component
public class MyUnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error", authException);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println("{\n" +
                "    \"code\": 401,\n" +
                "    \"message\": \"unauthorized!\",\n" +
                "    \"data\": null\n" +
                "}");
        response.getWriter().flush();
    }
}