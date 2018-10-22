package xyz.lihang.common.exception;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/7/5
 */
public class ErrorMsgException extends RuntimeException {

    private String errorMessage;

    private int code = 400;

    public ErrorMsgException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ErrorMsgException(int code,String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    public int getCode() {
        return code;
    }
}
