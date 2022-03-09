package com.haohan.platform.service.sys.modules.pss.dao.procure;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 商品采购明细DAO接口
 * @author haohan
 * @version 2018-09-07
 */
@MyBatisDao
public interface ProcurementDetailDao extends CrudDao<ProcurementDetail> {

    Integer countTotalGoodsNum(@Param(value = "procureNum") String procureNum);

    BigDecimal countSumAmount(@Param(value = "procureNum")String procureNum);

    Integer updateSelective(ProcurementDetail procurementDetail);
}