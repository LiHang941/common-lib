package com.github.lihang941.generator;

import com.alibaba.fastjson.JSONObject;
import com.github.lihang941.TemplateGenerator;
import com.github.lihang941.generator.config.DtoConfig;
import com.github.lihang941.generator.tool.FileTool;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class DtoAndConvert extends TemplateGenerator {

    private List<File> files;

    private DtoConfig dtoConfig;
    private File dtoPath;
    private File convertPath;

    public DtoAndConvert(DtoConfig dtoConfig, String modePath) {
        this(dtoConfig, Arrays.asList(new File(modePath)).stream().flatMap(file -> Arrays.asList(file.listFiles()).stream()).map(File::getAbsolutePath).collect(Collectors.toList()));
    }

    public DtoAndConvert(DtoConfig dtoConfig, List<String> models) {
        this.files = models.stream().map(File::new).filter(File::isFile).collect(Collectors.toList());
        this.dtoConfig = dtoConfig;
        this.dtoPath = mkdir(this.dtoConfig.getDto());
        this.convertPath = mkdir(this.dtoConfig.getConvert());
    }

    @Override
    public void generator() {
        files.stream().filter(file -> !this.isBlackList(file.getName())).forEach(this::generator);
    }


    public void generator(File file) {
        String content = toContent(file);
        String className = FileTool.getClassName(content);
        if (className == null) {
            LOGGER.warning(file.getAbsolutePath() + "未找到class name");
            return;
        }
        Matcher contentMatcher = Pattern.compile("\\{[^\\\\]+\\}").matcher(content);
        List<String> importList = FileTool.parseFileToImportList(content);
        importList.remove("javax.persistence.*");
        if (contentMatcher.find()) {
            Matcher matcher = Pattern.compile("^[^\\*\\n]+@\\w+(\\(.+\\))?$", Pattern.MULTILINE).matcher(contentMatcher.group());
            String newContent = matcher.replaceAll("");


            createTemp(Paths.get(dtoPath.getAbsolutePath(), className + "Dto.java").toFile(), new JSONObject()
                            .fluentPut("importList", importList)
                            .fluentPut("entityName", className)
                            .fluentPut("content", newContent)
                            .fluentPut("packageName", dtoConfig.getDto().getPackageName()),
                    "Dto.ftlh"
            );

            List<String> imp = new ArrayList<>();
            imp.add("java.util.function.Function");
            imp.add("com.github.lihang941.common.bean.BeanConvert");
            imp.add(dtoConfig.getDto().getPackageName() + "." + className + "Dto");
            imp.add(FileTool.getPackage(content) + "." + className);
            createTemp(Paths.get(convertPath.getAbsolutePath(), className + "Convert.java").toFile(), new JSONObject()
                            .fluentPut("importList", imp)
                            .fluentPut("entityName", className)
                            .fluentPut("packageName", dtoConfig.getConvert().getPackageName()),
                    "Convert.ftlh"
            );
        }
    }
}
