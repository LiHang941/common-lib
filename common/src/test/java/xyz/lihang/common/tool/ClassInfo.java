package xyz.lihang.common.tool;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/2
 */
public class ClassInfo {

    public String packageName;
    public String className;
    public String entityName;
    public String exampleName;
    public String IdType;
    public List<String> importList;
    public File file;
    public boolean createConvert = true;
    public boolean createResource = true;
    public boolean createService = true;


    public String getEntityPackage() {
        for (String ss : importList) {
            if (ss.substring(ss.lastIndexOf(".") + 1).equals(entityName)) {
                return (ss);
            }
        }
        return null;
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


    public static ClassInfo parseFile(File f) {
        Pattern packageRegex = Pattern.compile("package\\s+(.+);");
        Pattern interfaceRegex = Pattern.compile("interface\\s+(\\w+)\\s+extends\\s+BaseMapper<(\\w+),\\s+(\\w+),\\s+(\\w+)>");

        try (FileInputStream fileInputStream = new FileInputStream(f)) {
            ClassInfo classInfo = new ClassInfo();
            classInfo.file = f;
            String s = IOUtils.toString(fileInputStream, "UTF-8");
            Matcher packageMatcher = packageRegex.matcher(s);
            if (packageMatcher.find()) {
                classInfo.packageName = packageMatcher.group(1);
            }

            Matcher interfaceMatcher = interfaceRegex.matcher(s);
            if (interfaceMatcher.find()) {
                classInfo.className = interfaceMatcher.group(1);
                classInfo.entityName = interfaceMatcher.group(2);
                classInfo.exampleName = interfaceMatcher.group(3);
                classInfo.IdType = interfaceMatcher.group(4);
            }

            classInfo.importList = parseFileToImportList(s);

            return classInfo;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
