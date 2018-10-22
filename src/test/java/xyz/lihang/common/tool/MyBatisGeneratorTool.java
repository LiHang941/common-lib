package xyz.lihang.common.tool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xyz.lihang.common.utils.XMLUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 数据库命名采用 _ 分割才会有驼峰命名 否则全是小写
 *
 * @author : lihang1329@gmail.com
 * @since : 2018/3/1
 */
public class MyBatisGeneratorTool {

    private final static String BASE_PACKAGE = "io.mjoy.siamtokens";
    private final static String ABS_PATH = "E:/mjoy/btoken/";



    private final static String MAPPER_PATH = ABS_PATH + "mapper/";
    private final static String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";



    private final static String SERVICE_PATH = ABS_PATH + "mapper/src/main/java";
    private final static String SERVICE_PACKAGE = BASE_PACKAGE + ".service";


    // 配置文件内容
    private final static JSONObject JDBC_CONNECTION_CONFIG = new JSONObject()
            .fluentPut("driverClass", "org.postgresql.Driver")
            .fluentPut("connectionURL", "jdbc:postgresql://localhost:5432/siamtokens")
            .fluentPut("userId", "siamtokens")
            .fluentPut("password", "a123520")
            ;

    private final static JSONArray TABLE = new JSONArray()
            .fluentAdd(new JSONObject().fluentPut("tableName", "user").fluentPut("column", "id"))
            ;


    private static String ENTITY_FILE = null;
    private static String MAPPER_FILE = null;

    public static void main(String... args) throws Exception {
        InputStream inputStream = parseConfig(MyBatisGeneratorTool.class.getResourceAsStream("/GeneratorConfig.xml"));
        ConfigurationParser cp = new ConfigurationParser(null);
        Configuration config = cp.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, null);
        myBatisGenerator.generate(null);

        Arrays.asList(ENTITY_FILE)
                .stream()
                .flatMap(s -> Arrays.asList(new File(s).listFiles()).stream())
                .map(EntityUtils::handleSetTransVoidToClass)
                .forEach(System.out::println);


        ServiceTemplate.createService(SERVICE_PATH, SERVICE_PACKAGE,MAPPER_FILE);

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
        ENTITY_FILE = javaModelGenerator.getAttribute("targetProject") + "/" + javaModelGenerator.getAttribute("targetPackage").replace(".","/") ;
        MAPPER_FILE = javaClientGenerator.getAttribute("targetProject") + "/" + javaClientGenerator.getAttribute("targetPackage").replace(".","/") ;


        TABLE.stream().map(o -> (JSONObject) o).forEach(o -> {
            String tableName = o.getString("tableName");
            String column = o.getString("column");
            Element generatedKey = document.createElement("generatedKey");
            setAttr(generatedKey, new JSONObject().fluentPut("column", column).fluentPut("sqlStatement", "JDBC").fluentPut("identity", "true"));
            Element table = document.createElement("table");
            table.setAttribute("tableName", tableName);
            table.appendChild(generatedKey);
            documentElement.getElementsByTagName("context").item(0).appendChild(table);
        });


        String xmlStr = XMLUtils.doc2FormatString(document);
        // debug
        System.out.println(xmlStr);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStr.getBytes());
        return byteArrayInputStream;
    }

    private static void setTarget(Element element) {
        if (element.getTagName().equals("sqlMapGenerator") && element.getAttribute("targetProject").indexOf("resource") != -1) {
            element.setAttribute("targetPackage",  "mapper");
        } else {
            element.setAttribute("targetPackage", MAPPER_PACKAGE + "." + element.getAttribute("targetPackage"));
        }
        element.setAttribute("targetProject", MAPPER_PATH + element.getAttribute("targetProject"));
    }


    public static void setAttr(Element element, JSONObject object) {
        object.forEach((k, v) -> element.setAttribute(k, object.getString(k)));
    }

}
