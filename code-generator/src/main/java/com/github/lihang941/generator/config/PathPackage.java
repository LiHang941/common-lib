package com.github.lihang941.generator.config;

import com.github.lihang941.TemplateGenerator;

/**
 * @author : lihang941
 * @since : 2018/11/28
 */
public class PathPackage {

    private String path;
    private String packageName;

    public PathPackage(String path, String packageName) {
        this.path = path;
        this.packageName = packageName;
    }

    public String getPath() {
        return path;
    }

    public PathPackage setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public PathPackage setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String toFilePath() {
        return TemplateGenerator.mkdir(this).getAbsolutePath();
    }
}
