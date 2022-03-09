package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp.MerchantInfoResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;

import java.util.List;

/**
 * 商家管理DAO接口
 * @author haohan
 * @version 2017-08-05
 */
@MyBatisDao
public interface MerchantDao extends CrudDao<Merchant> {

    List<Merchant> findJoinList(Merchant merchant);

    MerchantInfoResp findMerchantInfo(Integer tenantId);
}