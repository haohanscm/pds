package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import java.io.Writer;
import java.lang.reflect.Field;

public class CdDataDomDriver extends DomDriver {
    private XmlFriendlyNameCoder xmlFriendlyNameCoder;

    public CdDataDomDriver(String code, XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(code, xmlFriendlyNameCoder);
        this.xmlFriendlyNameCoder = xmlFriendlyNameCoder;
    }

    private static boolean needCDATA(Class<?> targetClass, String fieldAlias) {
        boolean cdata = false;
        if (null == targetClass) {
            return false;
        }
        cdata = existsCDATA(targetClass, fieldAlias);
        if (cdata)
            return cdata;
        Class<?> superClass = targetClass.getSuperclass();
        while (!superClass.equals(Object.class)) {
            cdata = existsCDATA(superClass, fieldAlias);
            if (cdata)
                return cdata;
            superClass = superClass.getClass().getSuperclass();
        }
        return false;
    }

    private static boolean existsCDATA(Class<?> clazz, String fieldAlias) {
        if (clazz == null) {
            return false;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(XStreamCDATA.class) != null) {
                XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
                if (null != xStreamAlias) {
                    if (fieldAlias.equals(xStreamAlias.value()))
                        return true;
                } else {
                    if (fieldAlias.equals(field.getName()))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public HierarchicalStreamWriter createWriter(Writer out) {
        return new PrettyPrintWriter(out, xmlFriendlyNameCoder) {
            boolean cdata = false;
            Class<?> targetClass = null;

            @Override
            public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
                super.startNode(name, clazz);
                // 业务处理，对于用XStreamCDATA标记的Field，需要加上CDATA标签
                if (!name.equals("xml")) {
                    cdata = needCDATA(targetClass, name);
                } else {
                    targetClass = clazz;
                }
            }

            @Override
            protected void writeText(QuickWriter writer, String text) {
                if (cdata) {
                    writer.write("<![CDATA[" + text + "]]>");
                } else {
                    writer.write(text);
                }
            }
        };
    }
}
