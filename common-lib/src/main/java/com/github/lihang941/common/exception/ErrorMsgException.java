package com.github.lihang941.common.exception;

import java.text.MessageFormat;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/7/5
 */
public class ErrorMsgException extends RuntimeException {

    private String errorMessage;

    private int code = 400;

    /**
     * Available Message Template
     *
     * @param errorMessage
     * @param args
     */
    public ErrorMsgException(String errorMessage, Object... args) {
        super(errorMessage);
        if (args == null || args.length == 0) {
            this.errorMessage = errorMessage;
        } else {
            this.errorMessage = MessageFormat.format(errorMessage, args);
        }

    }

    public ErrorMsgException(int code, String errorMessage, Object... args) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.code = code;
        if (args == null || args.length == 0) {
            this.errorMessage = errorMessage;
        } else {
            this.errorMessage = MessageFormat.format(errorMessage, args);
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    public int getCode() {
        return code;
    }

}
