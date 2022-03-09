package com.haohan.platform.service.sys.modules.pds.api.entity.req;

import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dy
 * @date 2019/03/12
 */
public class PdsBuyerPriceUpdateReq implements Serializable {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    @NotBlank(message = "id不能为空")
    private String id;
    @NotNull(message = "price不能为空")
    private BigDecimal price;
    private String buyerMerchantId;
    private String buyerId;

    public void copyToGoodsPrice(PdsPlatformGoodsPrice goodsPrice) {
        goodsPrice.setPmId(this.pmId);
        goodsPrice.setId(this.id);
        goodsPrice.setPrice(this.price);
        goodsPrice.setMerchantId(this.buyerMerchantId);
        goodsPrice.setBuyerId(this.buyerId);
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
}
