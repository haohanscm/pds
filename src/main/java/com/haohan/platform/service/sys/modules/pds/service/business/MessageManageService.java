package com.haohan.platform.service.sys.modules.pds.service.business;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.business.MessageManageDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.MessageManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息管理Service
 * @author haohan
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class MessageManageService extends CrudService<MessageManageDao, MessageManage> {

	public MessageManage get(String id) {
		return super.get(id);
	}
	
	public List<MessageManage> findList(MessageManage messageManage) {
		return super.findList(messageManage);
	}
	
	public Page<MessageManage> findPage(Page<MessageManage> page, MessageManage messageManage) {
		return super.findPage(page, messageManage);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageManage messageManage) {
		super.save(messageManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageManage messageManage) {
		super.delete(messageManage);
	}
	
}