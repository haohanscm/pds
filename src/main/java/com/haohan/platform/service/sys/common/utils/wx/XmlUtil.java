package com.haohan.platform.service.sys.common.utils.wx;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 输出xml和解析xml的工具类
 */
public class XmlUtil {
    /**
     * java 转换成xml
     *
     * @param obj 对象实例
     * @return String xml字符串
     * @Title: toXml
     * @Description:
     */
    public static String toXml(Object obj) {
        // XStream xstream=new XStream();//存在单下划线变成2个下划线的问题
        XStream xstream = new XStream(new CdDataDomDriver("utf8", new XmlFriendlyNameCoder("_-", "_")));

        // 可以解决属性中存在下划线转换为xml时下划线多一个的问题，但不推荐使用，此方法已经废弃
        // XStream xstream = new XStream(new XppDriver(new
        // XmlFriendlyReplacer("-_", "_")));

        // XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
        // XStream xstream=new XStream(new DomDriver("utf-8"));
        // //指定编码解析器,直接用jaxp dom来解释
        // //如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass()); // 通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }

    public static String toXml(Object obj, String alias, Class cls) {
        XStream xstream = new XStream(new CdDataDomDriver("utf8", new XmlFriendlyNameCoder("_-", "_")));
        xstream.alias(alias, cls);
        xstream.registerConverter(new MapEntryConverter());
        xstream.processAnnotations(obj.getClass()); // 通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }

    /**
     * 将传入xml文本转换成Java对象
     *
     * @param xmlStr
     * @param cls    xml对应的class类
     * @return T xml对应的class类的实例对象 调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr, PersonBean.class);
     * @Title: toBean
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String xmlStr, Class<T> cls) {
        // 注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError:
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;
    }

    /**
     * 写到xml文件中去
     *
     * @param obj      对象
     * @param absPath  绝对路径
     * @param fileName 文件名
     * @return boolean
     * @Title: writeXMLFile
     * @Description:
     */
    public static boolean toXMLFile(Object obj, String absPath, String fileName) {
        String strXml = toXml(obj);
        String filePath = absPath + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        } // end if
        OutputStream ous = null;
        try {
            ous = new FileOutputStream(file);
            ous.write(strXml.getBytes());
            ous.flush();
        } catch (Exception e1) {
            return false;
        } finally {
            if (ous != null)
                try {
                    ous.close();
                } catch (IOException e) {
                }
        }
        return true;
    }

    /**
     * @param xml
     * @return
     * @Title: readStringXmlOut
     * @Description: 将Xml字符串转换成Map
     * @author laijie.zhang
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> readStringXmlOut(String xml) {
        Map<String, String> map = new HashMap<>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            List<Element> list = rootElt.elements();// 获取根节点下所有节点
            for (Element element : list) { // 遍历节点
                map.put(element.getName(), element.getText()); // 节点的name为map的key，text为map的value
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}