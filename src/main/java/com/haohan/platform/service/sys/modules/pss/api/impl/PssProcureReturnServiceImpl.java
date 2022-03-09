package com.haohan.platform.service.sys.modules.pss.api.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcureReturnApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcureReturnDetailApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssProcureReturnService;
import com.haohan.platform.service.sys.modules.pss.constant.IPssConstant;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturnDetail;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseStock;
import com.haohan.platform.service.sys.modules.pss.service.procure.PurchaseReturnDetailService;
import com.haohan.platform.service.sys.modules.pss.service.procure.PurchaseReturnService;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseStockService;
import com.haohan.platform.service.sys.modules.xiaodian.exception.DataNotFoundException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.GoodsStockNotEnoughException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/11/27
 */
@Service
public class PssProcureReturnServiceImpl implements IPssProcureReturnService {
    @Resource
    private PurchaseReturnService purchaseReturnService;
    @Resource
    private PurchaseReturnDetailService purchaseReturnDetailService;
    @Resource
    private IPssGoodsStorageService mercGoodsStorageService;
    @Resource
    private WarehouseStockService warehouseStockService;

    @Override
    public BaseResp procureReturnPage(PssProcureReturnApiReq returnApiReq, Page page) throws Exception{
        BaseList<PurchaseReturn> baseList = new BaseList<>();

        PurchaseReturn purchaseReturn = new PurchaseReturn();
        BeanUtils.copyProperties(returnApiReq, purchaseReturn);

        purchaseReturnService.findPage(page,purchaseReturn);
        if (CollectionUtils.isEmpty(page.getList())){
            baseList.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseList;
        }

        baseList.setTotalPage(page.getTotalPage());
        baseList.setCurPage(page.getPageNo());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(page.getList());
        baseList.success();
        return baseList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResp saveReturn(PurchaseReturn purchaseReturn) throws DataNotFoundException {
        BaseResp baseResp = BaseResp.newError();
        String merchantId = purchaseReturn.getMerchantId();
        if (StringUtils.isNotEmpty(purchaseReturn.getId())){
            if (IPssConstant.ReturnStatus.returned.getCode().equals(purchaseReturn.getReturnStatus())){
                baseResp.setMsg("操作失败,重复退货");
                return baseResp;
            }
        }
        if (CollectionUtils.isEmpty(purchaseReturn.getDetailList())){
            baseResp.setMsg("退货商品不能为空");
            return baseResp;
        }
        purchaseReturn.setMerchantId(merchantId);
        purchaseReturn.setBizTime(new Date());
        purchaseReturn.setGoodsNum(purchaseReturn.getDetailList().size());
        purchaseReturn.setTotalAmount(purchaseReturn.fetchTotalAmount());
        purchaseReturn.setReturnStatus(IPssConstant.ReturnStatus.audit.getCode());
        purchaseReturnService.save(purchaseReturn);

        //保存明细
        for (PurchaseReturnDetail detail : purchaseReturn.getDetailList()){
            String goodsModelId = detail.getGoodsCode();
            String warehouseId = detail.getWarehouseId();
            WarehouseStock warehouseStock = warehouseStockService.fetchStockGoods(goodsModelId,warehouseId);
            if (null == warehouseStock){
                throw new DataNotFoundException("未找到"+detail.getGoodsName()+"的商品信息");
            }
            detail.setMerchantId(merchantId);
            detail.setReturnId(purchaseReturn.getId());
            detail.setAmount(detail.getPrice().multiply(detail.getNum()));
            purchaseReturnDetailService.save(detail);
        }

        baseResp.success();
        baseResp.setExt(purchaseReturn.getId());
        return baseResp;
    }

//    @Override
//    public BaseResp addProcureReturn(MerchantAuth merchantAuth, PurchaseReturn purchaseReturn) {
//        BaseResp baseResp = BaseResp.newError();
//        SystemAuthorizingRealm.Principal principal = merchantAuth.getPrincipal();
//        if (null != principal){
//            purchaseReturn.setOperator(UserUtils.get(principal.getId()));
//        }
//        purchaseReturn.setMerchantId(merchantAuth.getMerchantId());
//        purchaseReturn.setBizTime(new Date());
//
//        purchaseReturnService.save(purchaseReturn);
//        baseResp.success();
//        return baseResp;
//    }

//    @Override
//    public BaseResp saveDetails(MerchantAuth merchantAuth, List<PurchaseReturnDetail> detailList, String returnId) {
//        BaseResp baseResp = BaseResp.newError();
//
//        for (PurchaseReturnDetail detail : detailList){
//            detail.setReturnId(returnId);
//            purchaseReturnDetailService.save(detail);
//        }
//        baseResp.success();
//        return baseResp;
//    }

    @Override
    public BaseResp deleteProcureReturn(String id) {
        BaseResp baseResp = BaseResp.newError();

        PurchaseReturn purchaseReturn = purchaseReturnService.get(id);
        if (null == purchaseReturn){
            baseResp.setMsg("记录已删除");
            return baseResp;
        }
        List<PurchaseReturnDetail> detailList = purchaseReturnDetailService.findByReturnId(purchaseReturn.getId());
        //删除明细
        for (PurchaseReturnDetail purchaseReturnDetail : detailList){
            purchaseReturnDetailService.delete(purchaseReturnDetail);
        }
        purchaseReturnService.delete(purchaseReturn);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp delDetail(String detailId) {
        BaseResp baseResp = BaseResp.newError();

        PurchaseReturnDetail purchaseReturnDetail = purchaseReturnDetailService.get(detailId);
        if (null == purchaseReturnDetail){
            baseResp.setExt("记录已删除");
            return baseResp;
        }
        purchaseReturnDetailService.delete(purchaseReturnDetail);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp procureReturnDetails(PssProcureReturnDetailApiReq detailApiReq,Page page) {
        BaseList<PurchaseReturnDetail> baseList = new BaseList<>();

        PurchaseReturnDetail purchaseReturnDetail = new PurchaseReturnDetail();
        purchaseReturnDetail.setMerchantId(detailApiReq.getMerchantId());
        purchaseReturnDetail.setReturnId(detailApiReq.getReturnId());
        purchaseReturnDetailService.findPage(page,purchaseReturnDetail);
        if (CollectionUtils.isEmpty(page.getList())){
            baseList.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseList;
        }

        baseList.setTotalPage(page.getTotalPage());
        baseList.setCurPage(page.getPageNo());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(page.getList());
        baseList.success();
        return baseList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResp confirm(String returnId) throws GoodsStockNotEnoughException {
        BaseResp baseResp = BaseResp.newError();

        PurchaseReturn purchaseReturn = purchaseReturnService.get(returnId);
        if (null == purchaseReturn || IPssConstant.ReturnStatus.returned.getCode().equals(purchaseReturn.getReturnStatus())){
            baseResp.setMsg("操作失败,重复退货");
            return baseResp;
        }
        List<PurchaseReturnDetail> returnDetails = purchaseReturnDetailService.findByReturnId(returnId);
        for (PurchaseReturnDetail detail : returnDetails){
            mercGoodsStorageService.returnOutStock(purchaseReturn,detail);
        }

        purchaseReturn.setReturnStatus(IPssConstant.ReturnStatus.returned.getCode());
        purchaseReturnService.save(purchaseReturn);
        baseResp.success();
        return baseResp;
    }
}
