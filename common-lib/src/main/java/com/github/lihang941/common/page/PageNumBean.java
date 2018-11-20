package com.github.lihang941.common.page;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/4
 */
public class PageNumBean {
    private int pageNum;
    private int pageSize;

    public PageNumBean(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageNumBean() {
    }

    public int getPageNum() {
        return pageNum;
    }

    public PageNumBean setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageNumBean setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
