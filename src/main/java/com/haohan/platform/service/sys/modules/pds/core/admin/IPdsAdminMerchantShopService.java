package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminMerchantDeleteReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminMerchantEditReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminShopDeleteReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminShopEditReq;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;

/**
 * 采购配送平台商家  商家管理  店铺管理
 *
 * @author dy
 * @date 2019/2/13
 */
public interface IPdsAdminMerchantShopService {

    /**
     * 获取所属商家列表
     *
     * @param merchant
     * @return
     */
    BaseResp findMerchantList(Merchant merchant);

    /**
     * 编辑商家 新增或修改
     *
     * @param editReq
     * @return
     */
    BaseResp merchantEdit(PdsAdminMerchantEditReq editReq);

    /**
     * 商家 删除
     *
     * @param deleteReq
     * @return
     */
    BaseResp merchantDelete(PdsAdminMerchantDeleteReq deleteReq);

    /**
     * 获取店铺列表
     *
     * @param shop
     * @return
     */
    BaseResp findShopList(Shop shop);

    /**
     * 编辑店铺 新增或修改
     *
     * @param editReq
     * @return
     */
    BaseResp shopEdit(PdsAdminShopEditReq editReq);

    /**
     * 店铺 删除
     *
     * @param deleteReq
     * @return
     */
    BaseResp shopDelete(PdsAdminShopDeleteReq deleteReq);
}
