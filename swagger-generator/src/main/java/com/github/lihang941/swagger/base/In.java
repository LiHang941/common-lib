package com.github.lihang941.swagger.base;

public enum In {
    OTHER(null),
    QUERY("query"),
    HEADER("header"),
    PATH("path"),
    FORM_DATA("formData"),
    BODY("body");
    private String param;

    public String param() {
        return param;
    }

    In(String param) {
        this.param = param;
    }
}
