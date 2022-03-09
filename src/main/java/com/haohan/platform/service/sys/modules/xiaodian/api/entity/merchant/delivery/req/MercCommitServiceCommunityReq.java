package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.req;

import java.io.Serializable;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/9/28
 */
public class MercCommitServiceCommunityReq implements Serializable {
    private String shopId;
    private List<MercCommitServiceItemsReq> deleteItems;
    private List<MercCommitServiceItemsReq> addItems;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<MercCommitServiceItemsReq> getDeleteItems() {
        return deleteItems;
    }

    public void setDeleteItems(List<MercCommitServiceItemsReq> deleteItems) {
        this.deleteItems = deleteItems;
    }

    public List<MercCommitServiceItemsReq> getAddItems() {
        return addItems;
    }

    public void setAddItems(List<MercCommitServiceItemsReq> addItems) {
        this.addItems = addItems;
    }
}
