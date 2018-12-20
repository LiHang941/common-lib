package com.github.lihang941.generator.config;

/**
 * @author : lihang941
 * @since : 2018/11/28
 */
public class DtoConfig {

    private PathPackage dto;
    private PathPackage convert;
    private boolean lombok;

    public DtoConfig() {
    }

    public DtoConfig(PathPackage dto, PathPackage convert) {
        this.dto = dto;
        this.convert = convert;
    }

    public PathPackage getDto() {
        return dto;
    }

    public DtoConfig setDto(PathPackage dto) {
        this.dto = dto;
        return this;
    }

    public PathPackage getConvert() {
        return convert;
    }

    public DtoConfig setConvert(PathPackage convert) {
        this.convert = convert;
        return this;
    }

    public boolean isLombok() {
        return lombok;
    }

    public DtoConfig setLombok(boolean lombok) {
        this.lombok = lombok;
        return this;
    }
}
