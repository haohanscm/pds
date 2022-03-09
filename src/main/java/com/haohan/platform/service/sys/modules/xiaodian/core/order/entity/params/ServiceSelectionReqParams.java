package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.params;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/10/1
 */
public class ServiceSelectionReqParams {
    private String serviceName;		// 服务名称
    private String serviceDetail;		// 服务内容
    private BigDecimal servicePrice;		// 服务价格
    private String serviceSchedule;		// 服务周期
    private Integer serviceNum;		// 服务次数

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceSchedule() {
        return serviceSchedule;
    }

    public void setServiceSchedule(String serviceSchedule) {
        this.serviceSchedule = serviceSchedule;
    }

    public Integer getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(Integer serviceNum) {
        this.serviceNum = serviceNum;
    }
}
