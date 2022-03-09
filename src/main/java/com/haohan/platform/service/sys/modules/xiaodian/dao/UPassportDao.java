package com.haohan.platform.service.sys.modules.xiaodian.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 通行证管理DAO接口
 * @author haohan
 * @version 2017-08-05
 */
@MyBatisDao
public interface UPassportDao extends CrudDao<UPassport> {

    Integer countUserNum(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime);
}