package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminMerchantShopService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 小程序用户管理
 *
 * @author dy
 * @date 2019/2/13
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/merchantShop")
public class PdsAdminMerchantShopCtrl extends BaseController {

    @Autowired
    private IPdsAdminMerchantShopService pdsAdminMerchantShopService;

    /**
     * 获取所属商家列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "merchant/findList")
    @ResponseBody
    public BaseResp merchantList(@Validated PdsAdminMerchantListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Merchant merchant = new Merchant();
        merchant.setParentId(listReq.getPmId());
        merchant.setMerchantName(listReq.getMerchantName());
        merchant.setContact(listReq.getContact());
        String status = listReq.getStatus();
        if (StringUtils.isNotEmpty(status)) {
            merchant.setStatus(StringUtils.toInteger(status));
        }
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        merchant.setPage(new Page<>(pageNo, pageSize));
        baseResp = pdsAdminMerchantShopService.findMerchantList(merchant);
        return baseResp;
    }

    /**
     * 所属商家 编辑
     *
     * @param editReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "merchant/edit")
    @ResponseBody
    public BaseResp merchantEdit(@Validated PdsAdminMerchantEditReq editReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminMerchantShopService.merchantEdit(editReq);
        return baseResp;
    }

    /**
     * 所属商家 删除
     *
     * @return
     */
    @RequestMapping(value = "merchant/delete")
    @ResponseBody
    public BaseResp merchantDelete(@Validated PdsAdminMerchantDeleteReq deleteReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminMerchantShopService.merchantDelete(deleteReq);
        return baseResp;
    }

    /**
     * 获取所属店铺列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "shop/findList")
    @ResponseBody
    public BaseResp shopList(@Validated PdsAdminShopListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Shop shop = new Shop();
        shop.setPmId(listReq.getPmId());
        shop.setName(listReq.getName());
        shop.setManager(listReq.getManager());
        shop.setMerchantId(listReq.getMerchantId());
        shop.setStatus(listReq.getStatus());
        shop.setServiceType(listReq.getServiceType());
        shop.setMerchantName(listReq.getMerchantName());
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        shop.setPage(new Page<>(pageNo, pageSize));

        baseResp = pdsAdminMerchantShopService.findShopList(shop);
        return baseResp;
    }

    /**
     * 获取所属店铺列表
     *
     * @param editReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "shop/edit")
    @ResponseBody
    public BaseResp shopEdit(@Validated PdsAdminShopEditReq editReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        // 默认值
        String merchantId = editReq.getMerchantId();
        String authType = editReq.getAuthType();
        String updateJisu = editReq.getIsUpdateJisu();
        if (StringUtils.isEmpty(merchantId)) {
            editReq.setMerchantId(editReq.getPmId());
        }
        if (null == IShopConstant.ShopAuthType.getTypeByCode(authType)) {
            editReq.setAuthType(IShopConstant.ShopAuthType.no.getCode());
        }
        if (null == ICommonConstant.YesNoType.getTypeByCode(updateJisu)) {
            editReq.setIsUpdateJisu(ICommonConstant.YesNoType.no.getCode());
        }
        baseResp = pdsAdminMerchantShopService.shopEdit(editReq);
        return baseResp;
    }

    /**
     * 所属店铺 删除
     *
     * @return
     */
    @RequestMapping(value = "shop/delete")
    @ResponseBody
    public BaseResp shopDelete(@Validated PdsAdminShopDeleteReq deleteReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminMerchantShopService.shopDelete(deleteReq);
        return baseResp;
    }

}
