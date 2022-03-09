package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.LogApiRecordDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.LogApiRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 接口日志记录Service
 * @author haohan
 * @version 2018-05-29
 */
@Service
@Transactional(readOnly = true)
public class LogApiRecordService extends CrudService<LogApiRecordDao, LogApiRecord> {

	public LogApiRecord get(String id) {
		return super.get(id);
	}
	
	public List<LogApiRecord> findList(LogApiRecord logApiRecord) {
		return super.findList(logApiRecord);
	}
	
	public Page<LogApiRecord> findPage(Page<LogApiRecord> page, LogApiRecord logApiRecord) {
		return super.findPage(page, logApiRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(LogApiRecord logApiRecord) {
		super.save(logApiRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(LogApiRecord logApiRecord) {
		super.delete(logApiRecord);
	}
	
}