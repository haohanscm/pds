package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.haohan.platform.service.sys.modules.weixin.open.api.entity.CategoryList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgw on 2018/1/2.
 */
public class MerchantApp implements Serializable{

    private MerchantAppManage merchantAppManage;

    private MerchantAppExtInfo merchantAppExtInfo;

    private String tags;

    private List<CategoryList> categoryLists;

    private String isUpdateExt = "0"; // 字典 yes_no  yes:1

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<CategoryList> getCategoryLists() {
        return categoryLists;
    }

    public void setCategoryLists(List<CategoryList> categoryLists) {
        this.categoryLists = categoryLists;
    }

    public MerchantAppManage getMerchantAppManage() {
        return merchantAppManage;
    }

    public void setMerchantAppManage(MerchantAppManage merchantAppManage) {
        this.merchantAppManage = merchantAppManage;
    }

    public MerchantAppExtInfo getMerchantAppExtInfo() {
        return merchantAppExtInfo;
    }

    public void setMerchantAppExtInfo(MerchantAppExtInfo merchantAppExtInfo) {
        this.merchantAppExtInfo = merchantAppExtInfo;
    }

    public String getIsUpdateExt() {
        return isUpdateExt;
    }

    public void setIsUpdateExt(String isUpdateExt) {
        this.isUpdateExt = isUpdateExt;
    }
}
