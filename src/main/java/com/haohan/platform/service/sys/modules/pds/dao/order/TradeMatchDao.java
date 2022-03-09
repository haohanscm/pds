package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeMatch;

import java.util.List;

/**
 * 交易匹配单DAO接口
 *
 * @author haohan
 * @version 2018-10-17
 */
@MyBatisDao
public interface TradeMatchDao extends CrudDao<TradeMatch> {

    List<TradeMatch> findJoinList(TradeMatch tradeMatch);
}