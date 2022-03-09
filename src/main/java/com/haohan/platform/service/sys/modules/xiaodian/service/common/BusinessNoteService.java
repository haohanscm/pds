package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.BusinessNoteDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.BusinessNote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商务留言管理Service
 * @author haohan
 * @version 2018-02-23
 */
@Service
@Transactional(readOnly = true)
public class BusinessNoteService extends CrudService<BusinessNoteDao, BusinessNote> {

	public BusinessNote get(String id) {
		return super.get(id);
	}
	
	public List<BusinessNote> findList(BusinessNote businessNote) {
		return super.findList(businessNote);
	}
	
	public Page<BusinessNote> findPage(Page<BusinessNote> page, BusinessNote businessNote) {
		return super.findPage(page, businessNote);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessNote businessNote) {
		super.save(businessNote);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessNote businessNote) {
		super.delete(businessNote);
	}
	
}