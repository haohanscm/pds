package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.AuthAccountDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.AuthAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号信息认证管理Service
 * @author dy
 * @version 2018-10-15
 */
@Service
@Transactional(readOnly = true)
public class AuthAccountService extends CrudService<AuthAccountDao, AuthAccount> {

	public AuthAccount get(String id) {
		return super.get(id);
	}
	
	public List<AuthAccount> findList(AuthAccount authAccount) {
		return super.findList(authAccount);
	}
	
	public Page<AuthAccount> findPage(Page<AuthAccount> page, AuthAccount authAccount) {
		return super.findPage(page, authAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(AuthAccount authAccount) {
		super.save(authAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(AuthAccount authAccount) {
		super.delete(authAccount);
	}
	
}