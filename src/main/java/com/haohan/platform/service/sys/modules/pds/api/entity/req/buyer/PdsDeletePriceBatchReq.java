package com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer;

import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 批量删除采购商 采购价  请求参数
 *
 * @author dy
 * @date 2019/03/06
 */
public class PdsDeletePriceBatchReq implements Serializable {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmId长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "buyerMerchantId不能为空")
    @Length(min = 0, max = 64, message = "buyerMerchantId长度必须介于 0 和 64 之间")
    private String buyerMerchantId;
    @NotNull(message = "请选择查询日期")
    private Date queryDate;

    public void copyToPrice(PdsPlatformGoodsPrice price) {
        price.setPmId(pmId);
        price.setMerchantId(buyerMerchantId);
        price.setQueryDate(queryDate);
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

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }
}
