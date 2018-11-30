package com.github.lihang941.generator.config;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class Table {

    private String tableName;
    private String column;
    private boolean generatedKey = true;
    private String sqlStatement = "JDBC";
    private boolean identity = true;
    private String type;

    public Table(String column, String tableName) {
        this.tableName = tableName;
        this.column = column;
    }

    public Table(boolean generatedKey, String column, String tableName) {
        this.generatedKey = generatedKey;
        this.tableName = tableName;
        this.column = column;
    }

    public boolean isGeneratedKey() {
        return generatedKey;
    }

    public Table setGeneratedKey(boolean generatedKey) {
        this.generatedKey = generatedKey;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public Table setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    public Table setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
        return this;
    }

    public boolean isIdentity() {
        return identity;
    }

    public Table setIdentity(boolean identity) {
        this.identity = identity;
        return this;
    }

    public String getType() {
        return type;
    }

    public Table setType(String type) {
        this.type = type;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public Table setColumn(String column) {
        this.column = column;
        return this;
    }
}
