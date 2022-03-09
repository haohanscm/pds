package com.haohan.platform.service.sys.modules.weixin.mp.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.weixin.mp.base.entity.WechatUserMsgevent;
import com.haohan.platform.service.sys.modules.weixin.mp.base.dao.WechatUserMsgeventDao;

/**
 * 微信用户消息事件Service
 * @author haohan
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class WechatUserMsgeventService extends CrudService<WechatUserMsgeventDao, WechatUserMsgevent> {

	public WechatUserMsgevent get(String id) {
		return super.get(id);
	}
	
	public List<WechatUserMsgevent> findList(WechatUserMsgevent wechatUserMsgevent) {
		return super.findList(wechatUserMsgevent);
	}
	
	public Page<WechatUserMsgevent> findPage(Page<WechatUserMsgevent> page, WechatUserMsgevent wechatUserMsgevent) {
		return super.findPage(page, wechatUserMsgevent);
	}
	
	@Transactional(readOnly = false)
	public void save(WechatUserMsgevent wechatUserMsgevent) {
		super.save(wechatUserMsgevent);
	}
	
	@Transactional(readOnly = false)
	public void delete(WechatUserMsgevent wechatUserMsgevent) {
		super.delete(wechatUserMsgevent);
	}
	
}