package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.beanvalidator.EditGroup;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDataResetApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDateSeqBaseApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsSortOutApiReq;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminSortOutService;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 分拣接口
 *
 * @author shenyu
 * @create 2018/11/1
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/sortout")
public class PdsAdminSortOutCtrl extends BaseController {

    @Resource
    private IPdsAdminSortOutService pdsAdminSortOutService;

    //分拣记录列表
    @RequestMapping(value = "findPage")
    @ResponseBody
    public BaseResp findList(@Validated(DefaultGroup.class) PdsBaseApiReq apiBaseReq, TradeOrder tradeOrder, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Integer pageNo = apiBaseReq.getPageNo();
        Integer pageSize = apiBaseReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 100 : pageSize);
        baseResp = pdsAdminSortOutService.findList(tradeOrder, reqPage);
        return baseResp;
    }

    //确定分拣
    @RequestMapping(value = "confirm")
    @ResponseBody
    public BaseResp confirm(@Validated(EditGroup.class) PdsSortOutApiReq sortOutReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        baseResp = pdsAdminSortOutService.confirm(sortOutReq.getTradeId(), sortOutReq.getSortOutNum());
        return baseResp;
    }

    //修改分拣数量
    @RequestMapping(value = "edit")
    @ResponseBody
    public BaseResp edit(@Validated(EditGroup.class) PdsSortOutApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        baseResp = pdsAdminSortOutService.editSortNum(apiReq.getTradeId(), apiReq.getSortOutNum());
        return baseResp;
    }

    //分拣进度(整体)
    @RequestMapping(value = "process/all")
    @ResponseBody
    public BaseResp allProcess(PdsDateSeqBaseApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        Date deliveryTime = apiReq.getDeliveryTime();
        String buySeq = apiReq.getBuySeq();
        String pmId = apiReq.getPmId();

        if (null == deliveryTime || StringUtils.isEmpty(pmId)) {
            baseResp.setMsg("请先选择配送日期/pmId");
            return baseResp;
        }

        baseResp = pdsAdminSortOutService.allProcess(deliveryTime, buySeq, pmId);
        return baseResp;
    }

    //分拣进度(按商品种类)
    @RequestMapping(value = "process/byGoods")
    @ResponseBody
    public BaseResp buyProcess(TradeOrder tradeOrder, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        Date deliveryDate = tradeOrder.getDeliveryTime();
        String buySeq = tradeOrder.getBuySeq();
        String pmId = tradeOrder.getPmId();

        if (null == deliveryDate || StringUtils.isEmpty(pmId)) {
            baseResp.setMsg("请先选择配送日期");
            return baseResp;
        }

        baseResp = pdsAdminSortOutService.buyOrderProcess(deliveryDate, buySeq, pmId);
        return baseResp;
    }

    @RequestMapping(value = "fastSortOut")
    @ResponseBody
    public BaseResp fastSortOut(@Validated(DefaultGroup.class) PdsDataResetApiReq dataResetReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Date date = dataResetReq.getDeliveryDate();
        String buySeq = dataResetReq.getBuySeq();
        String pmId = dataResetReq.getPmId();

        baseResp = pdsAdminSortOutService.fastSortOut(date, buySeq, pmId);
        return baseResp;
    }


}
