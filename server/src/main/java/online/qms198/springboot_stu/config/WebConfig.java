package online.qms198.springboot_stu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 匹配所有请求路径
                .allowedOrigins("http://localhost:3000")  // 替换为前端实际访问的地址
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的 HTTP 方法
                .allowCredentials(true);  // 允许发送认证信息（如 cookies）
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 匹配没有扩展名的路径并回退到 index.html
        registry.addViewController("/{spring:[^.]+}").setViewName("forward:/index.html");
        registry.addViewController("/{spring:[^.]+}/**").setViewName("forward:/index.html");
    }


}