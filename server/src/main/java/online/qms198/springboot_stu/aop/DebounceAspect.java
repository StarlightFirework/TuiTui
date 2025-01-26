package online.qms198.springboot_stu.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.pojo.common.Debounce;
import online.qms198.springboot_stu.pojo.common.ResponseMessage;
import online.qms198.springboot_stu.utils.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import static online.qms198.springboot_stu.filter.JwtAuthenticationTokenFilter.AUTH_HEADER;
import static online.qms198.springboot_stu.filter.JwtAuthenticationTokenFilter.AUTH_HEADER_TYPE;

@Slf4j
@Aspect
@Component
public class DebounceAspect {

    private final ConcurrentHashMap<String, Long> requestTimestamps = new ConcurrentHashMap<>();

    @Around("@annotation(debounce)")
    public Object around(ProceedingJoinPoint joinPoint, Debounce debounce) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        // 获取当前请求的用户ID，假设用户ID通过请求头传递
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userAccount = JwtUtil.getUserAccountFromToken(request.getHeader(AUTH_HEADER).substring(AUTH_HEADER_TYPE.length()).trim());
        String key = userAccount + ":" + method.getName();

        long currentTime = System.currentTimeMillis();

        Long lastTime = requestTimestamps.get(key);
        if (lastTime != null && (currentTime - lastTime) < debounce.delay()) {
            // 如果在防抖时间内，则忽略这次调用
            log.info("用户 {} 访问接口 {} 过于频繁，防抖成功", userAccount, methodSignature.getMethod());
            return ResponseMessage.error(429, "请求太频繁，请稍后再试");
        }

        // 更新上次调用的时间戳
        requestTimestamps.put(key, currentTime);

        // 执行目标方法
        return joinPoint.proceed();
    }

    public ConcurrentHashMap<String, Long> getRequestTimestamps() {
        return requestTimestamps;
    }
}