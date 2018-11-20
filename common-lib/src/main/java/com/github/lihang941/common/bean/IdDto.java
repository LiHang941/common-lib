package com.github.lihang941.common.bean;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/2
 */
public class IdDto {

    private Object id;

    public IdDto(Object id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }

    public IdDto setId(Object id) {
        this.id = id;
        return this;
    }
}
