package com.github.lihang941.generator.config.dao;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class Table {

    private boolean generatedKey = true;
    private String column;
    private String tableName;

    public Table(String column, String tableName) {
        this.column = column;
        this.tableName = tableName;
    }

    public Table(boolean generatedKey, String column, String tableName) {
        this.generatedKey = generatedKey;
        this.column = column;
        this.tableName = tableName;
    }

    public boolean isGeneratedKey() {
        return generatedKey;
    }

    public Table setGeneratedKey(boolean generatedKey) {
        this.generatedKey = generatedKey;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public Table setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public Table setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}
