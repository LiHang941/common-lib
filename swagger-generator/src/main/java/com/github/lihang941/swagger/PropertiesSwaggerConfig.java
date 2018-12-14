package com.github.lihang941.swagger;

import java.io.*;

public class PropertiesSwaggerConfig implements SwaggerConfig {
    /**
     * 生成swagger specification 文件的路径
     */
    public static final String BUILD_PATH = "buildPath";
    /**
     * 生成swagger specification 文件的名字
     */
    public static final String FILE_NAME = "fileName";
    /**
     * 项目的名称
     */
    public static final String PROJECT_NAME = "projectName";
    /**
     * 扫描包的路径，若为空，则扫描全部包
     */
    public static final String SCAN_PATH = "scanPath";
    /**
     * 该文档的描述
     */
    public static final String API_DESC = "apiDesc";
    /**
     * api文档的版本号
     */
    public static final String API_VERSION = "apiVersion";
    /**
     * 该api文档的标题
     */
    public static final String API_TITLE = "apiTitle";
    /**
     * 主机地址
     */
    public static final String API_HOST = "apiHost";
    /**
     * 基本地址，例如：/api
     */
    public static final String API_BASE_PATH = "apiBasePath";
    /**
     * 服务条款地址
     */
    public static final String TERMS_OF_SERVICE = "termsOfService";
    /**
     * 邮箱地址
     */
    public static final String EMAIL = "email";
    /**
     * 协议名称
     */
    public static final String LICENSE_NAME = "licenseName";
    /**
     * 协议地址
     */
    public static final String LICENSE_URL = "licenseUrl";

    /**
     * 默认的请求协议
     */
    public static final String SERVER_URLS = "serverUrls";


    private java.util.Properties properties;

    public PropertiesSwaggerConfig() {
        this.properties = new java.util.Properties();
    }

    public PropertiesSwaggerConfig load(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(new File(path));) {
            load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }


    public PropertiesSwaggerConfig load(InputStream inputStream) {
        try {
            properties.load(new BufferedReader(new InputStreamReader(inputStream, "utf-8")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public String getFileName() {
        return properties.getProperty(FILE_NAME);
    }

    @Override
    public String getProjectName() {
        return properties.getProperty(PROJECT_NAME);
    }

    @Override
    public String getApiTitle() {
        return properties.getProperty(API_TITLE);
    }

    @Override
    public String getApiVersion() {
        return properties.getProperty(API_VERSION);
    }

    @Override
    public String getBuildPath() {
        return properties.getProperty(BUILD_PATH);
    }

    @Override
    public String getApiDesc() {
        return properties.getProperty(API_DESC);
    }

    @Override
    public String getEmail() {
        return properties.getProperty(EMAIL);
    }

    @Override
    public String getTermsOfService() {
        return properties.getProperty(TERMS_OF_SERVICE);
    }

    @Override
    public String getLicenseName() {
        return properties.getProperty(LICENSE_NAME);
    }

    @Override
    public String getLicenseUrl() {
        return properties.getProperty(LICENSE_URL);
    }

    @Override
    public String[] getServerUrls() {
        return properties.getProperty(SERVER_URLS).split(",");
    }
}
