package me.jazzy.obp.config.exception.badrequest;

import me.jazzy.obp.config.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class TokenBadRequestException extends BaseException {
    public TokenBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
