package com.woolie.user.exception;

import com.woolie.common.exception.CustomException;
import com.woolie.common.exception.enums.ErrorCode;

public class TokenNotFoundException extends CustomException {
    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
