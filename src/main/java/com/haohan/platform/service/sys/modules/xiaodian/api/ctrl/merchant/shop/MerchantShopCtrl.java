package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.shop;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.MerchantAuth;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.shop.ReqShop;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.shop.RespShopInfo;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.shop.MerchantShopService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 商户后台  店铺管理
 * Created by zgw on 2018/9/26.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/v2.0/shop")
public class MerchantShopCtrl extends BaseController {

    @Autowired
    private MerchantShopService merchantShopService;


    /**
     * 获取店铺详情
      */
    @RequestMapping(value = "fetchShopInfo")
    @ResponseBody
    public String fetchShopInfo(ReqShop reqShop, HttpServletRequest request){
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
        if (StringUtils.isEmpty(reqShop.getShopId())) {
            resp.setMsg("缺少参数:shopId");
            return resp.toJson();
        }
        Shop shop = new Shop();
        shop.setMerchantId(merchantId);
        shop.setId(reqShop.getShopId());
        RespShopInfo result = merchantShopService.fetchShopInfo(shop);
        if (null == result) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 修改店铺信息 部分属性 传值为空时不修改
     * @param reqShop
     * @param request
     * @return
     */
    @RequestMapping(value = "modifyShopInfo")
    @ResponseBody
    public String modifyShopInfo(ReqShop reqShop, HttpServletRequest request){
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
        if (StringUtils.isEmpty(reqShop.getShopId())) {
            resp.setMsg("缺少参数:shopId");
            return resp.toJson();
        }
        reqShop.setMerchantId(merchantId);

        resp = merchantShopService.modifyShopInfo(reqShop);
        return resp.toJson();
    }

     /**
     * 新增店铺
     * @param reqShop
     * @param request
     * @return
     */
    @RequestMapping(value = "addShop")
    @ResponseBody
    public String addShop(ReqShop reqShop, HttpServletRequest request){
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
        IShopConstant.ShopServiceType serviceType = IShopConstant.ShopServiceType.getTypeByCode(reqShop.getServiceType());
        if (null == serviceType || StringUtils.isEmpty(reqShop.getName())) {
            resp.setMsg("参数错误:serviceType/name");
            return resp.toJson();
        }
        reqShop.setMerchantId(merchantId);
        resp = merchantShopService.addShop(reqShop);
        return resp.toJson();
    }


    @RequestMapping(value = "config")
    @ResponseBody
    public BaseResp config(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();

        return baseResp;
    }








}
