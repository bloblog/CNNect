package com.ssafy.cnnect.exception;

import com.ssafy.cnnect.exception.code.ExceptionCode;

public class GlobalException extends RuntimeException {
    public GlobalException (ExceptionCode errorMessage) {
        super(errorMessage.getMessage());
    }
}
