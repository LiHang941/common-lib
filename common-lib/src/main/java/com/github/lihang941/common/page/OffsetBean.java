package com.github.lihang941.common.page;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/4
 */
public class OffsetBean {
    private int offSet ;
    private int size;

    public OffsetBean() {
    }

    public OffsetBean(int offSet, int size) {
        this.offSet = offSet;
        this.size = size;
    }

    public int getOffSet() {
        return offSet;
    }

    public OffsetBean setOffSet(int offSet) {
        this.offSet = offSet;
        return this;
    }

    public int getSize() {
        return size;
    }

    public OffsetBean setSize(int size) {
        this.size = size;
        return this;
    }
}
