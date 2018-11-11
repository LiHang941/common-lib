package com.github.lihang941.tool;

import com.alibaba.fastjson.JSONObject;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import xyz.lihang.common.bean.BeanConvertMap;
import xyz.lihang.common.utils.XMLUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 数据库命名采用 _ 分割才会有驼峰命名 否则全是小写
 *
 * @author : lihang1329@gmail.com
 * @since : 2018/3/1
 */
public class MyBatisGeneratorTool {

    private Config generateConfig;

    public MyBatisGeneratorTool(Config generateConfig) {
        this.generateConfig = generateConfig;
    }

    public void generate() throws Exception {
        InputStream inputStream = parseConfig(MyBatisGeneratorTool.class.getResourceAsStream("/GeneratorConfig.xml"));
        ConfigurationParser cp = new ConfigurationParser(null);
        Configuration config = cp.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, null);
        myBatisGenerator.generate(null);


        ServiceTemplate.createService(generateConfig.DAO_PATH, generateConfig).forEach(classInfo -> {
            ServiceTemplate.parseService(new File(generateConfig.SERVICE_PATH), generateConfig.SERVICE_PACKAGE, classInfo);
            ServiceTemplate.parseConvert(new File(generateConfig.CONVERT_PATH), generateConfig.CONVERT_PACKAGE, new File(generateConfig.DTO_PATH), generateConfig.DTO_PACKAGE, classInfo, new File(generateConfig.ENTITY_PATH), generateConfig.ENTITY_PACKAGE);
            ServiceTemplate.parseResource(new File(generateConfig.RESOURCE_PATH), generateConfig.RESOURCE_PACKAGE, classInfo, generateConfig.SERVICE_PACKAGE, generateConfig.CONVERT_PACKAGE, generateConfig.DTO_PACKAGE);
        });

        Arrays.asList(generateConfig.ENTITY_PATH, ServiceTemplate.mkdirs(new File(generateConfig.DTO_PATH), generateConfig.DTO_PACKAGE).getAbsolutePath())
                .stream()
                .flatMap(s -> Arrays.asList(new File(s).listFiles()).stream())
                .map(EntityUtils::handleSetTransVoidToClass)
                .forEach(System.out::println);
    }

    // 将配置文件中的路径配置为绝对路径
    private InputStream parseConfig(InputStream configInputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(configInputStream);
        Element documentElement = document.getDocumentElement();
        setAttr((Element) documentElement.getElementsByTagName("jdbcConnection").item(0), BeanConvertMap.beanToJSONObject(generateConfig.JDBC_CONNECTION_CONFIG));
        Element javaClientGenerator = (Element) documentElement.getElementsByTagName("javaClientGenerator").item(0);

        setTarget((Element) documentElement.getElementsByTagName("sqlMapGenerator").item(0));
        setTarget(javaClientGenerator);
        Element javaModelGenerator = (Element) documentElement.getElementsByTagName("javaModelGenerator").item(0);
        setTarget(javaModelGenerator);
        generateConfig.ENTITY_PATH = javaModelGenerator.getAttribute("targetProject") + "/" + javaModelGenerator.getAttribute("targetPackage").replace(".", "/");
        generateConfig.ENTITY_PACKAGE = javaModelGenerator.getAttribute("targetPackage");
        generateConfig.DAO_PATH = javaClientGenerator.getAttribute("targetProject") + "/" + javaClientGenerator.getAttribute("targetPackage").replace(".", "/");

        generateConfig.TABLES.stream().forEach(o -> {
            String tableName = o.getTableName();
            String column = o.getColumn();
            Element table = document.createElement("table");
            table.setAttribute("tableName", tableName);

            if (o.isGeneratedKey() == true) {
                Element generatedKey = document.createElement("generatedKey");
                setAttr(generatedKey, new JSONObject().fluentPut("column", column).fluentPut("sqlStatement", "JDBC").fluentPut("identity", "true"));
                table.appendChild(generatedKey);
            }

            documentElement.getElementsByTagName("context").item(0).appendChild(table);
        });
        DocumentType doctype = document.getImplementation().createDocumentType("doctype",
                "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN",
                "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd");
        HashMap<String, String> map = new HashMap<>();
        map.put(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
        map.put(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

        String xmlStr = XMLUtils.doc2String(document, map);
        // debug
        System.out.println(xmlStr);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStr.getBytes());
        return byteArrayInputStream;
    }

    private void setTarget(Element element) {
        if (element.getTagName().equals("sqlMapGenerator") && element.getAttribute("targetProject").indexOf("resource") != -1) {
            element.setAttribute("targetPackage", "mapper");
        } else {
            element.setAttribute("targetPackage", generateConfig.MAPPER_PACKAGE + "." + element.getAttribute("targetPackage"));
        }
        element.setAttribute("targetProject", generateConfig.MAPPER_PATH + element.getAttribute("targetProject"));
    }


    public void setAttr(Element element, JSONObject object) {
        object.forEach((k, v) -> element.setAttribute(k, object.getString(k)));
    }

}
