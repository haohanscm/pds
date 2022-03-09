package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.order.TradeMatchDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeMatch;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 交易匹配单Service
 * @author haohan
 * @version 2018-10-17
 */
@Service
@Transactional(readOnly = true)
public class TradeMatchService extends CrudService<TradeMatchDao, TradeMatch> {

	public TradeMatch get(String id) {
		return super.get(id);
	}
	
	public List<TradeMatch> findList(TradeMatch tradeMatch) {
		return super.findList(tradeMatch);
	}

	public List<TradeMatch> findByAskOrder(String askOrderId){
		TradeMatch tradeMatch = new TradeMatch();
		tradeMatch.setAskOrderId(askOrderId);
		return dao.findList(tradeMatch);
	}
	
	public Page<TradeMatch> findPage(Page<TradeMatch> page, TradeMatch tradeMatch) {
		return super.findPage(page, tradeMatch);
	}
	
	@Transactional(readOnly = false)
	public void save(TradeMatch tradeMatch) {
		super.save(tradeMatch);
	}
	
	@Transactional(readOnly = false)
	public void delete(TradeMatch tradeMatch) {
		super.delete(tradeMatch);
	}

    public List<TradeMatch> findJoinList(TradeMatch tradeMatch) {
        return dao.findJoinList(tradeMatch);
    }
}