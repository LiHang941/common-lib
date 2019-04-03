package com.github.lihang941.common.exception;

/**
 * User: lihang1329@gmail.com
 * Date: 2018-04-26 下午3:00
 */
public class TokenInvalidException extends RuntimeException {

    private String errorMessage;

    public TokenInvalidException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public TokenInvalidException(String message, String errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public TokenInvalidException setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
