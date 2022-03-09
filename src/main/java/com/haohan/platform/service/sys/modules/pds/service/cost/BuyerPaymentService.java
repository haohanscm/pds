package com.haohan.platform.service.sys.modules.pds.service.cost;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsRangeAmountResultResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.cost.BuyerPaymentDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购商货款统计Service
 * @author haohan
 * @version 2018-10-20
 */
@Service
@Transactional(readOnly = true)
public class BuyerPaymentService extends CrudService<BuyerPaymentDao, BuyerPayment> {

	public BuyerPayment get(String id) {
		return super.get(id);
	}
	
	public List<BuyerPayment> findList(BuyerPayment buyerPayment) {
		return super.findList(buyerPayment);
	}

    /**
     * 带buyerName/merchantId/merchantName
      */
    public List<BuyerPayment> findJoinList(BuyerPayment buyerPayment) {
        return dao.findJoinList(buyerPayment);
    }
	
	public Page<BuyerPayment> findPage(Page<BuyerPayment> page, BuyerPayment buyerPayment) {
		return super.findPage(page, buyerPayment);
	}

    public BuyerPayment fetchByBuyId(String buyId) {
        BuyerPayment buyerPayment = new BuyerPayment();
        buyerPayment.setBuyId(buyId);
        List<BuyerPayment> list = super.findList(buyerPayment);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }
	
	@Transactional(readOnly = false)
	public void save(BuyerPayment buyerPayment) {
        if(StringUtils.isEmpty(buyerPayment.getBuyerPaymentId())){
            String buyerPaymentId = PdsCommonUtil.incrIdByClass(BuyerPayment.class, IPdsConstant.BUYER_PAYMENT_PREFIX);
            buyerPayment.setBuyerPaymentId(buyerPaymentId);
        }
		super.save(buyerPayment);
	}
	
	@Transactional(readOnly = false)
	public void delete(BuyerPayment buyerPayment) {
		super.delete(buyerPayment);
	}

    /**
     * 货款统计 按时间查询 pmId/buyerId/merchantId/status
     * @param payment
     * @return
     */
    public BuyerPayment countPayment(BuyerPayment payment) {
        List<BuyerPayment> list = dao.countPayment(payment);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 修改货款状态 批量
     * @param payment  必需: stauts/finalStatus / pmId/BuyerPaymentId
     * @return
     */
    @Transactional(readOnly = false)
    public int updateStatusBatch(BuyerPayment payment){
        return dao.updateStatusBatch(payment);
    }

    /**
     * 按时间统计 金额
     * 按 采购商分组
     * @param payment 必传 beginBuyDate/endBuyDate/pmId
     * @return
     */
    public List<PdsRangeAmountResultResp> rangeAmount(BuyerPayment payment){
        if(null == payment.getBeginBuyDate() || null == payment.getEndBuyDate()){
            return new ArrayList<>();
        }
        return dao.rangeAmount(payment);
    }
}