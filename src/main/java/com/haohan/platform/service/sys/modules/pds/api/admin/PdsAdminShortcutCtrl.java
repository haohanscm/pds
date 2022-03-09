package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDataResetApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDateSeqBaseApiReq;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminShortcutService;
import com.haohan.platform.service.sys.modules.pds.core.summary.IPdsSummaryService;
import com.haohan.platform.service.sys.modules.pds.exception.PdsSummaryOperationException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.PdsOnekeyOperationException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dy
 * @create 2018/12/5
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/shortcut")
public class PdsAdminShortcutCtrl extends BaseController {

    @Autowired
    private IPdsSummaryService pdsSummaryService;
    @Autowired
    private IPdsAdminShortcutService pdsAdminShortcutService;

    // 采购单汇总
    @RequestMapping(value = "collect")
    @ResponseBody
    public BaseResp collect(@Validated(DefaultGroup.class) PdsDateSeqBaseApiReq pdsApiOneKeyOpReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsSummaryService.summaryBuyOrders(pdsApiOneKeyOpReq.getPmId(), pdsApiOneKeyOpReq.getDeliveryTime(), pdsApiOneKeyOpReq.getBuySeq());
        return baseResp;
    }

    // 平台确认报价-采购商确认报价-交易匹配-运营揽货
    @RequestMapping(value = "confirm")
    @ResponseBody
    public BaseResp confirm(@Validated(DefaultGroup.class) PdsDateSeqBaseApiReq apiOneKeyOpReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        try {
            baseResp = pdsAdminShortcutService.confirm(apiOneKeyOpReq.getPmId(), apiOneKeyOpReq.getBuySeq(), apiOneKeyOpReq.getDeliveryTime());
        } catch (PdsOnekeyOperationException e) {
            baseResp.error();
            baseResp.setMsg(e.getMessage());
        } catch (PdsSummaryOperationException e2) {
            baseResp.error();
            baseResp.setMsg(e2.getMessage());
        } catch (StorageOperationException e3) {
            baseResp.error();
            baseResp.setMsg(e3.getMessage());
        }
        return baseResp;
    }

    // 装车- 送达
    @RequestMapping(value = "finish")
    @ResponseBody
    public BaseResp finish(@Validated(DefaultGroup.class) PdsDateSeqBaseApiReq apiOneKeyOpReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        try {
            baseResp = pdsAdminShortcutService.loadAndArrived(apiOneKeyOpReq.getPmId(), apiOneKeyOpReq.getBuySeq(), apiOneKeyOpReq.getDeliveryTime());
        } catch (PdsOnekeyOperationException e) {
            e.printStackTrace();
            baseResp.setMsg(e.getMessage());
        } finally {
            return baseResp;
        }
    }

    // 平台下 统一批次 全收货
    @RequestMapping(value = "goodsReceived")
    @ResponseBody
    public BaseResp goodsReceived(@Validated(DefaultGroup.class) PdsDateSeqBaseApiReq apiOneKeyOpReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminShortcutService.goodsReceived(apiOneKeyOpReq.getPmId(), apiOneKeyOpReq.getBuySeq(), apiOneKeyOpReq.getDeliveryTime());
        return baseResp;
    }

    // 重置数据 至汇总采购单前
    @RequestMapping("reset")
    @ResponseBody
    public BaseResp reset(@Validated(DefaultGroup.class) PdsDataResetApiReq dataResetReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminShortcutService.resetSummary(dataResetReq);
        return baseResp;
    }


}
