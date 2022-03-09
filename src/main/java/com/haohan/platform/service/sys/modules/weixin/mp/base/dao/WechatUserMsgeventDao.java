package com.haohan.platform.service.sys.modules.weixin.mp.base.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.weixin.mp.base.entity.WechatUserMsgevent;

/**
 * 微信用户消息事件DAO接口
 * @author haohan
 * @version 2018-11-07
 */
@MyBatisDao
public interface WechatUserMsgeventDao extends CrudDao<WechatUserMsgevent> {
	
}