package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;
import java.util.Map;

/**
 * 商品服务选项
 * @author shenyu
 * @create 2018/9/5
 */
public class GoodsServiceSelectionResp implements Serializable {
    private String serviceName;         //服务名称
    private Map<String,Object> serviceDetail;       //服务内容
    private String serviceSchedule;     //服务周期
//    private BigDecimal servicePrice;    //服务价格
    private Integer serviceNum;         //服务次数

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, Object> getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(Map<String, Object> serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public String getServiceSchedule() {
        return serviceSchedule;
    }

    public void setServiceSchedule(String serviceSchedule) {
        this.serviceSchedule = serviceSchedule;
    }

//    public BigDecimal getServicePrice() {
//        return servicePrice;
//    }
//
//    public void setServicePrice(BigDecimal servicePrice) {
//        this.servicePrice = servicePrice;
//    }

    public Integer getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(Integer serviceNum) {
        this.serviceNum = serviceNum;
    }
}
