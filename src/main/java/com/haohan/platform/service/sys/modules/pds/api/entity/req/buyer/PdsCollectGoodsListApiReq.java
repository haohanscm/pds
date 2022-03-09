package com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsCollectGoodsListApiReq extends PdsBaseApiReq {
    @NotBlank(message = "用户ID不能为空")
    private String uid;
    private String goodsId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
