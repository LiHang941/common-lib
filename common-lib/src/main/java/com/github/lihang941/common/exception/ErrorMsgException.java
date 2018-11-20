package com.github.lihang941.common.exception;

import java.text.MessageFormat;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/7/5
 */
public class ErrorMsgException extends RuntimeException {

    private String errorMessage;

    private int code = 400;

    public ErrorMsgException(String errorMessage, Object... obj) {
        super(errorMessage);
        if (obj == null || obj.length == 0) {
            this.errorMessage = errorMessage;
        } else {
            this.errorMessage = MessageFormat.format(errorMessage, obj);
        }

    }

    public ErrorMsgException(int code, String errorMessage, Object... obj) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.code = code;
        if (obj == null || obj.length == 0) {
            this.errorMessage = errorMessage;
        } else {
            this.errorMessage = MessageFormat.format(errorMessage, obj);
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    public int getCode() {
        return code;
    }

}
