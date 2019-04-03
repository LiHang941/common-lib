package com.github.lihang941.common.exception;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/7/5
 */
public class VerificationInvalidException extends RuntimeException {

    private String errorMessage;

    public VerificationInvalidException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public VerificationInvalidException(String message, String errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public VerificationInvalidException setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

}
