package com.github.lihang941;

import com.github.lihang941.generator.config.PathPackage;
import com.github.lihang941.generator.tool.FileTool;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public abstract class TemplateGenerator extends BlackListGenerator {

    public static Configuration cfg;

    public static String AUTHOR = "lihang1329@gmail.com";

    private boolean rewrite = false;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setClassForTemplateLoading(TemplateGenerator.class, "/template");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }


    public void createTemp(File outFile, Map<String, Object> o, String template) {
        if (outFile.exists() && rewrite == false) {
            return;
        }
        if (o == null) {
            o = new HashMap<>();
        }
        o.put("author", AUTHOR);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile); OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);) {
            Template temp = cfg.getTemplate(template);
            temp.process(o, outputStreamWriter);
            LOGGER.info("生成模板文件:" + outFile.getAbsolutePath());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static File mkdir(PathPackage pathPackage) {
        return FileTool.mkdirs(pathPackage.getPath(), pathPackage.getPackageName());
    }

    public TemplateGenerator setRewrite(boolean rewrite) {
        this.rewrite = rewrite;
        return this;
    }



    public static String toContent(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            String content = IOUtils.toString(fileInputStream);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
