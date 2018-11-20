package com.github.lihang941.common.bean;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/8/30
 */
public class BaseResult {

    private int code;
    private String msg;
    private Object data;

    public BaseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(Object data) {
        this.code = 200;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public BaseResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public BaseResult setData(Object data) {
        this.data = data;
        return this;
    }
}
