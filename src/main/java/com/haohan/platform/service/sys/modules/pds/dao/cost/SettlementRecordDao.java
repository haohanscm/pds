package com.haohan.platform.service.sys.modules.pds.dao.cost;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;

import java.util.List;

/**
 * 结算记录管理DAO接口
 *
 * @author dy
 * @version 2018-11-06
 */
@MyBatisDao
public interface SettlementRecordDao extends CrudDao<SettlementRecord> {

    List<SettlementRecord> findJoinList(SettlementRecord settlementRecord);
}