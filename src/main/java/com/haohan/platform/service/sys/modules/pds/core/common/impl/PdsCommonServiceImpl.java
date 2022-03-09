package com.haohan.platform.service.sys.modules.pds.core.common.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.PdsBuyerTopNGoodsApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsTopNGoodsResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerGoodsService;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.service.PdsMerchantGoodsService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.service.AreaService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.CloudPrintTerminal;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.CloudPrintTerminalService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.FeiePrinterService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdsCommonServiceImpl implements IPdsCommonService {

    @Resource
    private PdsMerchantGoodsService pdsMerchantGoodsService;
    @Resource
    private PdsBuyerService buyerService;
    @Resource
    private FeiePrinterService feiePrinterService;
    @Resource
    private UserOpenPlatformService userOpenPlatformService;
    @Resource
    private CloudPrintTerminalService cloudPrintTerminalService;
    @Autowired
    @Lazy(true)
    private IBuyerGoodsService buyerGoodsService;
    @Autowired
    @Lazy(true)
    private TradeOrderService tradeOrderService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService pdsSupplierService;
    @Autowired
    @Lazy(true)
    private AreaService areaService;

    /**
     * 目前用于供应商
     *
     * @param goods
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public BaseResp fetchGoodsList(Goods goods, int pageNo, int pageSize) {
        BaseResp baseResp = BaseResp.newError();
        ApiRespPage respPage = pdsMerchantGoodsService.fetchGoodsList(goods, pageNo, pageSize);
        if (Collections3.isEmpty(respPage.getList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            baseResp.setExt(respPage);
        }
        return baseResp;
    }

    /**
     * 目前用于采购商
     *
     * @param goodsReq
     * @return
     */
    @Override
    public BaseResp fetchGoodsList(PdsBuyerGoodsReq goodsReq) {
        BaseResp baseResp = BaseResp.newError();
        ApiRespPage respPage = buyerGoodsService.fetchGoodsList(goodsReq);
        if (Collections3.isEmpty(respPage.getList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            baseResp.setExt(respPage);
        }
        return baseResp;
    }

    @Override
    public BaseResp fetchGoodsInfo(String goodsSn) {
        BaseResp baseResp = BaseResp.newError();
        GoodsInfoExt goodsInfoExt = pdsMerchantGoodsService.getGoodsInfoExt(goodsSn);
        if (null == goodsInfoExt) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            baseResp.setExt(goodsInfoExt);
        }
        return baseResp;
    }

    @Override
    public BaseResp fetchCategoryList(GoodsCategory goodsCategory) {
        BaseResp baseResp = BaseResp.newError();
        ArrayList<GoodsCategory> list = pdsMerchantGoodsService.fetchCategoryList(goodsCategory);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            baseResp.setExt(list);
        }
        return baseResp;
    }

    /**
     * 查找用户 用于推送消息
     *
     * @param mpUid   推送用户uid
     * @param maAppId 公众号appId
     * @param roleId  buyerId/supplierId
     * @param type    buyer/supplier
     * @return
     */
    @Override
    public UserOpenPlatform fetchOpenUserByUid(String mpUid, String maAppId, String roleId, IPdsConstant.CompanyType type) {
        UserOpenPlatform userOpenPlatform = null;
        // 有下单用户时推送
        if (StringUtils.isEmpty(mpUid)) {
            return userOpenPlatform;
        }
        boolean flag = false;
        // 查看是否开启推送
        if (IPdsConstant.CompanyType.buyer.equals(type)) {
            PdsBuyer buyer = buyerService.get(roleId);
            if (null != buyer && StringUtils.equals(buyer.getNeedPush(), ICommonConstant.YesNoType.yes.getCode())) {
                flag = true;
            }
        } else if (IPdsConstant.CompanyType.supplier.equals(type)) {
            PdsSupplier supplier = pdsSupplierService.get(roleId);
            if (null != supplier && StringUtils.equals(supplier.getNeedPush(), ICommonConstant.YesNoType.yes.getCode())) {
                flag = true;
            }
        }
        if (flag) {
            userOpenPlatform = userOpenPlatformService.findByWechatPublic(mpUid, maAppId);
        }
        return userOpenPlatform;
    }

    @Override
    public BaseResp fetchPrinterList(FeiePrinter feiePrinter) {
        BaseResp baseResp = BaseResp.newError();
        List<FeiePrinter> list = feiePrinterService.findList(feiePrinter);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        ApiRespPage<FeiePrinter> page = new ApiRespPage<>();
        page.fetchFromPage(feiePrinter.getPage());
        // 部分属性不展示
        for (FeiePrinter f : list) {
            f.delProperties();
        }
        page.setList(list);
        baseResp.success();
        baseResp.setExt(page);
        return baseResp;
    }

    @Override
    public BaseResp findYiPrinterList(BaseResp baseResp, String shopId, String pmId) {
        CloudPrintTerminal cloudPrintTerminal = new CloudPrintTerminal();
        cloudPrintTerminal.setShopId(shopId);
        cloudPrintTerminal.setMerchantId(pmId);
        cloudPrintTerminal.setStatus(ICommonConstant.IsEnable.enable.getCode());
        List<CloudPrintTerminal> printerList = cloudPrintTerminalService.findList(cloudPrintTerminal);
        if (CollectionUtils.isEmpty(printerList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        baseResp.setExt(printerList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    /**
     * 采购配送系统 平台运营商家列表查询(采购商/供应商关联pmId)
     *
     * @return
     */
    @Override
    public BaseResp findPlatformMerchantList() {
        BaseResp baseResp = BaseResp.newError();
        List<Merchant> list = buyerService.findPlatformMerchantList();
        baseResp.success();
        baseResp.setExt(new ArrayList<>(list));
        return baseResp;
    }

    @Override
    public BaseResp selectTopNGoodsList(PdsBuyerTopNGoodsApiReq pdsApiBuyerTopNGoodsReq) {
        BaseResp baseResp = BaseResp.newError();

        pdsApiBuyerTopNGoodsReq.setQueryDate(new Date());
        int limitNum = pdsApiBuyerTopNGoodsReq.getLimitNum();
        if (limitNum == 0) {
            pdsApiBuyerTopNGoodsReq.setLimitNum(10);
        }
        PdsBuyer pdsBuyer = buyerService.get(pdsApiBuyerTopNGoodsReq.getBuyerId());
        if (null != pdsBuyer) {
            pdsApiBuyerTopNGoodsReq.setBuyerMerchantId(pdsBuyer.getMerchantId());
        }
        // 只查询上架商品
        pdsApiBuyerTopNGoodsReq.setQueryStatus(ICommonConstant.YesNoType.yes.getCode());
        List<PdsTopNGoodsResp> list = tradeOrderService.selectBuyerGoodsTopN(pdsApiBuyerTopNGoodsReq);
        if (CollectionUtils.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(new ArrayList<>(list));
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp fetchAreaList(Area area) {
        BaseResp baseResp = BaseResp.newError();
        List<Area> list = areaService.findBaseList(area);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        int size = list.size();
        ApiRespPage respPage = new ApiRespPage();
        respPage.setPageNo(1);
        respPage.setTotalPage(1);
        respPage.setPageSize(size);
        respPage.setList(list);
        respPage.setCount(size);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }
}
