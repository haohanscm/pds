package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant;

import com.haohan.platform.service.sys.modules.sys.security.SystemAuthorizingRealm;

import java.io.Serializable;

/**
 * Created by zgw on 2018/9/26.
 */
public class MerchantAuth implements Serializable {

    private String merchantId;

    private SystemAuthorizingRealm.Principal principal;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public SystemAuthorizingRealm.Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(SystemAuthorizingRealm.Principal principal) {
        this.principal = principal;
    }
}
