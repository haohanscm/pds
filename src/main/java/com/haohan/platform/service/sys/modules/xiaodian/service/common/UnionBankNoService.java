package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.UnionBankNoDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.UnionBankNo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 银行行号管理Service
 * @author haohan
 * @version 2017-12-10
 */
@Service
@Transactional(readOnly = true)
public class UnionBankNoService extends CrudService<UnionBankNoDao, UnionBankNo> {

	public UnionBankNo get(String id) {
		return super.get(id);
	}
	
	public List<UnionBankNo> findList(UnionBankNo unionBankNo) {
		return super.findList(unionBankNo);
	}
	
	public Page<UnionBankNo> findPage(Page<UnionBankNo> page, UnionBankNo unionBankNo) {
		return super.findPage(page, unionBankNo);
	}
	
	@Transactional(readOnly = false)
	public void save(UnionBankNo unionBankNo) {
		super.save(unionBankNo);
	}
	
	@Transactional(readOnly = false)
	public void delete(UnionBankNo unionBankNo) {
		super.delete(unionBankNo);
	}
	
}