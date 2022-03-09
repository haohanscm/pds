package com.haohan.platform.service.sys.modules.pds.api.entity.req;

import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/12
 */
public class PdsBuyerGoodsUpdateListReq implements Serializable {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    @NotBlank(message = "buyerMerchantId不能为空")
    private String buyerMerchantId;
    @NotBlank(message = "buyerId不能为空")
    private String buyerId;
    @NotEmpty(message = "goodsReqList不能为空")
    private List<PdsBuyerGoodsReq> goodsReqList;

    public void copyToGoodsPrice(PdsPlatformGoodsPrice goodsPrice) {
        goodsPrice.setPmId(this.pmId);
        goodsPrice.setMerchantId(this.buyerMerchantId);
        goodsPrice.setBuyerId(this.buyerId);
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getBuyerMerchantId() {
        return buyerMerchantId;
    }

    public void setBuyerMerchantId(String buyerMerchantId) {
        this.buyerMerchantId = buyerMerchantId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public List<PdsBuyerGoodsReq> getGoodsReqList() {
        return goodsReqList;
    }

    public void setGoodsReqList(List<PdsBuyerGoodsReq> goodsReqList) {
        this.goodsReqList = goodsReqList;
    }
}
