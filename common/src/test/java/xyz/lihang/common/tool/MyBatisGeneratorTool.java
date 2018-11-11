package xyz.lihang.common.tool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import xyz.lihang.common.utils.XMLUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * 数据库命名采用 _ 分割才会有驼峰命名 否则全是小写
 *
 * @author : lihang1329@gmail.com
 * @since : 2018/3/1
 */
public class MyBatisGeneratorTool {

    private final static String ABS_PATH = "F:/mjoy/btoken/";
    private final static String BASE_PACKAGE = "io.mjoy.btoken";


    private final static String MAPPER_PATH = ABS_PATH + "mapper/";
    private final static String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";


    private final static String SERVICE_PATH = ABS_PATH + "service/src/main/java";
    private final static String SERVICE_PACKAGE = BASE_PACKAGE + ".service";

    private final static String CONVERT_PATH = ABS_PATH + "service/src/main/java";
    private final static String CONVERT_PACKAGE = BASE_PACKAGE + ".service.convert";

    private final static String DTO_PATH = ABS_PATH + "api/src/main/java";
    private final static String DTO_PACKAGE = BASE_PACKAGE + ".dto";

    private final static String RESOURCE_PATH = ABS_PATH + "facade/src/main/java";
    private final static String RESOURCE_PACKAGE = BASE_PACKAGE + ".facade.resource";


    // 配置文件内容
    private final static JSONObject JDBC_CONNECTION_CONFIG = new JSONObject()
            .fluentPut("driverClass", "org.postgresql.Driver")
            .fluentPut("connectionURL", "jdbc:postgresql://localhost:5432/btoken_project")
            .fluentPut("userId", "btoken_project")
            .fluentPut("password", "a123520");

    public final static JSONArray TABLE = new JSONArray()
//            .fluentAdd(new JSONObject().fluentPut("tableName", "activity").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
            .fluentAdd(new JSONObject().fluentPut("tableName", "account_mapping").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", true).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "bt_subscription_record").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "bt_unlock_record").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "btc_account").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "currency").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "ethernet_account").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "invitation_relationship").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "node").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "permission").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
//            .fluentAdd(new JSONObject().fluentPut("tableName", "transaction").fluentPut("column", "id").fluentPut("generatedKey", false).fluentPut("createService", false).fluentPut("createResource", false).fluentPut("createConvert", false))
            ;


    private static String ENTITY_PATH = null;
    private static String ENTITY_PACKAGE = null;
    private static String DAO_PATH = null;

    public static void main(String... args) throws Exception {
        InputStream inputStream = parseConfig(MyBatisGeneratorTool.class.getResourceAsStream("/GeneratorConfig.xml"));
        ConfigurationParser cp = new ConfigurationParser(null);
        Configuration config = cp.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, null);
        myBatisGenerator.generate(null);


        ServiceTemplate.createService(DAO_PATH).forEach(classInfo -> {
            ServiceTemplate.parseService(new File(SERVICE_PATH), SERVICE_PACKAGE, classInfo);
            ServiceTemplate.parseConvert(new File(CONVERT_PATH), CONVERT_PACKAGE, new File(DTO_PATH), DTO_PACKAGE, classInfo, new File(ENTITY_PATH), ENTITY_PACKAGE);
            ServiceTemplate.parseResource(new File(RESOURCE_PATH), RESOURCE_PACKAGE, classInfo, SERVICE_PACKAGE, CONVERT_PACKAGE, DTO_PACKAGE);
        });

        Arrays.asList(ENTITY_PATH, ServiceTemplate.mkdirs(new File(DTO_PATH), DTO_PACKAGE).getAbsolutePath())
                .stream()
                .flatMap(s -> Arrays.asList(new File(s).listFiles()).stream())
                .map(EntityUtils::handleSetTransVoidToClass)
                .forEach(System.out::println);
    }

    // 将配置文件中的路径配置为绝对路径
    private static InputStream parseConfig(InputStream configInputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(configInputStream);
        Element documentElement = document.getDocumentElement();
        setAttr((Element) documentElement.getElementsByTagName("jdbcConnection").item(0), JDBC_CONNECTION_CONFIG);
        Element javaClientGenerator = (Element) documentElement.getElementsByTagName("javaClientGenerator").item(0);

        setTarget((Element) documentElement.getElementsByTagName("sqlMapGenerator").item(0));
        setTarget(javaClientGenerator);
        Element javaModelGenerator = (Element) documentElement.getElementsByTagName("javaModelGenerator").item(0);
        setTarget(javaModelGenerator);
        ENTITY_PATH = javaModelGenerator.getAttribute("targetProject") + "/" + javaModelGenerator.getAttribute("targetPackage").replace(".", "/");
        ENTITY_PACKAGE = javaModelGenerator.getAttribute("targetPackage");

        DAO_PATH = javaClientGenerator.getAttribute("targetProject") + "/" + javaClientGenerator.getAttribute("targetPackage").replace(".", "/");


        TABLE.stream().map(o -> (JSONObject) o).forEach(o -> {
            String tableName = o.getString("tableName");
            String column = o.getString("column");
            Element table = document.createElement("table");
            table.setAttribute("tableName", tableName);

            if (o.getBoolean("generatedKey") == null || o.getBoolean("generatedKey") == true) {
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

        String xmlStr = XMLUtils.doc2String(document,map);
        // debug
        System.out.println(xmlStr);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStr.getBytes());
        return byteArrayInputStream;
    }

    private static void setTarget(Element element) {
        if (element.getTagName().equals("sqlMapGenerator") && element.getAttribute("targetProject").indexOf("resource") != -1) {
            element.setAttribute("targetPackage", "mapper");
        } else {
            element.setAttribute("targetPackage", MAPPER_PACKAGE + "." + element.getAttribute("targetPackage"));
        }
        element.setAttribute("targetProject", MAPPER_PATH + element.getAttribute("targetProject"));
    }


    public static void setAttr(Element element, JSONObject object) {
        object.forEach((k, v) -> element.setAttribute(k, object.getString(k)));
    }

}
