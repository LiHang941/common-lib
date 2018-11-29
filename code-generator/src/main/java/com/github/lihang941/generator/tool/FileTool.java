package com.github.lihang941.generator.tool;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : lihang941
 * @since : 2018/11/28
 */
public class FileTool {

    public static String getClassName(String content) {
        Matcher matcher = Pattern.compile("class\\s+([^\\s]+)\\s?\\{").matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public static String getPackage(String content) {
        Matcher matcher = Pattern.compile("package\\s+(.+);").matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public static String getIdType(String content) {
        Matcher matcher = Pattern.compile("@Id.+?private\\s+(\\w+)\\s+[^\\s]+\\s?;", Pattern.DOTALL).matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public static String getIdName(String content) {
        Matcher matcher = Pattern.compile("@Id.+?private\\s+\\w+\\s+([^\\s]+)\\s?;", Pattern.DOTALL).matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }


    public static List<String> parseFileToImportList(String s) {
        List<String> list = new ArrayList<>();
        Pattern importRegex = Pattern.compile("import (.+);");
        Matcher importMatcher = importRegex.matcher(s);
        while (importMatcher.find()) {
            list.add(importMatcher.group(1));
        }
        return list;
    }


    public static File mkdirs(String basePath, String packageName) {
        String toPath = Paths.get(basePath, packageName.replaceAll("\\.", "/")).toFile().getAbsolutePath();
        File file = mkdir(toPath);
        return file;
    }

    public static File mkdir(String toPath) {
        File file = new File(toPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


}
