package com.haohan.platform.service.sys.modules.xiaodian.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.req.MercSaleOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.resp.MercSaleOrderResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderSaleCurveApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.dao.order.GoodsOrderDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品订单Service
 * @author haohan
 * @version 2017-12-12
 */
@Service
@Transactional(readOnly = true)
public class GoodsOrderService extends CrudService<GoodsOrderDao, GoodsOrder> {

	@Autowired
	private GoodsOrderDao goodsOrderDao;

	public GoodsOrder get(String id) {
		return super.get(id);
	}

	public List<GoodsOrder> findList(GoodsOrder goodsOrder) {
		return super.findList(goodsOrder);
	}

	public Page<GoodsOrder> findPage(Page<GoodsOrder> page, GoodsOrder goodsOrder) {
		return super.findPage(page, goodsOrder);
	}

	public Page<MercSaleOrderResp> findMercSaleOrderPage(Page<MercSaleOrderReq> page, MercSaleOrderReq mercSaleOrderReq) {
		mercSaleOrderReq.setPage(page);
		Page<MercSaleOrderResp> respPage = new Page<>();
		respPage.setList(dao.findMercSaleOrderList(mercSaleOrderReq));
		respPage.setCount(page.getCount());
		respPage.setPageSize(page.getPageSize());
		respPage.setPageNo(page.getPageNo());
		return respPage;
	}

	@Transactional(readOnly = false)
	public void save(GoodsOrder goodsOrder) {
		super.save(goodsOrder);
	}

	@Transactional(readOnly = false)
	public void delete(GoodsOrder goodsOrder) {
		super.delete(goodsOrder);
	}


	public GoodsOrder fetchByOrderId(String orderId){
		GoodsOrder order = new GoodsOrder();
		order.setOrderId(orderId);
		List<GoodsOrder> list = findList(order);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Transactional(readOnly = false)
	public void updatePayStatus(String orderId, IBankServiceConstant.PayStatus payStatus) {
		GoodsOrder goodsOrder = fetchByOrderId(orderId);
		if(null != goodsOrder) {
			goodsOrder.setPayStatus(payStatus.getCode());
			super.save(goodsOrder);
		}
	}

	@Transactional(readOnly = false)
	public void updateOrderStatus(String orderId, IOrderServiceConstant.OrderStatus orderStatus) {
		GoodsOrder goodsOrder = fetchByOrderId(orderId);
		if(null != goodsOrder) {
			goodsOrder.setOrderStatus(orderStatus.getCode());
			super.save(goodsOrder);
		}
	}

	public BigDecimal sumSaleAmount(GoodsOrder goodsOrder){
		BigDecimal result = goodsOrderDao.sumSaleAmount(goodsOrder);
		return null == result?BigDecimal.ZERO:result;
	}

	public Integer countOrderNum(GoodsOrder goodsOrder){
		Integer orderNum = dao.countOrderNum(goodsOrder);
		return null == orderNum ? 0 : orderNum;
	}

	/**
	 * 修改 订单状态  orderStatus
	 * @param goodsOrder
	 */
	@Transactional(readOnly = false)
	public void modifyOrderStatus(GoodsOrder goodsOrder) {
		dao.modifyOrderStatus(goodsOrder);
	}

	public BigDecimal sumRefundAmount(GoodsOrder goodsOrder) {
		BigDecimal result = dao.sumRefundAmount(goodsOrder);
		return null == result ? BigDecimal.ZERO : result;
	}

	public List<GoodsOrderSaleCurveApiResp> saleCurve(GoodsOrder goodsOrder) {
		return dao.saleCurve(goodsOrder);
	}


	public Integer countBuyerNum(GoodsOrder goodsOrder) {
		return dao.countBuyerNum(goodsOrder);
	}
}