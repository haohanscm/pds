package com.haohan.platform.service.sys.modules.pds.service.cost;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.cost.SettlementRecordDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;
import com.haohan.platform.service.sys.modules.pds.entity.order.ServiceOrder;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 结算记录管理Service
 * @author dy
 * @version 2018-11-06
 */
@Service
@Transactional(readOnly = true)
public class SettlementRecordService extends CrudService<SettlementRecordDao, SettlementRecord> {

	public SettlementRecord get(String id) {
		return super.get(id);
	}
	
	public List<SettlementRecord> findList(SettlementRecord settlementRecord) {
		return super.findList(settlementRecord);
	}
	
	public Page<SettlementRecord> findPage(Page<SettlementRecord> page, SettlementRecord settlementRecord) {
		return super.findPage(page, settlementRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(SettlementRecord settlementRecord) {
        if(StringUtils.isEmpty(settlementRecord.getSettlementId())){
            String settlementId = PdsCommonUtil.incrIdByClass(SettlementRecord.class, IPdsConstant.SETTLEMENT_PREFIX);
            settlementRecord.setSettlementId(settlementId);
        }
		super.save(settlementRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(SettlementRecord settlementRecord) {
		super.delete(settlementRecord);
	}

    public List<SettlementRecord> findJoinList(SettlementRecord settlementRecord) {
        return dao.findJoinList(settlementRecord);
    }
}