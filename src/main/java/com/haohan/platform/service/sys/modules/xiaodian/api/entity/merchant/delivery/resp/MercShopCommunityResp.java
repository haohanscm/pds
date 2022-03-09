package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.resp;

/**
 * @author shenyu
 * @create 2018/9/28
 */
public class MercShopCommunityResp extends MercDeliveryBaseResp {
    private Boolean isSelected;
    private String pid;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
