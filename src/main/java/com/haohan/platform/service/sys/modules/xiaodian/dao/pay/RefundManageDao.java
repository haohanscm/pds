package com.haohan.platform.service.sys.modules.xiaodian.dao.pay;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.RefundManage;

import java.math.BigDecimal;

/**
 * 退款管理DAO接口
 * @author haohan
 * @version 2017-12-20
 */
@MyBatisDao
public interface RefundManageDao extends CrudDao<RefundManage> {
    BigDecimal fetchRefundTotalAmount(String orderId,String refundStatus);
	
}