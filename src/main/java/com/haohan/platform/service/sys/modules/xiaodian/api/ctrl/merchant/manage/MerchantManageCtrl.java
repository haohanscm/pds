package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.manage;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.MerchantAuth;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.RespPage;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage.FeiePrinterReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage.ReqTerminal;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage.RespPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage.RespShop;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.manage.MerchantManageService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.entity.teiminal.TerminalManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/**
 * 商户 管理信息设置
 * Created by zgw on 2018/9/26.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/v2.0/manage")
public class MerchantManageCtrl extends BaseController {

    @Autowired
    private MerchantManageService merchantManageService;

    /**
     * 商家支付账户 查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchPayChannelInfo")
    @ResponseBody
    public String fetchPayChannelInfo(HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
//        if (StringUtils.isEmpty(reqMember.getShopId())) {
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
        ArrayList<RespPayInfo> result = merchantManageService.fetchPayChannelInfo(merchantId);
        if (Collections3.isEmpty(result)) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 商家店铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchShopList")
    @ResponseBody
    public String fetchShopList(HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
//        if (StringUtils.isEmpty(reqMember.getShopId())) {
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
        ArrayList<RespShop> result = merchantManageService.fetchShopList(merchantId);
        if (Collections3.isEmpty(result)) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 设备列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchTerminalList")
    @ResponseBody
    public String fetchTerminalList(ReqTerminal reqTerminal, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
//        if (StringUtils.isEmpty(reqMember.getShopId())) {
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
        // 分页查询
        int pageNo = StringUtils.toInteger(reqTerminal.getPageNo());
        int pageSize = StringUtils.toInteger(reqTerminal.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        TerminalManage terminalManage = new TerminalManage();
        terminalManage.setMerchantId(merchantId);
        // 查询条件
        terminalManage.setTerminalNo(reqTerminal.getTerminalNo());
        terminalManage.setTerminalType(reqTerminal.getTerminalType());
        terminalManage.setShopId(reqTerminal.getShopId());
        terminalManage.setStatus(reqTerminal.getStatus());

        Page<TerminalManage> page = new Page<>(pageNo, pageSize);
        terminalManage.setPage(page);
        RespPage<TerminalManage> result = merchantManageService.fetchTerminalList(terminalManage);
        if (Collections3.isEmpty(result.getList())) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 设备详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchTerminalInfo")
    @ResponseBody
    public String fetchTerminalInfo(ReqTerminal reqTerminal, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
        if (StringUtils.isEmpty(reqTerminal.getTerminalId())) {
            resp.setMsg("缺少参数:terminalId");
            return resp.toJson();
        }
        TerminalManage terminalManage = new TerminalManage();
        // 查询条件
        terminalManage.setId(reqTerminal.getTerminalId());
        terminalManage.setMerchantId(merchantId);
        TerminalManage result = merchantManageService.fetchTerminalInfo(terminalManage);

        if (null == result) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 设备信息修改/新增
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "terminalModify")
    @ResponseBody
    public String terminalModify(ReqTerminal reqTerminal, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
//        if (StringUtils.isEmpty(reqTerminal.getTerminalId())) {
//            resp.setMsg("缺少参数:terminalId");
//            return resp.toJson();
//        }
        if (!StringUtils.equals(merchantId, reqTerminal.getMerchantId())){
            resp.setMsg("错误参数:merchantId");
            return resp.toJson();
        }
        TerminalManage terminalManage = new TerminalManage();
        // 获取属性
        reqTerminal.transToTerminal(terminalManage);
        merchantManageService.terminalModify(terminalManage);
        resp.success();
        return resp.toJson();
    }

    /**
     * 飞鹅打印机 列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchPrinterList")
    @ResponseBody
    public String fetchPrinterList(FeiePrinterReq feiePrinterReq, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
//        if (StringUtils.isEmpty(FeiePrinterReq.getShopId())) {
//            resp.setMsg("缺少参数:shopId");
//            return resp.toJson();
//        }
        // 分页查询
        int pageNo = StringUtils.toInteger(feiePrinterReq.getPageNo());
        int pageSize = StringUtils.toInteger(feiePrinterReq.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        FeiePrinter feiePrinter = new FeiePrinter();
//        feiePrinter.setMerchantId(merchantId);
        // 查询条件
        feiePrinter.setShopId(feiePrinterReq.getShopId());
        feiePrinter.setPrinterSn(feiePrinterReq.getPrinterSn());
        feiePrinter.setPrinterName(feiePrinterReq.getPrinterName());
        feiePrinter.setUseable(feiePrinterReq.getUseable());

        Page<FeiePrinter> page = new Page<>(pageNo, pageSize);
        feiePrinter.setPage(page);
        RespPage<FeiePrinter> result = merchantManageService.fetchPrinterList(feiePrinter);
        if (Collections3.isEmpty(result.getList())) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 设备详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchPrinterInfo")
    @ResponseBody
    public String fetchPrinterInfo(FeiePrinterReq feiePrinterReq, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
        if (StringUtils.isEmpty(feiePrinterReq.getPrinterId())) {
            resp.setMsg("缺少参数:printerId");
            return resp.toJson();
        }
        FeiePrinter feiePrinter = new FeiePrinter();
        // 查询条件
        feiePrinter.setId(feiePrinterReq.getPrinterId());
//        feiePrinter.setMerchantId(merchantId);
        FeiePrinter result = merchantManageService.fetchPrinterInfo(feiePrinter);

        if (null == result) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 设备信息修改/新增
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "printerModify")
    @ResponseBody
    public String printerModify(FeiePrinterReq feiePrinterReq, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
        if (StringUtils.isAnyEmpty(feiePrinterReq.getPrinterSn(), feiePrinterReq.getShopId())) {
            resp.setMsg("缺少参数:printerSn/shopId");
            return resp.toJson();
        }
//        if (!StringUtils.equals(merchantId, reqTerminal.getMerchantId())){
//            resp.setMsg("错误参数:merchantId");
//            return resp.toJson();
//        }
        FeiePrinter feiePrinter = new FeiePrinter();
        feiePrinter.setMerchantId(merchantId);
        feiePrinterReq.transToPrinter(feiePrinter);
        merchantManageService.printerModify(feiePrinter);
        resp.success();
        return resp.toJson();
    }

    /**
     * 店铺设置  订单自动分配
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "shopSetting")
    @ResponseBody
    public String shopSetting(HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        try {
            MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
            Map<String,Object> reqParams = getRequestParameters(request);

            resp.success();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setExt("请求处理有误");
        }

        return resp.toJson();
    }







}
