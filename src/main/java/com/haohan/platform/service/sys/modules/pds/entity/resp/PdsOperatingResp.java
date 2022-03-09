package com.haohan.platform.service.sys.modules.pds.entity.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/10/26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsOperatingResp implements Serializable {
    private String buySeq;  //采购批次
    private Date deliveryTime;    //送货时间
    private List<PdsSupListParams> supList;

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<PdsSupListParams> getSupList() {
        return supList;
    }

    public void setSupList(List<PdsSupListParams> supList) {
        this.supList = supList;
    }
}
