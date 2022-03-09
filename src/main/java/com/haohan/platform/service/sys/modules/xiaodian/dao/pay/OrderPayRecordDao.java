package com.haohan.platform.service.sys.modules.xiaodian.dao.pay;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;

import java.math.BigDecimal;

/**
 * 订单支付DAO接口
 * @author haohan
 * @version 2017-12-10
 */
@MyBatisDao
public interface OrderPayRecordDao extends CrudDao<OrderPayRecord> {

    BigDecimal sumSaleAmount(OrderPayRecord orderPayRecord);
	
}