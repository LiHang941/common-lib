package com.github.lihang941.generator;

import com.github.lihang941.TemplateGenerator;
import com.github.lihang941.BlackListGenerator;
import com.github.lihang941.generator.tool.FileTool;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 给 set 方法生成 return this;
 *
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class BeanBuilder extends BlackListGenerator {

    private List<File> fileList;

    public static String method_reg = "public\\s+void\\s+(set\\w+)\\s?(\\(\\w+\\s+\\w+\\))\\s?\\{([^}]+;)\\s+\\}";

    public BeanBuilder(String... path) {
        fileList = Arrays.asList(path)
                .stream()
                .map(File::new)
                .filter(File::isDirectory)
                .flatMap(file -> Arrays.asList(file.listFiles()).stream())
                .filter(File::isFile).collect(Collectors.toList());
    }

    @Override
    public void generator() {
        fileList.stream().filter(file -> !this.isBlackList(file.getName())).forEach(file -> {
            String content = TemplateGenerator.toContent(file);
            String className = FileTool.getClassName(content);
            if (className == null) {
                LOGGER.warning(file.getAbsolutePath() + "未找到class name");
                return;
            }
            Matcher matcher = Pattern.compile(method_reg).matcher(content);
            String newContent = matcher.replaceAll(
                    "public " + className + " $1$2{\t\t$3\n\t\treturn this;\n\t}"
            );
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                IOUtils.write(newContent, fileOutputStream);
                LOGGER.info(file.getAbsolutePath() + "修改成功");
            } catch (IOException e) {
                LOGGER.warning(file.getAbsolutePath() + "修改失败");
                e.printStackTrace();
            }
        });
    }

}
