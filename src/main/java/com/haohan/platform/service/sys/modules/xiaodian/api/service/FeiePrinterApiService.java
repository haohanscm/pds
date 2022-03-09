package com.haohan.platform.service.sys.modules.xiaodian.api.service;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.FeiePrinterParams;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintOrder;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.FeiePrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2018/6/22.
 */
@Service
public class FeiePrinterApiService {
    @Autowired
    private FeiePrinterService feiePrinterService;

    // 获取打印机列表  shopId 不能为空或空串
    public ArrayList<Map<String, String>> fetchPrinterList(String shopId){
        ArrayList<Map<String, String>> list = new ArrayList<>();
        FeiePrinter printer = new FeiePrinter();
        printer.setShopId(shopId);
        List<FeiePrinter> printerList = feiePrinterService.findList(printer);
        for(FeiePrinter p : printerList){
            list.add(transToMap(p));
        }
        return list;
    }

    // 修改打印机启用状态
    public Map<String, String> editPrinter(String printerId, String useable){
        FeiePrinter printer = feiePrinterService.get(printerId);
        printer.setUseable(useable);
        feiePrinterService.save(printer);
        return transToMap(printer);
    }

    // 修改打印机启用状态
    public Map<String, String> editPrinter(FeiePrinterParams params) {
        FeiePrinter printer = feiePrinterService.get(params.getPrinterId());
        printer.setUseable(params.getUseable());
        if (StringUtils.isNotEmpty(params.getName())) {
            printer.setPrinterName(params.getName());
        }
        if (StringUtils.isNotEmpty(params.getTimes())) {
            printer.setTimes(params.getTimes());
        }
        feiePrinterService.save(printer);
        return transToMap(printer);
    }

    // 打印机对象转map
    private Map<String, String> transToMap(FeiePrinter printer){
        Map<String, String> map = new HashMap<>();
        map.put("printerId", printer.getId());
        map.put("shopId", printer.getShopId());
        map.put("printerSn", printer.getPrinterSn());
        map.put("printerKey", printer.getPrinterKey());
        map.put("name", printer.getPrinterName());
        map.put("useable", StringUtils.defaultString(printer.getUseable(),"0"));
        map.put("times", printer.getTimes());
        return map;
    }

    // 店铺下打印机 打印订单
    public HashMap<String, Object> printOrderMsg(FeiePrinterParams params) {
        HashMap<String, Object> resultMap = new HashMap<>();
        PrintOrder order = feiePrinterService.fetchPrintOrderById(params.getOrderId());
        BaseResp resp = feiePrinterService.printByShopId(params.getShopId(), order);
        resultMap.put("code", resp.getCode());
        resultMap.put("msg", resp.getMsg());
        return resultMap;
    }

}
