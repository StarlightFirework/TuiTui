package online.qms198.springboot_stu.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import online.qms198.springboot_stu.constants.MyConstant;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    /**
     * 生成 JWT Token
     */
    public static String createToken(String userAccount) {
        return JWT.create()
                .setPayload("userAccount", userAccount)
                .setPayload("iat", new Date())
                .setPayload("exp", new Date(System.currentTimeMillis() + 1209600 * 1000))
                .setKey(MyConstant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                .sign();
    }

    /**
     * 验证和解析 token 是否有效
     */
    public static boolean verifyToken(String token) {
        try {
            return JWTUtil.verify(token, MyConstant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("Token verification failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * 解析用户名
     */
    public static String getUserAccountFromToken(String token) {
        return (String) JWTUtil.parseToken(token).getPayload("userAccount");
    }
}
