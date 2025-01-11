package online.qms198.springboot_stu.controller;

//import cn.hutool.jwt.JWT;
import jakarta.validation.Valid;
import online.qms198.springboot_stu.pojo.common.ResponseMessage;
import online.qms198.springboot_stu.pojo.user.User;
import online.qms198.springboot_stu.dto.user.UserLoginDto;
import online.qms198.springboot_stu.dto.user.UserRegisterDto;
import online.qms198.springboot_stu.service.user.IUserService;
import online.qms198.springboot_stu.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController // 标记请求处理类。接口方法返回对象，转换成json文本
@RequestMapping("/user") // localhost:8088/user/，标记拦截user URL前缀地址类
@CrossOrigin(
        origins = {"http://localhost:3000", "https://qms198.online"},
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS},
        allowCredentials = "true",
        exposedHeaders = {"Authorization"}
)
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);//日志记录对象
    @Autowired
    IUserService userService;

    // 增加
    // URL: localhost:8088/user
    @PostMapping("/register")
    public ResponseMessage<User> add(@Valid @Validated @RequestBody UserRegisterDto user) {
        User userNew = userService.add(user);
        return ResponseMessage.success(userNew);
    }

//    @PostMapping("/login")
//    public ResponseMessage<User> login(@Valid @RequestBody UserLoginDto userLoginDto) {
//        logger.info("Attempting login for user: {}", userLoginDto.getUserAccount());
//        User authenticatedUser = userService.authenticate(userLoginDto.getUserAccount(), userLoginDto.getPassword());
//        if (authenticatedUser != null) {
//            return ResponseMessage.success(authenticatedUser);
//        } else {
//            return new ResponseMessage<>(401, "Invalid username or password", null);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage<User>> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        logger.info("Attempting login for user: {}", userLoginDto.getUserAccount());
        User authenticatedUser = userService.authenticate(userLoginDto.getUserAccount(), userLoginDto.getPassword());

        if (authenticatedUser != null) {
            String token = JwtUtil.createToken(authenticatedUser.getUserAccount(), authenticatedUser.getUserIdentity());

            logger.info("Generated JWT token: {}", token);
            // 创建响应消息
            ResponseMessage<User> responseMessage = ResponseMessage.success(authenticatedUser);

            // 返回带有 token 的响应头
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(responseMessage);
        } else {
            ResponseMessage<User> responseMessage = new ResponseMessage<>(401, "Invalid username or password", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        }
    }


    // 查询
    @GetMapping("/userAccount")
    public ResponseMessage<User> get(@RequestParam String userAccount) {
        logger.info("Querying user with account: {}", userAccount);
        User userNew = userService.getUserByUserAccount(userAccount);
        return ResponseMessage.success(userNew);
    }

    // 修改
    @PutMapping("/edit")
    public ResponseMessage<User> edit(@Validated @RequestBody UserRegisterDto user) {
        User userNew = userService.edit(user);
        return ResponseMessage.success(userNew);
    }

    // 删除
    @DeleteMapping("/delete/{userId}") // URL: localhost:8088/user/userId method: delete
    public ResponseMessage<User> delete(@PathVariable Integer userId) {
        if(userService.delete(userId)){
            return ResponseMessage.success();
        }
        return ResponseMessage.error();
    }
}