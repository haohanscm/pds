package com.haohan.platform.service.sys.modules.xiaodian.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 开放平台用户管理DAO接口
 * @author haohan
 * @version 2017-08-05
 */
@MyBatisDao
public interface UserOpenPlatformDao extends CrudDao<UserOpenPlatform> {

    // 可传入merchantId/shopId 查询对应开发平台用户
    List<UserOpenPlatform> findListByMerchantShop(UserOpenPlatform userOpenPlatform);

    // 用于查找用户关注的公众号的开放平台用户 uid appId
    List<UserOpenPlatform> findByWechatPublic(UserOpenPlatform openPlatform);

    Integer countUserNum(@Param(value = "merchantId") String merchantId, @Param(value = "shopId") String shopId,
                         @Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime);

    /**
     * 查询平台商家的用户
     * @param userOpenPlatform
     * @return
     */
    List<UserOpenPlatform> findListByPmId(UserOpenPlatform userOpenPlatform);
}