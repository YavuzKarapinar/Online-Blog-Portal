package me.jazzy.obp.config.exception.base;

import me.jazzy.obp.dto.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseBody> exceptionHandling(BaseException baseException) {

        ResponseBody responseBody = new ResponseBody(
                baseException.getStatus().value(),
                baseException.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseBody, baseException.getStatus());
    }
}