package com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2019/1/18
 */
public class GoodsOrderSaleCurveApiResp implements Serializable {
    private BigDecimal saleAmount;
    private Integer orderNum;
    private Date orderDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
