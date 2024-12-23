package online.qms198.springboot_stu.utils;

import cn.hutool.crypto.digest.BCrypt;

public class EncryptionUtil {
    private final static String suffix = "sicnu";
    public static String advancedEncryption(String encryptionContent){
        return BCrypt.hashpw(encryptionContent + suffix);
    }

    public static boolean checkCiphertext(String plaintext,String ciphertext){
        return BCrypt.checkpw(plaintext + suffix,ciphertext);
    }
}
