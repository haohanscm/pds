package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 请求 采购商货款编辑
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminBuyerPaymentBatchCreateReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    @NotNull(message = "请选择送货日期")
    private Date deliveryDate;
    @NotBlank(message = "请选择批次")
    private String buySeq;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }
}
