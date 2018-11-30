package com.github.lihang941.generator.config;

import java.util.List;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class DaoConfig {

    public static final String MYSQL_DELIMITER = "`";
    public static final String POSTGRESQL_DELIMITER = "\\\"";

    private String beginningDelimiter;
    private String endingDelimiter;

    private PathPackage javaModel;
    private PathPackage sqlMap;
    private PathPackage javaClient;

    private JdbcConfig jdbcConfig;
    private List<Table> tables;


    public DaoConfig(PathPackage javaModel, PathPackage sqlMap, PathPackage javaClient, JdbcConfig jdbcConfig, List<Table> tables) {
        this.jdbcConfig = jdbcConfig;
        this.tables = tables;
        this.javaModel = javaModel;
        this.sqlMap = sqlMap;
        this.javaClient = javaClient;
    }

    public DaoConfig() {
    }

    public DaoConfig setDelimiter(String delimiter) {
        this.beginningDelimiter = delimiter;
        this.endingDelimiter = delimiter;
        return this;
    }

    public String getBeginningDelimiter() {
        return beginningDelimiter;
    }

    public DaoConfig setBeginningDelimiter(String beginningDelimiter) {
        this.beginningDelimiter = beginningDelimiter;
        return this;
    }

    public String getEndingDelimiter() {
        return endingDelimiter;
    }

    public DaoConfig setEndingDelimiter(String endingDelimiter) {
        this.endingDelimiter = endingDelimiter;
        return this;
    }

    public PathPackage getJavaModel() {
        return javaModel;
    }

    public DaoConfig setJavaModel(PathPackage javaModel) {
        this.javaModel = javaModel;
        return this;
    }

    public PathPackage getSqlMap() {
        return sqlMap;
    }

    public DaoConfig setSqlMap(PathPackage sqlMap) {
        this.sqlMap = sqlMap;
        return this;
    }

    public PathPackage getJavaClient() {
        return javaClient;
    }

    public DaoConfig setJavaClient(PathPackage javaClient) {
        this.javaClient = javaClient;
        return this;
    }

    public JdbcConfig getJdbcConfig() {
        return jdbcConfig;
    }

    public DaoConfig setJdbcConfig(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
        return this;
    }

    public List<Table> getTables() {
        return tables;
    }

    public DaoConfig setTables(List<Table> tables) {
        this.tables = tables;
        return this;
    }
}
