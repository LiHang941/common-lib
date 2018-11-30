package com.github.lihang941.generator.config;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class JdbcConfig {

    private String password;
    private String driverClass;
    private String connectionURL;
    private String userId;

    public String getPassword() {
        return password;
    }

    public JdbcConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public JdbcConfig setDriverClass(String driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public JdbcConfig setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public JdbcConfig setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
