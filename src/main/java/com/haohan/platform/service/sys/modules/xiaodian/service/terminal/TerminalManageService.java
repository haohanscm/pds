package com.haohan.platform.service.sys.modules.xiaodian.service.terminal;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.terminal.TerminalManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.teiminal.TerminalManage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 终端设备Service
 * @author shenyu
 * @version 2018-08-18
 */
@Service
@Transactional(readOnly = true)
public class TerminalManageService extends CrudService<TerminalManageDao, TerminalManage> {

	public TerminalManage get(String id) {
		return super.get(id);
	}
	
	public List<TerminalManage> findList(TerminalManage terminalManage) {
		return super.findList(terminalManage);
	}
	
	public Page<TerminalManage> findPage(Page<TerminalManage> page, TerminalManage terminalManage) {
		return super.findPage(page, terminalManage);
	}
	
	@Transactional(readOnly = false)
	public void save(TerminalManage terminalManage) {
		super.save(terminalManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(TerminalManage terminalManage) {
		super.delete(terminalManage);
	}

	public TerminalManage fetchByShopId(String shopId){
		TerminalManage terminalManage = new TerminalManage();
		terminalManage.setShopId(shopId);
		List<TerminalManage> terminalManageList = super.findList(terminalManage);
		return CollectionUtils.isNotEmpty(terminalManageList) ?terminalManageList.get(0):null;
	}
	
}