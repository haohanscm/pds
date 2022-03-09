package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.consumer.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.consumer.AbstractRocketMqPushConsumer;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.FeiePrintMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.MQEntity;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintParams;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.FeiePrinterService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.PrintTemplateType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author shenyu
 * @create 2019/3/25
 */
public class PrintMsgConsumer extends AbstractRocketMqPushConsumer {
    @Autowired
    private FeiePrinterService feiePrinterService;

    @Override
    protected void execute(MQEntity mqEntity) {
        FeiePrintMsgEntity printMsgEntity = (FeiePrintMsgEntity) mqEntity;
        switch (PrintMsgTag.valueOfTagName(printMsgEntity.getMqTags())){
            case CREATE_ORDER:
                doPrint(printMsgEntity.getPrintParams(),printMsgEntity.getMerchantId(),PrintTemplateType.NEW_ORDER);
                break;
            case CANCEL_ORDER:
                doPrint(printMsgEntity.getPrintParams(),printMsgEntity.getMerchantId(),PrintTemplateType.CANCEL_ORDER);
                break;
            case UPDATE_ORDER:
                doPrint(printMsgEntity.getPrintParams(),printMsgEntity.getMerchantId(),PrintTemplateType.UPDATE_ORDER);
            default:break;
        }
    }

    private BaseResp doPrint(List<PrintParams> printParams,String merchantId,PrintTemplateType printTemplateType) {
        BaseResp baseResp = BaseResp.newError();
        List<FeiePrinter> feiePrinterList = feiePrinterService.findByMerchantIdAndTplType(merchantId,printTemplateType);
        for (FeiePrinter printer : feiePrinterList){
            baseResp = feiePrinterService.printBuyOrder(printer,printParams.toArray(new PrintParams[]{}));
        }
        return baseResp;
    }
}
