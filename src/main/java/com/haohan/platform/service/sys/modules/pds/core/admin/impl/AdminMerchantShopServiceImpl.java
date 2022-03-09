package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminMerchantDeleteReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminMerchantEditReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminShopDeleteReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminShopEditReq;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminMerchantShopService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dy
 * @date 2019/02/13
 */
@Service
public class AdminMerchantShopServiceImpl implements IPdsAdminMerchantShopService {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ShopService shopService;

    @Override
    public BaseResp findMerchantList(Merchant merchant) {
        BaseResp baseResp = BaseResp.newError();
        List<Merchant> list = merchantService.findList(merchant);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = merchant.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp merchantEdit(PdsAdminMerchantEditReq editReq) {
        BaseResp baseResp = BaseResp.newError();
        Merchant merchant = null;
        // 修改
        if (StringUtils.isNotEmpty(editReq.getId())) {
            merchant = merchantService.get(editReq.getId());
            if (null == merchant) {
                baseResp.setMsg("id有误");
                return baseResp;
            }
        }
        // 新增
        if (null == merchant) {
            editReq.setId(null);
            merchant = new Merchant();
        }
        editReq.transToMerchant(merchant);
        merchantService.save(merchant);
        baseResp.success();
        baseResp.setExt(merchant);
        return baseResp;
    }

    @Override
    public BaseResp merchantDelete(PdsAdminMerchantDeleteReq deleteReq) {
        BaseResp baseResp = BaseResp.newError();
        // 所属商家 删除
        Merchant merchant = merchantService.get(deleteReq.getId());
        // 只可删除 所属商家
        if (null == merchant || !StringUtils.equals(merchant.getParentId(), deleteReq.getPmId())) {
            baseResp.setMsg("商家id有误");
            return baseResp;
        }
        merchantService.delete(merchant);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp findShopList(Shop shop) {
        BaseResp baseResp = BaseResp.newError();
        // 查找平台商家和子商家下的店铺
        List<Shop> list = shopService.findListWithPmId(shop);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = shop.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp shopEdit(PdsAdminShopEditReq editReq) {
        BaseResp baseResp = BaseResp.newError();
        Shop shop = null;
        // 修改
        if (StringUtils.isNotEmpty(editReq.getId())) {
            shop = shopService.get(editReq.getId());
            if (null == shop) {
                baseResp.setMsg("店铺id有误");
                return baseResp;
            }
        }
        // 新增
        if (null == shop) {
            editReq.setId(null);
            shop = new Shop();
        }
        // 商家
        Merchant merchant = merchantService.get(editReq.getMerchantId());
        if (null == merchant) {
            baseResp.setMsg("商家id有误");
            return baseResp;
        } else {
            shop.setMerchantName(merchant.getMerchantName());
        }
        editReq.transToShop(shop);
        shopService.save(shop);
        baseResp.success();
        baseResp.setExt(shop);
        return baseResp;
    }

    @Override
    public BaseResp shopDelete(PdsAdminShopDeleteReq deleteReq) {
        BaseResp baseResp = BaseResp.newError();
        // 查找平台商家和子商家下的店铺
        Shop shop = new Shop(deleteReq.getId());
        shop.setPmId(deleteReq.getPmId());
        List<Shop> shopList = shopService.findListWithPmId(shop);
        // 只可删除 所属店铺
        if (Collections3.isEmpty(shopList)) {
            baseResp.setMsg("店铺id有误");
            return baseResp;
        }
        shopService.delete(shop);
        baseResp.success();
        return baseResp;
    }

}
