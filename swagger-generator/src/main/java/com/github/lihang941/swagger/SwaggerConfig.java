package com.github.lihang941.swagger;

/**
 * @author : lihang941
 * @since : 2018/12/4
 */
public interface SwaggerConfig {

    String getFileName();

    String getProjectName();

    String getApiTitle();

    String getApiVersion();

    String getBuildPath();

    String getApiDesc();

    String getEmail();

    String[] getServerUrls();

    default String getTermsOfService() {
        return "http://swagger.io/terms/";
    }

    default String getLicenseName() {
        return "Apache 2.0";
    }

    default String getLicenseUrl() {
        return "http://www.apache.org/licenses/LICENSE-2.0.html";
    }
}
