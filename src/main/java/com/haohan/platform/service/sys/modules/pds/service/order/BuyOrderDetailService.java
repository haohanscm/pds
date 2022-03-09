package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminSumOrderDetail;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiOrderAnalyzeReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisCurveReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisSaleTopReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.*;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.order.BuyOrderDetailDao;
import com.haohan.platform.service.sys.modules.pds.entity.dto.LastDealDTO;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.resp.BuyOrderDetailResp;
import com.haohan.platform.service.sys.modules.pds.entity.resp.BuyOrderSummaryResp;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购单详情Service
 * @author haohan
 * @version 2018-10-24
 */
@Service
@Transactional(readOnly = true)
public class BuyOrderDetailService extends CrudService<BuyOrderDetailDao, BuyOrderDetail> {

	@Override
    public BuyOrderDetail get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<BuyOrderDetail> findList(BuyOrderDetail buyOrderDetail) {
		return super.findList(buyOrderDetail);
	}

    public List<BuyOrderDetail> findListByBuyId(String buyId) {
        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setBuyId(buyId);
        return super.findList(buyOrderDetail);
    }

	@Override
    public Page<BuyOrderDetail> findPage(Page<BuyOrderDetail> page, BuyOrderDetail buyOrderDetail) {
		return super.findPage(page, buyOrderDetail);
	}

	public List<BuyOrderSummaryResp> findGroupByGoodsId(BuyOrderDetail buyOrderDetail){
		List<BuyOrderSummaryResp> list = dao.findGroupByGoodsId(buyOrderDetail);
		return CollectionUtils.isEmpty(list)?null:list;
	}

	public List<PdsAdminSumOrderDetail> findBySummaryOrder(String summaryOrderId){
		List<PdsAdminSumOrderDetail> list = dao.findBySummaryOrder(summaryOrderId,new Date());
		return CollectionUtils.isEmpty(list)?null:list;
	}

	//计算采购均价
	public BigDecimal countBuyAvgPrice(String summaryOrderId){
		BigDecimal avgPrice = dao.countBuyAvgPrice(summaryOrderId);
		return null == avgPrice?new BigDecimal(0):avgPrice;
	}

	//计算汇总单的采购成交数量
    public BigDecimal countDealNum(String summaryOrderId, String goodsId) {
        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setSmmaryBuyId(summaryOrderId);
        // 修改BuyOrderStatus后 为待发货状态
        buyOrderDetail.setStatus(IPdsConstant.BuyOrderStatus.delivery.getCode());
        buyOrderDetail.setGoodsId(goodsId);
        BigDecimal result = dao.countDealNum(buyOrderDetail);
        return null == result ? BigDecimal.ZERO : result;
    }

	public BigDecimal countTotalAmount(String buyId){
        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setBuyId(buyId);
		BigDecimal result = dao.countTotalAmount(buyOrderDetail);
		return null == result ? BigDecimal.ZERO :result;
	}

	@Transactional(readOnly = false)
    @Override
	public void save(BuyOrderDetail buyOrderDetail) {
        // 商品下单数量 不可修改
        if (!buyOrderDetail.getIsNewRecord()) {
            buyOrderDetail.setOrderGoodsNum(null);
        }
        if(StringUtils.isEmpty(buyOrderDetail.getBuyDetailSn())){
            String buyDetailSn = PdsCommonUtil.incrIdByClass(BuyOrderDetail.class, IPdsConstant.BUY_ORDER_DETAIL_SN_PRE);
            buyOrderDetail.setBuyDetailSn(buyDetailSn);
        }
		super.save(buyOrderDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BuyOrderDetail buyOrderDetail) {
		super.delete(buyOrderDetail);
	}

    /**
     * 按批次/送货时间/状态/pmId  修改对应采购单及采购明细的状态  批量
     * @param buyOrderDetail  根据pmId/buySeq/status/deliveryDate查询 修改状态为finalStatus
     */
    @Transactional(readOnly = false)
    public int updateStatusBatch(BuyOrderDetail buyOrderDetail) {
        if (StringUtils.isAnyEmpty(buyOrderDetail.getBuySeq(), buyOrderDetail.getStatus(), buyOrderDetail.getFinalStatus())
                || null == buyOrderDetail.getDeliveryDate()) {
            return 0;
        }
        return dao.updateStatusBatch(buyOrderDetail);
    }

    /**
     * 按采购编号/状态  修改对应采购单及采购明细的状态
     * @param buyOrderDetail  根据buyId查询 修改状态为finalStatus
     */
    @Transactional(readOnly = false)
    public int updateStatusByBuyId(BuyOrderDetail buyOrderDetail) {
        if (StringUtils.isAnyEmpty(buyOrderDetail.getBuyId(), buyOrderDetail.getStatus(), buyOrderDetail.getFinalStatus())) {
            return 0;
        }
        return dao.updateStatusByBuyId(buyOrderDetail);
    }


	// 采购单明细列表 带交易单的状态/分拣数量
	public List<BuyOrderDetailResp> findListWithTrade(BuyOrderDetail buyOrderDetail) {
		return dao.findListWithTrade(buyOrderDetail);
	}


    public List<BuyOrderDetail> findJoinList(BuyOrderDetail buyOrderDetail) {
        return dao.findJoinList(buyOrderDetail);
    }

	public List<PdsApiCategoryPercentResp> categoryPercent(PdsApiStatisCurveReq statisCurveReq) {
		return dao.categoryPercent(statisCurveReq);
	}

	public List<PdsApiTopNSaleResp> goodsTopN(PdsApiStatisSaleTopReq saleTopReq) {
		return dao.goodsTopN(saleTopReq);
	}

	public BigDecimal sumBuyOrderProfit(PdsApiOrderAnalyzeReq orderAnalyzeReq){
    	BigDecimal result = dao.sumBuyOrderProfit(orderAnalyzeReq);
    	return null == result ? BigDecimal.ZERO : result;
	}

	public List<PdsApiStatisCurveResp> orderAmountCurve(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
		return dao.orderAmountCurve(orderAnalyzeReq);
	}

	public List<PdsApiStatisCurveResp> costCurve(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
		return dao.costCurve(orderAnalyzeReq);
	}

	public List<PdsApiBuyerSaleTopResp> buyerSaleTop(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
		return dao.buyerSaleTop(orderAnalyzeReq);
	}

	public PdsApiOrderAnalyzeResp orderAnalyze(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
		return dao.orderAnalyze(orderAnalyzeReq);
	}

    /**
     * 按采购商的商家分组
     * @param buyOrderDetail
     * @return
     */
    public List<BuyOrderDetail> findListGroupByMerchant(BuyOrderDetail buyOrderDetail) {
        return dao.findListGroupByMerchant(buyOrderDetail);
    }


	public List<PdsPreSumOrderApiResp> selectPreSummaryOrders(Date reqDate) {
		return dao.selectPreSummaryOrders(reqDate);
	}

    /**
     * 查询上次采购价
     * 同一采购商家
     * 已成交 规格商品
     *
     * @param pmId         平台商家
     * @param goodsModelId 商品规格
     * @param buyerId      采购商
     * @return
     */
    public LastDealDTO fetchLastDeal(String pmId, String goodsModelId, String buyerId) {
        // 若无当前商家的上次商品采购  则查询所有商家
        BuyOrderDetail buyOrderDetail = dao.fetchLastDeal(pmId, goodsModelId, buyerId);
        if(null == buyOrderDetail){
            buyOrderDetail = dao.fetchLastDeal(pmId, goodsModelId, "");
        }
        return new LastDealDTO(buyOrderDetail);
    }
}