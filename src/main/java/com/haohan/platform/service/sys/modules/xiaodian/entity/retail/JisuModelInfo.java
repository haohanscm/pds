package com.haohan.platform.service.sys.modules.xiaodian.entity.retail;

import java.io.Serializable;
import java.util.List;

/**
 * 即速 商品规格信息
 * Created by dy on 2018/9/25.
 */
public class JisuModelInfo implements Serializable {

    private String name; // 商品规格类型名字
    private String id; // 商品规格类型id
    private List<String> subModelId; // 商品规格类型 下面分类id集合
    private List<String> subModelName; // 商品规格类型 下面分类名字集合

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getSubModelId() {
        return subModelId;
    }

    public void setSubModelId(List<String> subModelId) {
        this.subModelId = subModelId;
    }

    public List<String> getSubModelName() {
        return subModelName;
    }

    public void setSubModelName(List<String> subModelName) {
        this.subModelName = subModelName;
    }
}
