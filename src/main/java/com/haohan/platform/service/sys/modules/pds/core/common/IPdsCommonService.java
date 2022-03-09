package com.haohan.platform.service.sys.modules.pds.core.common;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.PdsBuyerTopNGoodsApiReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;

/**
 * 公共service接口
 *
 * @author shenyu
 * @create 2018/10/19
 */
public interface IPdsCommonService {


    // 查询商品列表
    BaseResp fetchGoodsList(Goods goods, int pageNo, int pageSize);

    BaseResp fetchGoodsList(PdsBuyerGoodsReq goodsReq);

    // 查询商品详情
    BaseResp fetchGoodsInfo(String goodsSn);

    // 查询分类列表
    BaseResp fetchCategoryList(GoodsCategory goodsCategory);

    // 获取开放平台用户
    UserOpenPlatform fetchOpenUserByUid(String uid, String appId, String roleId, IPdsConstant.CompanyType type);

    // 查询 打印机列表
    BaseResp fetchPrinterList(FeiePrinter feiePrinter);

    //  易联云打印机列表
    BaseResp findYiPrinterList(BaseResp baseResp, String shopId, String pmId);

    // 采购配送系统 平台运营商家列表查询(采购商/供应商关联pmId)
    BaseResp findPlatformMerchantList();

    // 获取采购商TopN商品列表
    BaseResp selectTopNGoodsList(PdsBuyerTopNGoodsApiReq pdsApiBuyerTopNGoodsReq);

    BaseResp fetchAreaList(Area area);
}
