package com.ssafy.cnnect.exception;

import com.ssafy.cnnect.exception.code.ExceptionCode;

public class UnAuthorizedException extends GlobalException {
    public UnAuthorizedException(ExceptionCode errorMessage) {
        super(errorMessage);
    }
}
