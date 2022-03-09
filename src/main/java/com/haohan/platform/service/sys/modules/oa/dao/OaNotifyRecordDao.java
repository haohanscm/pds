/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.modules.oa.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.oa.entity.OaNotifyRecord;

import java.util.List;

/**
 * 通知通告记录DAO接口
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyRecordDao extends CrudDao<OaNotifyRecord> {

    /**
     * 插入通知记录
     *
     * @param oaNotifyRecordList
     * @return
     */
    public int insertAll(List<OaNotifyRecord> oaNotifyRecordList);

    /**
     * 根据通知ID删除通知记录
     *
     * @param oaNotifyId 通知ID
     * @return
     */
    public int deleteByOaNotifyId(String oaNotifyId);

}