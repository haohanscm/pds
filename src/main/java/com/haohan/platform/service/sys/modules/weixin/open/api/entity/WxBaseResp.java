package com.haohan.platform.service.sys.modules.weixin.open.api.entity;

import cn.binarywang.wx.miniapp.bean.code.WxMaCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgw on 2017/12/25.
 */

public class WxBaseResp implements Serializable{

    @JsonProperty("errcode")
    private int errCode;

    @JsonProperty("errmsg")
    private String errMsg;

    @JsonProperty("status")
    private String status;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("auditid")
    private String auditid;

    @JsonProperty("page_list")
    private String[] pagelists;

    @JsonProperty("category_list")
    private List<CategoryList> categoryLists;


    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAuditid() {
        return auditid;
    }

    public void setAuditid(String auditid) {
        this.auditid = auditid;
    }

    public String[] getPagelists() {
        return pagelists;
    }

    public void setPagelists(String[] pagelists) {
        this.pagelists = pagelists;
    }

    public List<CategoryList> getCategoryLists() {
        return categoryLists;
    }

    public void setCategoryLists(List<CategoryList> categoryLists) {
        this.categoryLists = categoryLists;
    }

    public static  WxBaseResp fromJson(String json){
        return (WxBaseResp) JsonMapper.fromJsonString(json,WxBaseResp.class);
    }

    public void copyCategoryList(List<WxMaCategory> wxMaCategoryList){
        if(!Collections3.isEmpty(wxMaCategoryList)){
            categoryLists = new ArrayList<>();
            CategoryList category;
            for(WxMaCategory wx:wxMaCategoryList){
                category = new CategoryList();
                category.setFirstClass(wx.getFirstClass());
                category.setSecondClass(wx.getSecondClass());
                category.setThirdClass(wx.getThirdClass());
                category.setFirstId(StringUtils.toInteger(wx.getFirstId()));
                category.setSecondId(StringUtils.toInteger(wx.getSecondId()));
                category.setThirdId(StringUtils.toInteger(wx.getThirdId()));
                categoryLists.add(category);
            }
        }
    }
}
