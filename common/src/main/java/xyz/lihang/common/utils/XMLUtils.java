package xyz.lihang.common.utils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/10/20
 */
public abstract class XMLUtils {

    /**
     * Document 转换为 String
     *
     * @param doc XML的Document对象
     * @return String
     */
    public static String doc2String(Document doc, Map<String, String> map) {
        try {
            Source source = new DOMSource(doc);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, String> kv : map.entrySet()) {
                    transformer.setOutputProperty(kv.getKey(), kv.getValue());
                }
            } else {
                transformer.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_PUBLIC, doc.getDoctype().getPublicId());
            }
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String 转换为 Document 对象
     *
     * @param xml 字符串
     * @return Document 对象
     */
    public static Document string2Doc(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        StringReader reader = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            reader = new StringReader(xml);
            InputSource source = new InputSource(reader);//使用字符流创建新的输入源
            Document doc = builder.parse(source);
            return doc;
        } catch (Exception e) {
            return null;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Document 保存为 XML 文件
     *
     * @param doc  Document对象
     * @param path 文件路径
     */
    public static void doc2XML(Document doc, String path) {
        try {
            Source source = new DOMSource(doc);
            Result result = new StreamResult(new File(path));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            return;
        }
    }

}
