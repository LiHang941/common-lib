package com.github.lihang941.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/11
 */
public class Config {

    public static class PATH_CONFIG {
        public static String MAPPER_PATH = "mapper/";
        public static String MAPPER_PACKAGE = ".mapper";

        public static String SERVICE_PATH = "service/src/main/java";
        public static String SERVICE_PACKAGE = ".service";

        public static String CONVERT_PATH = "service/src/main/java";
        public static String CONVERT_PACKAGE = ".service.convert";

        public static String DTO_PATH = "api/src/main/java";
        public static String DTO_PACKAGE = ".dto";

        public static String RESOURCE_PATH = "facade/src/main/java";
        public static String RESOURCE_PACKAGE = ".facade.resource";

    }


    public String ABS_PATH;
    public String BASE_PACKAGE;

    public String MAPPER_PATH;
    public String MAPPER_PACKAGE;

    public String SERVICE_PATH;
    public String SERVICE_PACKAGE;

    public String CONVERT_PATH;
    public String CONVERT_PACKAGE;

    public String DTO_PATH;
    public String DTO_PACKAGE;

    public String RESOURCE_PATH;
    public String RESOURCE_PACKAGE;


    public String ENTITY_PATH;
    public String ENTITY_PACKAGE;
    public String DAO_PATH;

    public JDBCConfig JDBC_CONNECTION_CONFIG;
    public List<Table> TABLES;

    public Config(JDBCConfig JDBC_CONNECTION_CONFIG, Table... tables) {
        this.JDBC_CONNECTION_CONFIG = JDBC_CONNECTION_CONFIG;
        this.TABLES = Arrays.asList(tables);
    }

    public Config serializ(String absPath, String basePackage) {
        this.ABS_PATH = absPath;
        this.BASE_PACKAGE = basePackage;

        this.MAPPER_PATH = absPath + PATH_CONFIG.MAPPER_PATH;
        this.MAPPER_PACKAGE = basePackage + PATH_CONFIG.MAPPER_PACKAGE;

        this.SERVICE_PATH = absPath + PATH_CONFIG.SERVICE_PATH;
        this.SERVICE_PACKAGE = basePackage + PATH_CONFIG.SERVICE_PACKAGE;

        this.CONVERT_PATH = absPath + PATH_CONFIG.CONVERT_PATH;
        this.CONVERT_PACKAGE = basePackage + PATH_CONFIG.CONVERT_PACKAGE;

        this.DTO_PATH = absPath + PATH_CONFIG.DTO_PATH;
        this.DTO_PACKAGE = basePackage + PATH_CONFIG.DTO_PACKAGE;

        this.RESOURCE_PATH = absPath + PATH_CONFIG.RESOURCE_PATH;
        this.RESOURCE_PACKAGE = basePackage + PATH_CONFIG.RESOURCE_PACKAGE;
        return this;
    }


}
