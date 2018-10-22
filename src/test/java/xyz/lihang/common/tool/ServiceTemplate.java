package xyz.lihang.common.tool;


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


    public static void createService(String servicePath, String packageName, String mapperPath) throws IOException {
        Arrays.asList(new File(mapperPath).listFiles()).stream().filter(File::isFile).forEach(file -> {
            File outFile = new File(servicePath);
            if (!outFile.exists()) {
                outFile.mkdirs();
            }

            parseService(
                    outFile,
                    packageName,
                    file
            );
        });
    }


    static Pattern packageRegex = Pattern.compile("package\\s+(.+);");
    static Pattern interfaceRegex = Pattern.compile("interface\\s+(\\w+)\\s+extends\\s+BaseMapper<(\\w+),\\s+(\\w+),\\s+(\\w+)>");
    static Pattern importRegex = Pattern.compile("import (.+);");

    public static void parseService(File outPath, String servicePackageName, File mapperFile) {
        String toPath = outPath.getAbsolutePath() + File.separator + (servicePackageName.replaceAll("\\.", "/")) + File.separator;
        {
            File file = new File(toPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        try (FileInputStream fileInputStream = new FileInputStream(mapperFile)) {
            String s = IOUtils.toString(fileInputStream, "UTF-8");
            Matcher packageMatcher = packageRegex.matcher(s);
            String packageName = null;
            if (packageMatcher.find()) {
                packageName = packageMatcher.group(1);
            }

            String className = null;
            String entityName = null;
            String exampleName = null;
            String IdType = null;

            Matcher interfaceMatcher = interfaceRegex.matcher(s);
            if (interfaceMatcher.find()) {
                className = interfaceMatcher.group(1);
                entityName = interfaceMatcher.group(2);
                exampleName = interfaceMatcher.group(3);
                IdType = interfaceMatcher.group(4);
            }

            List<String> importList = new ArrayList<>();
            Matcher importMatcher = importRegex.matcher(s);
            while (importMatcher.find()) {
                importList.add(importMatcher.group(1));
            }

            {// service

                File outFile = new File(toPath + entityName + "Service.java");
                List<String> imp = new ArrayList<>();
                imp.add("xyz.lihang.common.service.BaseService");
                for (String ss : importList) {
                    if (ss.substring(ss.lastIndexOf(".") + 1).equals(entityName)) {
                        imp.add(ss);
                        break;
                    }
                }

                createTemp(outFile, new JSONObject()
                                .fluentPut("author", AUTHOR)
                                .fluentPut("importList", imp)
                                .fluentPut("entityName", entityName)
                                .fluentPut("idType", IdType)
                                .fluentPut("packageName", servicePackageName),
                        "BaseServiceTemplate.ftlh"
                )
                ;
            }

            {// serviceImpl
                String impl = "impl";

                {
                    File file = new File(toPath + impl);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }

                File outFile = new File(toPath + impl + File.separator + entityName + "ServiceImpl.java");
                List<String> imp = new ArrayList<>();
                imp.add("xyz.lihang.common.service.BaseMapperService");
                imp.add("org.springframework.stereotype.Service");
                imp.add(packageName + "." + className);
                imp.add(servicePackageName + "." + entityName + "Service");
                imp.addAll(importList);

                createTemp(outFile, new JSONObject()
                                .fluentPut("author", AUTHOR)
                                .fluentPut("importList", imp)
                                .fluentPut("entityName", entityName)
                                .fluentPut("exampleName", exampleName)
                                .fluentPut("idType", IdType)
                                .fluentPut("packageName", servicePackageName + "." + impl),
                        "BaseServiceImplTemplate.ftlh"
                )
                ;

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static void createTemp(File outFile, Object o, String template) throws TemplateException {
        if (outFile.exists()) {
            return;
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile); OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);) {
            Template temp = cfg.getTemplate(template);
            temp.process(o, outputStreamWriter);
            System.out.println(outFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        parseService(
                new File("E:\\mjoy\\btoken\\mapper\\src\\main\\java\\"),
                "io.mjoy.siamtokens.dao",
                new File("E:\\mjoy\\btoken\\mapper\\src\\main\\java\\io\\mjoy\\siamtokens\\dao\\UserMapper.java")
        );
    }


}
