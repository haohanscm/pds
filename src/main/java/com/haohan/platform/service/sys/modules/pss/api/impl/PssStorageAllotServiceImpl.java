package com.haohan.platform.service.sys.modules.pss.api.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssGoodsAllotApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssGoodsAllotDetailApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssStorageAllotService;
import com.haohan.platform.service.sys.modules.pss.constant.IPssConstant;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllot;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllotDetail;
import com.haohan.platform.service.sys.modules.pss.service.storage.GoodsAllotDetailService;
import com.haohan.platform.service.sys.modules.pss.service.storage.GoodsAllotService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/11/28
 */
@Service
public class PssStorageAllotServiceImpl implements IPssStorageAllotService {
    @Resource
    private GoodsAllotService goodsAllotService;
    @Resource
    private GoodsAllotDetailService goodsAllotDetailService;
    @Resource
    private IPssGoodsStorageService mercGoodsStorageService;

    @Override
    public BaseResp findAllotPage(PssGoodsAllotApiReq allotApiReq,Page page) throws Exception{
        BaseList<GoodsAllot> baseList = new BaseList<>();

        GoodsAllot goodsAllot = new GoodsAllot();
        BeanUtils.copyProperties(allotApiReq, goodsAllot);
        goodsAllotService.findPage(page,goodsAllot);

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

    @Override
    public BaseResp findAllotDetailPage(PssGoodsAllotDetailApiReq allotDetailApiReq,Page page) throws Exception{
        BaseList<GoodsAllotDetail> baseList = new BaseList<>();
        GoodsAllotDetail goodsAllotDetail = new GoodsAllotDetail();
        BeanUtils.copyProperties(allotDetailApiReq, goodsAllotDetail);

        goodsAllotDetailService.findPage(page,goodsAllotDetail);

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

    @Override
    public BaseResp delDetail(String detailId) {
        BaseResp baseResp = BaseResp.newError();

        GoodsAllotDetail goodsAllotDetail = goodsAllotDetailService.get(detailId);
        if (null == goodsAllotDetail){
            baseResp.setMsg("记录已删除");
            return baseResp;
        }
        goodsAllotDetailService.delete(goodsAllotDetail);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp saveAllot(String merchantId, GoodsAllot goodsAllot) {
        BaseResp baseResp = BaseResp.newError();

        if (IPssConstant.AllotOrderStatus.received.getCode().equals(goodsAllot.getOrderStatus())){
            baseResp.setMsg("操作失败,重复调拨");
            return baseResp;
        }

        goodsAllot.setMerchantId(merchantId);
        goodsAllot.setBizTime(new Date());
        if (StringUtils.isEmpty(goodsAllot.getAuditStatus())){
            goodsAllot.setAuditStatus(IPssConstant.AllotAuditStatus.auditing.getCode());
        }
        if (StringUtils.isEmpty(goodsAllot.getOrderStatus())){
            goodsAllot.setOrderStatus(IPssConstant.AllotOrderStatus.wait_audit.getCode());
        }
        goodsAllotService.save(goodsAllot);
        baseResp.setExt(goodsAllot.getId());
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp saveAllotDetail(String merchantId, List<GoodsAllotDetail> goodsAllotDetailList,String allotId) {
        BaseResp baseResp = BaseResp.newError();

        GoodsAllot goodsAllot = goodsAllotService.get(allotId);
        for (GoodsAllotDetail goodsAllotDetail : goodsAllotDetailList){
            goodsAllotDetail.setAllotId(allotId);
            goodsAllotDetail.setMerchantId(goodsAllot.getMerchantId());
            //保存调拨记录
            goodsAllotDetailService.save(goodsAllotDetail);
        }
        baseResp.success();
        return baseResp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResp confirm(String allotId) throws Exception{
        BaseResp baseResp = BaseResp.newError();

        GoodsAllot goodsAllot = goodsAllotService.get(allotId);
        if (IPssConstant.AllotOrderStatus.received.getCode().equals(goodsAllot.getOrderStatus())){
            baseResp.setMsg("操作失败,重复调拨");
            return baseResp;
        }

        List<GoodsAllotDetail> detailList = goodsAllotDetailService.fetchByAllotId(allotId);
        for (GoodsAllotDetail detail : detailList){
            mercGoodsStorageService.allotStockModify(goodsAllot, detail);
        }
        goodsAllot.setOrderStatus(IPssConstant.AllotOrderStatus.received.getCode());
        goodsAllotService.save(goodsAllot);
        baseResp.success();
        return baseResp;
    }
}
