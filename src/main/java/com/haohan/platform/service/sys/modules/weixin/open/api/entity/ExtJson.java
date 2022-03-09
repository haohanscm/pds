package com.haohan.platform.service.sys.modules.weixin.open.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;

import java.util.HashMap;

/**
 * Created by zgw on 2017/12/26.
 */

public class ExtJson {

    @JsonProperty("extAppid")
    private String extAppid;

    @JsonProperty("ext")
    private HashMap<String,Object> ext;

    @JsonProperty("extEnable")
    private boolean extEnable;

    @JsonProperty("directCommit")
    private boolean directCommit;

    public String getExtAppid() {
        return extAppid;
    }

    public void setExtAppid(String extAppid) {
        this.extAppid = extAppid;
    }

    public HashMap<String, Object> getExt() {
        return ext;
    }

    public void setExt(HashMap<String, Object> ext) {
        this.ext = ext;
    }

    public boolean isExtEnable() {
        return extEnable;
    }

    public void setExtEnable(boolean extEnable) {
        this.extEnable = extEnable;
    }

    public boolean isDirectCommit() {
        return directCommit;
    }

    public void setDirectCommit(boolean directCommit) {
        this.directCommit = directCommit;
    }

    public String toJson(){
      return  JsonMapper.toJsonString(this);
    }
}
