package com.woolie.common.exception;


import com.woolie.common.exception.enums.ErrorCode;

public class CanNotConvertException extends CustomException {
    public CanNotConvertException(ErrorCode errorCode) {
        super(errorCode);
    }
}
