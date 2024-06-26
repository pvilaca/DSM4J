package responses;

import utils.DsmUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;

public class Response<T>  {

    private boolean success;

    private T data;

    private Error error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    @Data
    public static class Error {
        private String code;
        private ErrorDetail errors;

      
        public Optional<String> getDescription() {
            if(Optional.ofNullable(code).isPresent()) {
                return Optional.of(DsmUtils.manageErrorMessage(Integer.valueOf(code)));
            }
           return Optional.empty();
        }


        @Data
        public static class ErrorDetail {
            String line;
            String message;

        }

    }
}
