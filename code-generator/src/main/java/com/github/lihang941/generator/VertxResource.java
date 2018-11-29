package com.github.lihang941.generator;

import com.alibaba.fastjson.JSONObject;
import com.github.lihang941.TemplateGenerator;
import com.github.lihang941.generator.config.DtoConfig;
import com.github.lihang941.generator.config.ResourceConfig;
import com.github.lihang941.generator.config.ServiceConfig;
import com.github.lihang941.generator.tool.FileTool;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class VertxResource extends TemplateGenerator {

    private List<File> files;

    private DtoConfig dtoConfig;
    private ServiceConfig serviceConfig;
    private ResourceConfig resourceConfig;
    private File resourcePath;

    public VertxResource(ResourceConfig resourceConfig, DtoConfig dtoConfig, ServiceConfig serviceConfig, String modePath) {
        this(resourceConfig, dtoConfig, serviceConfig, Arrays.asList(new File(modePath)).stream().flatMap(file -> Arrays.asList(file.listFiles()).stream()).map(File::getAbsolutePath).collect(Collectors.toList()));
    }

    public VertxResource(ResourceConfig resourceConfig, DtoConfig dtoConfig, ServiceConfig serviceConfig, List<String> models) {
        this.resourceConfig = resourceConfig;
        this.serviceConfig = serviceConfig;
        this.files = models.stream().map(File::new).filter(File::isFile).collect(Collectors.toList());
        this.dtoConfig = dtoConfig;
        this.resourcePath = mkdir(resourceConfig.getResource());
    }

    @Override
    public void generator() {
        this.files.stream().filter(file -> !this.isWhite(file.getName())).forEach(this::generator);
    }


    public void generator(File file) {
        String content = toContent(file);
        String className = FileTool.getClassName(content);
        String idType = FileTool.getIdType(content);
        String idName = FileTool.getIdName(content);


        List<String> imp = new ArrayList<>();
        imp.add(serviceConfig.getService().getPackageName() + "." + className + "Service");
        imp.add(dtoConfig.getConvert().getPackageName() + "." + className + "Convert");
        imp.add(dtoConfig.getDto().getPackageName() + "." + className + "Dto");
        imp.add(FileTool.getPackage(content) + "." + className);
        imp.add("com.github.lihang941.vertx.rest.*");
        if (this.resourceConfig.isPage()){
            imp.add("com.github.lihang941.common.page.PageNumBean");
        }else {
            imp.add("com.github.lihang941.common.page.OffsetBean");
        }
        imp.add("com.github.lihang941.common.vertx.RequestTool");
        imp.add("com.github.lihang941.common.bean.IdDto");
        imp.add("com.github.pagehelper.Page");
        imp.add("io.vertx.core.http.HttpServerResponse");
        imp.add("org.slf4j.Logger");
        imp.add("org.slf4j.LoggerFactory");
        imp.add("org.springframework.beans.factory.annotation.Autowired");



        imp.add("java.util.Map");
        imp.add("java.util.NoSuchElementException");
        imp.add("java.util.stream.Collectors");

        createTemp(Paths.get(resourcePath.getAbsolutePath(), className + "Resource.java").toFile(), new JSONObject()
                        .fluentPut("author", AUTHOR)
                        .fluentPut("importList", imp)
                        .fluentPut("entityName", className)
                        .fluentPut("idType", idType)
                        .fluentPut("idName", idName)
                        .fluentPut("isPage", this.resourceConfig.isPage())
                        .fluentPut("packageName", this.resourceConfig.getResource().getPackageName()),
                "VertxResource.ftlh"
        );
    }
}
