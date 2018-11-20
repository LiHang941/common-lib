package com.github.lihang941.tool;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/11
 */
public class JDBCConfig {


    private String password;
    private String driverClass;
    private String connectionURL;
    private String userId;

    public String getPassword() {
        return password;
    }

    public JDBCConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public JDBCConfig setDriverClass(String driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public JDBCConfig setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public JDBCConfig setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
