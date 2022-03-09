package com.haohan.platform.service.sys.modules.weixin.open.api.entity;

import com.haohan.platform.service.sys.common.mapper.JsonMapper;

/**
 * Created by zgw on 2017/12/26.
 */
public class ServiceDomainReq {


    private String action;

    private String[] requestdomain;

    private String[] wsrequestdomain;

    private String[] uploaddomain;

    private String[] downloaddomain;

    public ServiceDomainReq() {
    }

    public ServiceDomainReq(String action, String[] requestdomain, String[] wsrequestdomain, String[] uploaddomain, String[] downloaddomain) {
        this.action = action;
        this.requestdomain = requestdomain;
        this.wsrequestdomain = wsrequestdomain;
        this.uploaddomain = uploaddomain;
        this.downloaddomain = downloaddomain;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String[] getRequestdomain() {
        return requestdomain;
    }

    public void setRequestdomain(String[] requestdomain) {
        this.requestdomain = requestdomain;
    }

    public String[] getWsrequestdomain() {
        return wsrequestdomain;
    }

    public void setWsrequestdomain(String[] wsrequestdomain) {
        this.wsrequestdomain = wsrequestdomain;
    }

    public String[] getUploaddomain() {
        return uploaddomain;
    }

    public void setUploaddomain(String[] uploaddomain) {
        this.uploaddomain = uploaddomain;
    }

    public String[] getDownloaddomain() {
        return downloaddomain;
    }

    public void setDownloaddomain(String[] downloaddomain) {
        this.downloaddomain = downloaddomain;
    }

    public String toJson(){
        return JsonMapper.toJsonString(this);
    }


}
