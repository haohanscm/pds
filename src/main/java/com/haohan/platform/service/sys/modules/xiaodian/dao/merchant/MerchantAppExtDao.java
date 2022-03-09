package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppExt;

import java.util.List;

/**
 * 商家应用扩展信息管理DAO接口
 * @author haohan
 * @version 2018-02-05
 */
@MyBatisDao
public interface MerchantAppExtDao extends TreeDao<MerchantAppExt> {

    List<MerchantAppExt> fetchAppId(MerchantAppExt entity);
    // 包括del_flag不为0的记录
    int existExtInfoCount(MerchantAppExt entity);

    // 删除记录及其子节点记录
    int deleteWithChildren(MerchantAppExt entity);
	
}