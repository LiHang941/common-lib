package com.github.lihang941.tool;


import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/10/22
 */
public class ServiceTemplate {

    static Configuration cfg;

    static String AUTHOR = "lihang1329@gmail.com";

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setClassForTemplateLoading(ServiceTemplate.class, "/template");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }


    public static List<ClassInfo> createService(String mapperPath, Config config) {
        return Arrays.asList(new File(mapperPath).listFiles())
                .stream()
                .filter(File::isFile)
                .map(file -> {
                    for (Table table : config.TABLES) {
                        String tableName = table.getTableName();
                        if (file.getName().toLowerCase().indexOf(tableName.replace("_", "").toLowerCase()) != -1) {
                            ClassInfo classInfo = ClassInfo.parseFile(file);
                            classInfo.createService = table.isCreateService();
                            classInfo.createConvert = table.isCreateConvert();
                            classInfo.createResource = table.isCreateResource();
                            return classInfo;
                        }
                    }
                    return null;
                })
                .filter(o -> o != null).collect(Collectors.toList());
    }


    public static File mkdirs(File basePath, String packageName) {
        String toPath = basePath.getAbsolutePath() + File.separator + (packageName.replaceAll("\\.", "/")) + File.separator;
        File file = mkdir(toPath);
        return file;
    }

    private static File mkdir(String toPath) {
        File file = new File(toPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    public static void parseResource(File path, String packageName, ClassInfo classInfo, String servicePackageName, String convertPackage, String dtoPackage) {
        if (classInfo.createResource == false) return;
        File resourcePath = mkdirs(path, packageName);
        File outFile = new File(resourcePath.getAbsolutePath() + File.separator + classInfo.entityName + "Resource.java");
        List<String> imp = new ArrayList<>();
        imp.add(servicePackageName + "." + classInfo.entityName + "Service");
        imp.add(convertPackage + "." + classInfo.entityName + "Convert");
        imp.add(dtoPackage + "." + classInfo.entityName + "Dto");
        imp.add("org.mdvsc.vertx.rest.*");
        imp.add("org.slf4j.Logger");
        imp.add("org.springframework.beans.factory.annotation.Autowired");
        imp.add("org.springframework.stereotype.Component");
        imp.add("io.vertx.core.http.HttpServerResponse");

        imp.add("com.github.pagehelper.Page");

        imp.add("xyz.lihang.common.page.OffsetBean");
        imp.add("xyz.lihang.common.logger.Log");
        imp.add("xyz.lihang.common.vertx.RequestTool");
        imp.add("xyz.lihang.common.bean.IdDto");

        imp.add("java.util.Arrays");
        imp.add("java.util.Map");
        imp.add("java.util.NoSuchElementException");
        imp.add("java.util.List");
        imp.add("java.util.stream.Collectors");
        imp.add(classInfo.getEntityPackage());
        createTemp(outFile, new JSONObject()
                        .fluentPut("author", AUTHOR)
                        .fluentPut("importList", imp)
                        .fluentPut("entityName", classInfo.entityName)
                        .fluentPut("idType", classInfo.IdType)
                        .fluentPut("packageName", packageName),
                "BaseResource.ftlh"
        )
        ;
    }


    public static void parseConvert(File path, String packageName, File dtoPath, String dtoPackageName, ClassInfo classInfo, File entityPath, String entityPackageName) {
        if (classInfo.createConvert == false) return;
        File toPath = mkdirs(path, packageName);

        {
            File dPath = mkdirs(dtoPath, dtoPackageName);


            File file = new File(dPath.getAbsolutePath() + File.separator + classInfo.entityName + "Dto.java");
            if (!file.exists()) {
                File entity = Arrays.asList(entityPath.listFiles()).stream().filter(f -> f.getName().equals(classInfo.entityName + ".java")).findFirst().get();
                try (FileInputStream fileInputStream = new FileInputStream(entity)) {
                    String s = IOUtils.toString(fileInputStream);
                    Pattern contentPattern = Pattern.compile("\\{[^\\\\]+\\}");
                    Matcher contentMatcher = contentPattern.matcher(s);
                    List<String> importList = ClassInfo.parseFileToImportList(s);
                    importList.add("com.alibaba.fastjson.annotation.JSONType");
                    if (contentMatcher.find()) {
                        Matcher json = Pattern.compile("private\\s+\\w++\\s+(.+);").matcher(contentMatcher.group());
                        StringBuilder sb = new StringBuilder();
                        while (json.find()) {
                            sb.append("\"")
                                    .append(json.group(1))
                                    .append("\"")
                                    .append(",")
                            ;
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        createTemp(file, new JSONObject()
                                        .fluentPut("author", AUTHOR)
                                        .fluentPut("importList", importList)
                                        .fluentPut("entityName", classInfo.entityName)
                                        .fluentPut("exampleName", classInfo.exampleName)
                                        .fluentPut("content", contentMatcher.group())
                                        .fluentPut("packageName", dtoPackageName)
                                        .fluentPut("jsonOder", sb.toString()),
                                "Dto.ftlh"
                        );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }


        File outFile = new File(toPath.getAbsolutePath() + File.separator + classInfo.entityName + "Convert.java");
        List<String> imp = new ArrayList<>();
        imp.add("java.util.function.Function");
        imp.add("xyz.lihang.common.bean.BeanConvert");
        imp.add(dtoPackageName + "." + classInfo.entityName + "Dto");
        imp.add(entityPackageName + "." + classInfo.entityName);
        createTemp(outFile, new JSONObject()
                        .fluentPut("author", AUTHOR)
                        .fluentPut("importList", imp)
                        .fluentPut("entityName", classInfo.entityName)
                        .fluentPut("exampleName", classInfo.exampleName)
                        .fluentPut("idType", classInfo.IdType)
                        .fluentPut("packageName", packageName),
                "Convert.ftlh"
        )
        ;
    }


    public static void parseService(File sPath, String servicePackageName, ClassInfo classInfo) {
        if (classInfo.createService == false) return;
        File servicePath = mkdirs(sPath, servicePackageName);


        {// service
            File outFile = new File(servicePath.getAbsolutePath() + File.separator + classInfo.entityName + "Service.java");
            List<String> imp = new ArrayList<>();
            imp.add("xyz.lihang.common.service.BaseService");
            imp.add(classInfo.getEntityPackage());
            createTemp(outFile, new JSONObject()
                            .fluentPut("author", AUTHOR)
                            .fluentPut("importList", imp)
                            .fluentPut("entityName", classInfo.entityName)
                            .fluentPut("idType", classInfo.IdType)
                            .fluentPut("packageName", servicePackageName),
                    "BaseServiceTemplate.ftlh"
            )
            ;
        }

        {// serviceImpl
            String impl = "impl";
            File implPath = mkdir(servicePath.getAbsolutePath() + File.separator + impl);
            File outFile = new File(implPath.getAbsolutePath() + File.separator + classInfo.entityName + "ServiceImpl.java");
            List<String> imp = new ArrayList<>();
            imp.add("xyz.lihang.common.service.BaseMapperService");
            imp.add("org.springframework.stereotype.Service");
            imp.add(classInfo.packageName + "." + classInfo.className);
            imp.add(servicePackageName + "." + classInfo.entityName + "Service");
            imp.addAll(classInfo.importList);
            imp.remove(imp.size() - 1);
            imp.remove(imp.size() - 1);
            imp.remove(imp.size() - 1);
            createTemp(outFile, new JSONObject()
                            .fluentPut("author", AUTHOR)
                            .fluentPut("importList", imp)
                            .fluentPut("entityName", classInfo.entityName)
                            .fluentPut("exampleName", classInfo.exampleName)
                            .fluentPut("idType", classInfo.IdType)
                            .fluentPut("packageName", servicePackageName + "." + impl),
                    "BaseServiceImplTemplate.ftlh"
            )
            ;
        }
    }


    public static void createTemp(File outFile, Object o, String template) {
        if (outFile.exists()) {
            return;
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile); OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);) {
            Template temp = cfg.getTemplate(template);
            temp.process(o, outputStreamWriter);
            System.out.println(outFile.getAbsolutePath());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
