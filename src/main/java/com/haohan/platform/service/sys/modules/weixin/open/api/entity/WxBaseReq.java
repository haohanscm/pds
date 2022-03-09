package com.haohan.platform.service.sys.modules.weixin.open.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;

import java.util.List;

/**
 * Created by zgw on 2017/12/26.
 */
public class WxBaseReq {


    @JsonProperty("wechatid")
    private String wechatid;

    @JsonProperty("template_id")
    private String templateId;

    @JsonProperty("user_version")
    private String userVersion;

    @JsonProperty("user_desc")
    private String userDesc;

    @JsonProperty("ext_json")
    private String extJson;

    @JsonProperty("item_list")
    private List<ItemList> itemLists;


    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(String userVersion) {
        this.userVersion = userVersion;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson;
    }

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    public List<ItemList> getItemLists() {
        return itemLists;
    }

    public void setItemLists(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }

}
