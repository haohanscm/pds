package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shenyu
 * @create 2019/1/10
 */
public class MQEntity implements Serializable {
    private Map<String, Object> extObj = new LinkedHashMap<String, Object>();

    private String mqId;

    private String mqKey;

    protected String mqTags;

    /**
     * 添加附加字段
     * @param key
     * @param value
     */
    public void addExt(String key , Object value){
        extObj.put(key, value);
    }

    /**
     * 获取附加字段
     * @param key
     */
    public void getExt(String key ){
        extObj.get(key);
    }

    public String getMqId() {
        return mqId;
    }

    public void setMqId(String mqId) {
        this.mqId = mqId;
    }

    public String getMqKey() {
        return mqKey;
    }

    public void setMqKey(String mqKey) {
        this.mqKey = mqKey;
    }

    public String getMqTags() {
        return mqTags;
    }

    public void setMqTags(String mqTags) {
        this.mqTags = mqTags;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
