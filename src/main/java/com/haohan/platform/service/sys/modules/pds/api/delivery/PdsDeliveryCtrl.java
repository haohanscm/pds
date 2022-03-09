package com.haohan.platform.service.sys.modules.pds.api.delivery;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsAdmBaseReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsShipOrderApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.delivery.PdsShipBuyerApiReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminBuyOrderService;
import com.haohan.platform.service.sys.modules.pds.core.delivery.IPdsDeliveryService;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/11/12
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/delivery")
public class PdsDeliveryCtrl extends BaseController {
    @Resource
    private IPdsDeliveryService pdsDeliveryService;
    @Resource
    private IPdsAdminBuyOrderService pdsAdminBuyOrderService;
    @Resource
    private MerchantEmployeeService merchantEmployeeService;

    //获取送货单列表
    @RequestMapping(value = "driver/shipBuyerList")
    @ResponseBody
    public BaseResp findDeliveryBuyerList(@Validated PdsShipBuyerApiReq apiShipBuyerReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        Page page = new Page();
        page.setPageNo(null == apiShipBuyerReq.getPageNo() ? 1 : apiShipBuyerReq.getPageNo());
        page.setPageSize(null == apiShipBuyerReq.getPageSize() ? 30 : apiShipBuyerReq.getPageSize());

        baseResp = pdsDeliveryService.findDeliveryBuyerList(apiShipBuyerReq, page);
        return baseResp;
    }

    //获取送货单明细
    @RequestMapping(value = "driver/shipOrderDetails")
    @ResponseBody
    public BaseResp findShipOrderList(@Validated PdsShipBuyerApiReq shipBuyerReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        MerchantEmployee merchantEmployee = merchantEmployeeService.fetchByUidAndRole(shipBuyerReq.getUid(), IPdsConstant.EmployeeRole.driver);
        if (null == merchantEmployee) {
            baseResp.setMsg("无此权限");
            return baseResp;
        }
        shipBuyerReq.setDriverId(merchantEmployee.getId());

        Integer pageNo = null == shipBuyerReq.getPageNo() ? 1 : shipBuyerReq.getPageNo();
        Integer pageSize = null == shipBuyerReq.getPageSize() ? 20 : shipBuyerReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(pageNo);
        reqPage.setPageSize(pageSize);

        baseResp = pdsAdminBuyOrderService.findShipOrderPage(reqPage, shipBuyerReq);
        return baseResp;
    }

    //装车
    @RequestMapping(value = "operation/truckLoad")
    @ResponseBody
    public BaseResp truckLoad(TradeOrder tradeOrder, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        try {
            baseResp = pdsDeliveryService.truckLoad(tradeOrder);
        } catch (Exception e) {
            baseResp.setMsg("系统错误");
        }
        return baseResp;
    }

    //发车(司机)
    @RequestMapping(value = "driver/depart")
    @ResponseBody
    public BaseResp depart(PdsShipOrderApiReq apiShipOrderReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        baseResp = pdsDeliveryService.depart(apiShipOrderReq);
        return baseResp;
    }

    //送货单送达(司机)
    @RequestMapping(value = "driver/shipOrderArrived")
    @ResponseBody
    public BaseResp shipOrderArrived(String shipId, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        if (StringUtils.isEmpty(shipId)) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        baseResp = pdsDeliveryService.shipOrderArrived(shipId);
        return baseResp;
    }

    //TODO 送货单送货失败

    //自提单送达
    @RequestMapping(value = "operation/selfOrderArrived")
    @ResponseBody
    public BaseResp selfOrderArrived(PdsAdmBaseReq pdsAdmBaseReq) {
        BaseResp baseResp = BaseResp.newError();
        Date deliveryDate = pdsAdmBaseReq.getDeliveryDate();
        String buySeq = pdsAdmBaseReq.getBuySeq();
        String pmId = pdsAdmBaseReq.getPmId();

        baseResp = pdsDeliveryService.selfOrderArrived(deliveryDate, buySeq, pmId);
        return baseResp;
    }


}
