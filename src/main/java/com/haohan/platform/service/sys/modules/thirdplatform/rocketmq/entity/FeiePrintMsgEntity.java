package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity;

import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common.IRocketMqConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintParams;

import java.util.List;

/**
 * @author shenyu
 * @create 2019/3/25
 */
public class FeiePrintMsgEntity extends MQEntity{
    private String merchantId;
    private List<PrintParams> printParams;

    public FeiePrintMsgEntity(List<PrintParams> printParams) {
        this.printParams = printParams;
    }

    public FeiePrintMsgEntity(List<PrintParams> printParams, IRocketMqConstant.PrintMsgTag tag, String merchantId) {
        this.printParams = printParams;
        this.mqTags = tag.getTagName();
        this.merchantId = merchantId;
    }

    public List<PrintParams> getPrintParams() {
        return printParams;
    }

    public void setPrintParams(List<PrintParams> printParams) {
        this.printParams = printParams;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
