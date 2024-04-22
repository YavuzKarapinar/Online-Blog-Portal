package me.jazzy.obp.config.exception.badrequest;

import me.jazzy.obp.config.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserBadRequestException extends BaseException {
    public UserBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}