package me.jazzy.obp.config.exception.notfound;

import me.jazzy.obp.config.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}