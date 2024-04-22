package me.jazzy.obp.config.exception.notfound;

import me.jazzy.obp.config.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends BaseException {

    public PostNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}