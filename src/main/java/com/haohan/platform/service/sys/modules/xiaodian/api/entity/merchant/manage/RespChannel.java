package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage;

import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.ChannelRateManage;

import java.io.Serializable;

/**
 * 商户后台 商家支付账户 渠道信息 返回参数
 * Created by dy on 2018/10/9.
 */
public class RespChannel implements Serializable {

    private String channel; //渠道名称
    private String rate;  //费率
    private String status;		// 状态

    public void copyFromChannel(ChannelRateManage channelRateManage){
        this.channel = channelRateManage.getChannel();
        this.rate = channelRateManage.getRate();
        this.status = channelRateManage.getStatus();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
