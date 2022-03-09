package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public class PdsApiCategoryPercentResp implements Serializable {
    private String categoryName;
    private Integer num;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

}
