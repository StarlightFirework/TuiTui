package online.qms198.springboot_stu.config;

import online.qms198.springboot_stu.filter.JwtAuthenticationTokenFilter;
import online.qms198.springboot_stu.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private MyUnauthorizedHandler unauthorizedHandler;

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider();
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 匹配所有请求路径
                .allowedOrigins("http://localhost:3000", "http://qms198.online")  // 替换为前端实际访问的地址
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的 HTTP 方法
                .allowedHeaders("*")
                .allowCredentials(true);  // 允许发送认证信息（如 cookies）
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //由于使用的是JWT，这里不需要csrf防护
        httpSecurity.csrf(CsrfConfigurer::disable)
                //基于token，所以不需要session
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizationRegistry -> authorizationRegistry
                // -1级权限
                        //允许对于网站静态资源的无授权访问
                        .requestMatchers("/*.ico", "/*.ttf", "/*.js", "/*.html", "/client/**", "/login", "/findJob").permitAll()
                        //对登录注册允许匿名访问
                        .requestMatchers("/user/login", "/user/register",  "user/userAccount").permitAll()
                        // OPTIONS 请求允许匿名访问（跨域预检）
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                // 0级权限
                        // 招聘信息查询
                        .requestMatchers("/recruit/get/all", "/recruit/get/id/").hasAnyRole("SEEKER", "INCUMBENT", "ADMIN")
                        // 标签查询
                        .requestMatchers("tags/get/id/", "tags/search").hasAnyRole("SEEKER", "INCUMBENT", "ADMIN")
                        // 招聘信息统计
                        .requestMatchers("/recruit/statistics/view", "recruit/statistics/collectionPlus", "recruit/statistics/collectionMinus").hasAnyRole("SEEKER", "INCUMBENT", "ADMIN")
                        // 招聘圈查询
                        .requestMatchers("group/get/",  "group/selectUser").hasAnyRole("SEEKER", "INCUMBENT", "ADMIN")
                        // 标签大类查询
                        .requestMatchers("tag-classifications/get/all", "tag-classifications/get/id").hasAnyRole("SEEKER", "INCUMBENT", "ADMIN")
                // 1级权限
                        // 招聘信息增删改
                        .requestMatchers("recruit/insert", "recruit/edit", "recruit/delete/").hasAnyRole("INCUMBENT", "ADMIN")
                        // 招聘圈增删改
                        .requestMatchers("group/create", "group/edit", "group/delete/").hasAnyRole("INCUMBENT", "ADMIN")
                        // 招聘圈管理
                        .requestMatchers("group/addUser", "group/deleteUser").hasAnyRole("INCUMBENT", "ADMIN")
                // 2级权限
                        // 用户修改、删除
                        .requestMatchers("/user/delete/", "/user/edit").hasRole("ADMIN")
                        // 招聘信息审核及查询
                        .requestMatchers("recruit/audit/get", "recruit/audit/update").hasRole("ADMIN")
                        // 标签的增删改
                        .requestMatchers("tags/create", "tags/update", "tags/delete/").hasRole("ADMIN")
                        // 标签大类的增删改
                        .requestMatchers("/tag-classifications/create", "tag-classifications/update/", "tag-classifications/delete/").hasRole("ADMIN")
                //除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated()
                )

                //禁用缓存
                .headers(headersConfigurer -> headersConfigurer
                        .cacheControl(HeadersConfigurer.CacheControlConfig::disable)
                )
                //使用自定义provider
                .authenticationProvider(jwtAuthenticationProvider())
                //添加JWT filter
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                //添加自定义未授权和未登录结果返回
                .exceptionHandling(exceptionConfigurer -> exceptionConfigurer
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(unauthorizedHandler));
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();  // 使用自定义的 UserDetailsService
    }
}