package com.github.lihang941.generator;

import com.alibaba.fastjson.JSONObject;
import com.github.lihang941.TemplateGenerator;
import com.github.lihang941.generator.config.ServiceConfig;
import com.github.lihang941.generator.config.DaoConfig;
import com.github.lihang941.generator.tool.FileTool;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class Service extends TemplateGenerator {

    private List<File> files;

    private ServiceConfig serviceConfig;
    private DaoConfig daoConfig;
    private File servicePath;
    private File serviceImplPath;

    public Service(ServiceConfig serviceConfig,DaoConfig daoConfig, String modePath) {
        this(serviceConfig, daoConfig,Arrays.asList(new File(modePath)).stream().flatMap(file -> Arrays.asList(file.listFiles()).stream()).map(File::getAbsolutePath).collect(Collectors.toList()));
    }

    public Service(ServiceConfig serviceConfig,DaoConfig daoConfig, List<String> models) {
        this.files = models.stream().map(File::new).filter(File::isFile).collect(Collectors.toList());
        this.serviceConfig = serviceConfig;
        this.daoConfig = daoConfig;
        this.servicePath = mkdir(this.serviceConfig.getService());
        this.serviceImplPath = mkdir(this.serviceConfig.getServiceImpl());
    }


    @Override
    public void generator() {
        this.files.stream().filter(file -> !this.isBlackList(file.getName())).forEach(this::generator);
    }


    public void generator(File file) {
        String content = toContent(file);
        String className = FileTool.getClassName(content);
        String packageName = FileTool.getPackage(content);
        String idType = FileTool.getIdType(content);
        {// service
            List<String> imp = new ArrayList<>();
            imp.add("com.github.lihang941.common.service.BaseService");
            imp.add(packageName + "." + className);
            createTemp(Paths.get(servicePath.getAbsolutePath(), className + "Service.java").toFile(), new JSONObject()
                            .fluentPut("author", AUTHOR)
                            .fluentPut("importList", imp)
                            .fluentPut("entityName", className)
                            .fluentPut("idType", idType)
                            .fluentPut("packageName", this.serviceConfig.getService().getPackageName()),
                    "BaseServiceTemplate.ftlh"
            )
            ;
        }

        {// serviceImpl
            File outFile = Paths.get(serviceImplPath.getAbsolutePath(), className + "ServiceImpl.java").toFile();
            List<String> imp = new ArrayList<>();
            imp.add("com.github.lihang941.common.service.BaseMapperService");
            imp.add("org.springframework.stereotype.Service");
            imp.add(this.serviceConfig.getService().getPackageName() + "." + className + "Service");
            imp.add(this.daoConfig.getJavaClient().getPackageName() + "." + className + "Mapper");
            imp.add(packageName + "." + className);
            createTemp(outFile, new JSONObject()
                            .fluentPut("author", AUTHOR)
                            .fluentPut("importList", imp)
                            .fluentPut("entityName", className)
                            .fluentPut("idType", idType)
                            .fluentPut("packageName", this.serviceConfig.getServiceImpl().getPackageName()),
                    "BaseServiceImplTemplate.ftlh"
            )
            ;
        }
    }

}
