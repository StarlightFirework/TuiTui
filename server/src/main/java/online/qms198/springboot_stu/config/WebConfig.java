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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/assets/**", "/images/**")
                .addResourceLocations("classpath:/static/");

    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 匹配没有扩展名的路径并回退到 index.html
        registry.addViewController("/{spring:[^.]+}").setViewName("forward:/index.html");
        registry.addViewController("/{spring:[^.]+}/**").setViewName("forward:/index.html");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://qms198.online", "http://117.72.104.77")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .exposedHeaders("Authorization")
                .maxAge(3600);
    }

}