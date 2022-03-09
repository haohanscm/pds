package com.haohan.platform.service.sys.modules.xiaodian.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.TSaleVolumeResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderCategoryStatisApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderSaleVolRankApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.dao.order.GoodsOrderDetailDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品订单明细Service
 * @author haohan
 * @version 2018-09-01
 */
@Service
@Transactional(readOnly = true)
public class GoodsOrderDetailService extends CrudService<GoodsOrderDetailDao, GoodsOrderDetail> {

	public GoodsOrderDetail get(String id) {
		return super.get(id);
	}
	
	public List<GoodsOrderDetail> findList(GoodsOrderDetail goodsOrderDetail) {
		return super.findList(goodsOrderDetail);
	}
	
	public Page<GoodsOrderDetail> findPage(Page<GoodsOrderDetail> page, GoodsOrderDetail goodsOrderDetail) {
		return super.findPage(page, goodsOrderDetail);
	}

	public List<GoodsOrderDetail> findByOrderId(String orderId){
		GoodsOrderDetail orderDetail = new GoodsOrderDetail();
		orderDetail.setOrderId(orderId);
		return super.findList(orderDetail);
	}

	public BigDecimal countMemberBoughtNum(String uid, String goodsId){
		BigDecimal result = dao.countMemberBoughtNum(uid,goodsId);
		return null == result ? BigDecimal.ZERO : result;
	}

	@Transactional(readOnly = false)
	public void save(GoodsOrderDetail goodsOrderDetail) {
		super.save(goodsOrderDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsOrderDetail goodsOrderDetail) {
		super.delete(goodsOrderDetail);
	}


	public List<GoodsOrderCategoryStatisApiResp> categorySaleStatis(GoodsOrder goodsOrder) {
		return dao.categorySaleStatis(goodsOrder);
	}

	public List<GoodsOrderSaleVolRankApiResp> saleVolumeRank(GoodsOrder goodsOrder,String orderBy,Integer limit) {
		return dao.saleVolumeRank(goodsOrder,orderBy,limit);
	}

	public List<TSaleVolumeResp> sumSaleVolume(GoodsOrderDetail goodsOrderDetail){
		return dao.sumSaleVolume(goodsOrderDetail);
	}

}