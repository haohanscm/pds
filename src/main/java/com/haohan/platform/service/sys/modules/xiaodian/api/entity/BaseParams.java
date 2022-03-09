package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;

import java.io.Serializable;

/**
 * Created by zgw on 2018/6/11.
 */
public abstract class BaseParams implements Serializable {

    private PartnerApp partnerApp;

    public PartnerApp getPartnerApp() {
        return partnerApp;
    }

    public void setPartnerApp(PartnerApp partnerApp) {
        this.partnerApp = partnerApp;
    }


}
