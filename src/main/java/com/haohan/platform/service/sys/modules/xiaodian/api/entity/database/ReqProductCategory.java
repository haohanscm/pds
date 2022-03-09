package com.haohan.platform.service.sys.modules.xiaodian.api.entity.database;


import java.io.Serializable;

/**
 * 公共商品库 商品分类 请求参数
 * Created by dy on 2018/10/23.
 */
public class ReqProductCategory implements Serializable {

    private String name;		// 分类名称
    private String parentId;   // 父级分类id
    private String forefatherId;   // 祖先分类id
    private String categoryId;  // 分类id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getForefatherId() {
        return forefatherId;
    }

    public void setForefatherId(String forefatherId) {
        this.forefatherId = forefatherId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
