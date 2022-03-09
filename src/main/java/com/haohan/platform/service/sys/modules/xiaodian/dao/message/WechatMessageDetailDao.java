package com.haohan.platform.service.sys.modules.xiaodian.dao.message;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.message.WechatMessageDetail;

/**
 * 消息模板管理DAO接口
 * @author haohan
 * @version 2018-04-26
 */
@MyBatisDao
public interface WechatMessageDetailDao extends CrudDao<WechatMessageDetail> {
	
}