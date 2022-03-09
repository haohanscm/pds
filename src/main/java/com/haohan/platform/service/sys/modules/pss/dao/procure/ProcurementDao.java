package com.haohan.platform.service.sys.modules.pss.dao.procure;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;

import java.util.List;

/**
 * 商品采购DAO接口
 * @author haohan
 * @version 2018-09-07
 */
@MyBatisDao
public interface ProcurementDao extends CrudDao<Procurement> {

    List<Procurement> findJoinList(Procurement procurement);
}