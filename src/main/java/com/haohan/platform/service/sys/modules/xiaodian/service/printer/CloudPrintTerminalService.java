package com.haohan.platform.service.sys.modules.xiaodian.service.printer;

import java.util.List;

import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.CloudPrintTerminal;
import com.haohan.platform.service.sys.modules.xiaodian.dao.printer.CloudPrintTerminalDao;

/**
 * 易联云打印机管理Service
 * @author haohan
 * @version 2018-11-26
 */
@Service
@Transactional(readOnly = true)
public class CloudPrintTerminalService extends CrudService<CloudPrintTerminalDao, CloudPrintTerminal> {

	public CloudPrintTerminal get(String id) {
		return super.get(id);
	}
	
	public List<CloudPrintTerminal> findList(CloudPrintTerminal cloudPrintTerminal) {
		return super.findList(cloudPrintTerminal);
	}

	// 用于列表展示  shopName/merchantName
    public List<CloudPrintTerminal> findJoinList(CloudPrintTerminal cloudPrintTerminal) {
		return dao.findJoinList(cloudPrintTerminal);
	}

	public CloudPrintTerminal fetchByMachineCode(String machineCode){
		CloudPrintTerminal cloudPrintTerminal = new CloudPrintTerminal();
		cloudPrintTerminal.setMachineCode(machineCode);
		cloudPrintTerminal.setStatus(ICommonConstant.IsEnable.enable.getCode());
		List<CloudPrintTerminal> list = dao.findList(cloudPrintTerminal);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	public Page<CloudPrintTerminal> findPage(Page<CloudPrintTerminal> page, CloudPrintTerminal cloudPrintTerminal) {
		return super.findPage(page, cloudPrintTerminal);
	}
	
	@Transactional(readOnly = false)
	public void save(CloudPrintTerminal cloudPrintTerminal) {
		super.save(cloudPrintTerminal);
	}
	
	@Transactional(readOnly = false)
	public void delete(CloudPrintTerminal cloudPrintTerminal) {
		super.delete(cloudPrintTerminal);
	}
	
}