package com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer;

import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 批量修改平台商家店铺 商品的零售定价 请求参数
 *
 * @author dy
 * @date 2019/03/06
 */
public class PdsUpdateShopPriceReq implements Serializable {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmId长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "buyerMerchantId不能为空")
    @Length(min = 0, max = 64, message = "buyerMerchantId长度必须介于 0 和 64 之间")
    private String buyerMerchantId;
    @NotBlank(message = "shopId不能为空")
    @Length(min = 0, max = 64, message = "shopId长度必须介于 0 和 64 之间")
    private String shopId;
    @NotNull(message = "请选择开始日期")
    private Date startDate;
    @NotNull(message = "请选择结束日期")
    private Date endDate;

    public void copyToPrice(PdsPlatformGoodsPrice price) {
        price.setPmId(pmId);
        price.setMerchantId(buyerMerchantId);
        price.setShopId(shopId);
        price.setStartDate(startDate);
        price.setEndDate(endDate);
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
