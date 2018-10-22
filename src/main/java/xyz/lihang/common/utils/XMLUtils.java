package xyz.lihang.common.utils;

import java.io.*;
import java.net.URI;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.*;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/10/20
 */
public abstract class XMLUtils {


    /**
     * Document 转换为 String 并且进行了格式化缩进
     *
     * @param doc XML的Document对象
     * @return String
     */
    public static String doc2FormatString(Document doc) {
        StringWriter stringWriter = null;
        try {
            stringWriter = new StringWriter();
            if(doc != null){
                OutputFormat format = new OutputFormat(doc,"UTF-8",true);
                //format.setIndenting(true);//设置是否缩进，默认为true
                //format.setIndent(4);//设置缩进字符数
                //format.setPreserveSpace(false);//设置是否保持原来的格式,默认为 false
                //format.setLineWidth(500);//设置行宽度
                XMLSerializer serializer = new XMLSerializer(stringWriter,format);
                serializer.asDOMSerializer();
                serializer.serialize(doc);
                return stringWriter.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            if(stringWriter != null){
                try {
                    stringWriter.close();
                } catch (IOException e) {
                    return null;
                }
            }
        }
    }

    /**
     * Document 转换为 String
     *
     * @param doc XML的Document对象
     * @return String
     */
    public static String doc2String(Document doc){
        try {
            Source source = new DOMSource(doc);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (Exception e) {
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
            DocumentBuilder builder  = factory.newDocumentBuilder();
            reader = new StringReader(xml);
            InputSource source = new InputSource(reader);//使用字符流创建新的输入源
            Document doc = builder.parse(source);
            return doc;
        } catch (Exception e) {
            return null;
        } finally {
            if(reader != null){
                reader.close();
            }
        }
    }

    /**
     * Document 保存为 XML 文件
     *
     * @param doc Document对象
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
