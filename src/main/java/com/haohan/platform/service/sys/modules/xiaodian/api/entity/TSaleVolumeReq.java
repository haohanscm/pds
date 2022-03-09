/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TSaleVolumeReq
 * Author:   Lenovo
 * Date:     2018/6/15 18:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/6/15
 * @since 1.0.0
 */
public class TSaleVolumeReq extends BaseParams implements Serializable {

    private String partnerId;

    private String shopId;

    private String goodsId;

    private String period;

    private  int pageNo;
    private  int pageSize;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
