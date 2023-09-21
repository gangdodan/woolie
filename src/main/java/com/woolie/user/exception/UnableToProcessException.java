package com.woolie.user.exception;


import com.woolie.common.exception.CustomException;
import com.woolie.common.exception.enums.ErrorCode;

public class UnableToProcessException extends CustomException {
    public UnableToProcessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
