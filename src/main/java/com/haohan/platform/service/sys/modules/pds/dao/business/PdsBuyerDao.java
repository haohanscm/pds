package com.haohan.platform.service.sys.modules.pds.dao.business;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisOverViewReq;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;

import java.util.List;

/**
 * 采购商管理DAO接口
 *
 * @author haohan
 * @version 2018-10-19
 */
@MyBatisDao
public interface PdsBuyerDao extends CrudDao<PdsBuyer> {

//    List<PdsBuyer> findOrderBuyerList(PdsAdmSelfOrderReq pdsAdmSelfOrderReq);

    List<PdsBuyer> findJoinList(PdsBuyer pdsBuyer);

    List<PdsBuyer> findMerchantList(PdsBuyer pdsBuyer);

    List<Merchant> findPlatformMerchantList(Merchant merchant);

    PdsBuyer getWithName(String id);

    int countUserNum(PdsApiStatisOverViewReq apiStatisticalReq);
}