package com.github.lihang941.tool;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/11
 */
public class Table {

    private boolean createService = true;
    private boolean createResource = true;
    private boolean generatedKey = true;
    private boolean createConvert = true;
    private String column;
    private String tableName;

    public boolean isCreateService() {
        return createService;
    }

    public Table setCreateService(boolean createService) {
        this.createService = createService;
        return this;
    }

    public boolean isCreateResource() {
        return createResource;
    }

    public Table setCreateResource(boolean createResource) {
        this.createResource = createResource;
        return this;
    }

    public boolean isGeneratedKey() {
        return generatedKey;
    }

    public Table setGeneratedKey(boolean generatedKey) {
        this.generatedKey = generatedKey;
        return this;
    }

    public boolean isCreateConvert() {
        return createConvert;
    }

    public Table setCreateConvert(boolean createConvert) {
        this.createConvert = createConvert;
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
