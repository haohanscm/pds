package com.haohan.platform.service.sys.modules.pds.api.supplier;


import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierOrderService;
import com.haohan.platform.service.sys.modules.pds.entity.req.ReqOfferOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 供应商API接口  订单
 *
 * @author dy
 * @create 2018/11/12
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/supplier/order")
public class PdsSupplierOrderCtrl extends BaseController {

    @Autowired
    private ISupplierOrderService supplierOrderService;

    /**
     * 查询待报价商品列表(采购单汇总)
     *
     * @param reqOfferOrders
     * @return
     */
    @RequestMapping(value = "waitOffer/fetchList")
    @ResponseBody
    public BaseResp queryWaitOfferList(ReqOfferOrders reqOfferOrders) {
        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isEmpty(reqOfferOrders.getSupplierId())) {
            baseResp.setMsg("缺少参数supplierId");
            return baseResp;
        }
        baseResp = supplierOrderService.queryWaitOfferList(reqOfferOrders.getSupplierId(), reqOfferOrders.getBuySeq(), reqOfferOrders.getDeliveryTime());
        return baseResp;
    }

//    /**
//     * 供应商 商品报价(报价单)
//     * @param reqOfferOrders
//     * @return
//     */
//    @RequestMapping(value = "supplierOffer")
//    @ResponseBody
//    public BaseResp supplierOffer(@RequestBody ReqOfferOrders reqOfferOrders){
//        BaseResp baseResp = BaseResp.newError();
//        //验证参数
//        if (StringUtils.isEmpty(reqOfferOrders.getSupplierId())) {
//            baseResp.setMsg("缺少参数supplierId");
//            return baseResp;
//        }
//        if(Collections3.isEmpty(reqOfferOrders.getOfferOrderList())){
//            baseResp.setMsg("缺少参数offerOrderList");
//            return baseResp;
//        }
//        baseResp = supplierOrderService.supplierOfferOrders(reqOfferOrders);
//        return  baseResp;
//    }

//    /**
//     * TODO 供应商报价单 列表查询
//     *
//     * @return
//     */
//    @RequestMapping(value = "offerOrder/fetchList")
//    @ResponseBody
//    public BaseResp queryOfferOrderList(ReqOfferOrders reqOfferOrders) {
//        BaseResp baseResp = BaseResp.newError();
//        //验证参数
//        if (StringUtils.isEmpty(reqOfferOrders.getSupplierId())) {
//            baseResp.setMsg("缺少参数supplierId");
//            return baseResp;
//        }
//        baseResp = supplierOrderService.queryOfferOrderList(reqOfferOrders);
//        return baseResp;
//    }

    // 供应商成交商品列表

    // 供应商 备货确认

    // 售后单列表

}
