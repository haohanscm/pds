package com.haohan.platform.service.sys.modules.xiaodian.entity.pay;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgw on 2018/3/28.
 */
public class MerchantPayConfig implements Serializable {

    private List<ChannelRateManage> channelRateManageList;

    private MerchantPayInfo merchantPayInfo;

    public List<ChannelRateManage> getChannelRateManageList() {
        return channelRateManageList;
    }

    public void setChannelRateManageList(List<ChannelRateManage> channelRateManageList) {
        this.channelRateManageList = channelRateManageList;
    }

    public MerchantPayInfo getMerchantPayInfo() {
        return merchantPayInfo;
    }

    public void setMerchantPayInfo(MerchantPayInfo merchantPayInfo) {
        this.merchantPayInfo = merchantPayInfo;
    }
}
