package com.github.lihang941.generator;

import com.github.lihang941.Generator;
import com.github.lihang941.common.utils.XMLUtils;
import com.github.lihang941.generator.config.DaoConfig;
import com.github.lihang941.generator.config.PathPackage;
import com.github.lihang941.generator.config.Table;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.NullProgressCallback;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class DaoAndPojo implements Generator {

    private DaoConfig daoConfig;

    private String ConfigPath = "/GeneratorConfig.xml";

    public DaoAndPojo(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    @Override
    public void generator() throws Exception {
        generator(parseConfig(this.getClass().getResourceAsStream(ConfigPath)));
    }


    public void generator(InputStream inputStream) throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, null);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        myBatisGenerator.generate(new NullProgressCallback() {
            @Override
            public void done() {
                countDownLatch.countDown();
            }
        });
        for (String warning : warnings) {
            LOGGER.warning(warning);
        }
        countDownLatch.await();
    }


    // 将配置文件中的路径配置为绝对路径
    private InputStream parseConfig(InputStream configInputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(configInputStream);
        Element documentElement = document.getDocumentElement();
        {
            Element jdbcConnection = (Element) documentElement.getElementsByTagName("jdbcConnection").item(0);
            jdbcConnection.setAttribute("connectionURL", daoConfig.getJdbcConfig().getConnectionURL());
            jdbcConnection.setAttribute("driverClass", daoConfig.getJdbcConfig().getDriverClass());
            jdbcConnection.setAttribute("password", daoConfig.getJdbcConfig().getPassword());
            jdbcConnection.setAttribute("userId", daoConfig.getJdbcConfig().getUserId());
        }
        {
            Element plugin = (Element) documentElement.getElementsByTagName("plugin").item(0);
            {
                Element property = document.createElement("property");
                property.setAttribute("beginningDelimiter", daoConfig.getBeginningDelimiter());
                plugin.appendChild(property);
            }
            {
                Element property = document.createElement("property");
                property.setAttribute("endingDelimiter", daoConfig.getEndingDelimiter());
                plugin.appendChild(property);
            }
        }
        {
            for (Map<String, PathPackage> map : Arrays.asList(
                    Collections.singletonMap("javaClientGenerator", daoConfig.getJavaClient()),
                    Collections.singletonMap("sqlMapGenerator", daoConfig.getSqlMap()),
                    Collections.singletonMap("javaModelGenerator", daoConfig.getJavaModel())
            )) {
                for (Map.Entry<String, PathPackage> kv : map.entrySet()) {
                    Element javaClientGenerator = (Element) documentElement.getElementsByTagName(kv.getKey()).item(0);
                    javaClientGenerator.setAttribute("targetPackage", kv.getValue().getPackageName());
                    javaClientGenerator.setAttribute("targetProject", kv.getValue().getPath());
                }
            }
        }

        {

            for (Table table : daoConfig.getTables()) {
                Element tableElement = document.createElement("table");
                tableElement.setAttribute("tableName", table.getTableName());

                if (table.isGeneratedKey() == true) {
                    Element generatedKey = document.createElement("generatedKey");
                    generatedKey.setAttribute("identity", String.valueOf(table.isIdentity()));

                    if (table.getSqlStatement() != null) {
                        generatedKey.setAttribute("sqlStatement", table.getSqlStatement());
                    }
                    if (table.getColumn() != null) {
                        generatedKey.setAttribute("column", table.getColumn());
                    }
                    if (table.getType() != null) {
                        generatedKey.setAttribute("type", table.getType());
                    }
                    tableElement.appendChild(generatedKey);
                }
                documentElement.getElementsByTagName("context").item(0).appendChild(tableElement);
            }
        }

        DocumentType doctype = document.getImplementation().createDocumentType("doctype",
                "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN",
                "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd");
        HashMap<String, String> map = new HashMap<>();
        map.put(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
        map.put(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
        String xmlStr = XMLUtils.doc2String(document, map);
        LOGGER.info(xmlStr);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStr.getBytes());
        return byteArrayInputStream;
    }


}
