package swteam6.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean isSuccess;
    private int code;
    private String message;
    private T data;

    public ApiResponse(boolean success,int code,String message){
        this.message = message;
        this.isSuccess= success;
        this.code = code;
    }
}
