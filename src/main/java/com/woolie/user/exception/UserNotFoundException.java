package com.woolie.user.exception;


import com.woolie.common.exception.CustomException;
import com.woolie.common.exception.enums.ErrorCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
