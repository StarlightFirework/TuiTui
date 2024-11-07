package online.qms198.springboot_stu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> {
    private Integer code;
    private String message;
    private T data;

    // 接口请求成功
    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage(HttpStatus.OK.value(), "success!", data);
    }

    public static <T> ResponseMessage<T> success() {
        return new ResponseMessage(HttpStatus.OK.value(), "success!", null);
    }
}