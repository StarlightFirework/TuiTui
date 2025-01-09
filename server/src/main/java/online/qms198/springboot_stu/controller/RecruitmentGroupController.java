package online.qms198.springboot_stu.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController // 标记请求处理类。接口方法返回对象，转换成json文本
@RequestMapping("/group") // localhost:8088/user/，标记拦截user URL前缀地址类
public class RecruitmentGroupController {

}
