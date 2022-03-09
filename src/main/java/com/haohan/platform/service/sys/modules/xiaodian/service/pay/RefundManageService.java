package com.haohan.platform.service.sys.modules.xiaodian.service.pay;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.pay.RefundManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.RefundManage;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

;

/**
 * 退款管理Service
 * @author haohan
 * @version 2017-12-20
 */
@Service
@Transactional(readOnly = true)
public class RefundManageService extends CrudService<RefundManageDao, RefundManage> {

	@Autowired
	@Lazy(true)
	private OrderPayRecordService orderPayRecordService;
	@Autowired
	private RefundManageDao refundManageDao;

	public RefundManage get(String id) {
		return super.get(id);
	}

	public List<RefundManage> findList(RefundManage refundManage) {
		return super.findList(refundManage);
	}

	public Page<RefundManage> findPage(Page<RefundManage> page, RefundManage refundManage) {
		return super.findPage(page, refundManage);
	}

	@Transactional(readOnly = false)
	public void save(RefundManage refundManage) {
		super.save(refundManage);
	}

	@Transactional(readOnly = false)
	public void delete(RefundManage refundManage) {
		super.delete(refundManage);
	}

	public RefundManage fetchByOrderId(String orderId) {

		RefundManage refundManage = new RefundManage();
		refundManage.setOrderId(orderId);
		List<RefundManage> list =  findList(refundManage);

		return  (null == list || list.size() == 0 ) ? null : list.get(0);
	}

	/**
	 * 退款申请
	 * @param orderPayRecord  已有订单支付记录
	 * @param needChange  是否修改订单支付状态
	 * @return  操作失败时 返回null
	 */
	@Transactional(readOnly = false)
	public RefundManage refundApply(OrderPayRecord orderPayRecord, boolean needChange){
		RefundManage refundManage = new RefundManage();
		if(StringUtils.isEmpty(orderPayRecord.getId())){
			return null;
		}
		String payStatus = orderPayRecord.getPayStatus();
		// 订单已支付
		if(!StringUtils.equalsAny(payStatus, IBankServiceConstant.PayStatus.SUCCESS.getCode())){
			return null;
		}
		refundManage.setMerchantId(orderPayRecord.getMerchantId());
		refundManage.setMerchantName(orderPayRecord.getMerchantName());
		refundManage.setPartnerId(orderPayRecord.getPartnerId());
		refundManage.setOrderId(orderPayRecord.getOrderId());
		refundManage.setRequestId(CommonUtils.genId(orderPayRecord.getPayType()));
		refundManage.setOrderAmount(orderPayRecord.getOrderAmount());
		refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDAPPLY.getCode());
		refundManage.setBusType(XmBankEnums.TransType.refund.getCode());
		refundManage.setPayAmount(orderPayRecord.getOrderAmount());
		refundManage.setRefundAmount(orderPayRecord.getOrderAmount());

		refundManage.setOrgTransId(orderPayRecord.getTransId());
		refundManage.setOrgReqId(orderPayRecord.getRequestId());
		// 保存退款申请实体  修改订单支付状态并保存
		save(refundManage);
		if(needChange){
			orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.CHECK.getCode());
			orderPayRecordService.save(orderPayRecord);
		}
		return refundManage;
	}

	public BigDecimal fetchRefundTotalAmount(String orderId,String refundStatus){
		BigDecimal totalRefund = refundManageDao.fetchRefundTotalAmount(orderId,refundStatus);
		return totalRefund;
	}

}