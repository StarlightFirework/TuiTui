package online.qms198.springboot_stu.pojo.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResponseMessage<T> {
    private Integer code;
    private String message;
    private T data;
    public ResponseMessage(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 接口请求成功
    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage(HttpStatus.OK.value(), "success!", data);
    }

    public static <T> ResponseMessage<T> success() {
        return new ResponseMessage(HttpStatus.OK.value(), "success!", null);
    }

    public static <T> ResponseMessage<T> error() {
        return new ResponseMessage(HttpStatus.MULTI_STATUS.value(), "error!", null);
    }

    public static <T> ResponseMessage<T> error(String errorMessage) {
        return new ResponseMessage(HttpStatus.MULTI_STATUS.value(), "error!", errorMessage);
    }
}