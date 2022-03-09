package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.MerchantAccountDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAccount;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理不同渠道的商户账号Service
 * @author yu.shen
 * @version 2018-06-11
 */
@Service
@Transactional(readOnly = true)
public class MerchantAccountService extends CrudService<MerchantAccountDao, MerchantAccount> {

	public MerchantAccount get(String id) {
		return super.get(id);
	}
	
	public List<MerchantAccount> findList(MerchantAccount merchantAccount) {
		return super.findList(merchantAccount);
	}
	
	public Page<MerchantAccount> findPage(Page<MerchantAccount> page, MerchantAccount merchantAccount) {
		return super.findPage(page, merchantAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantAccount merchantAccount) {
		super.save(merchantAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantAccount merchantAccount) {
		super.delete(merchantAccount);
	}



    public MerchantAccount fetchByMobile(String mobile){
		MerchantAccount merchantAccount = new MerchantAccount();
		merchantAccount.setMobile(mobile);

		List<MerchantAccount> list = findList(merchantAccount);

		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public MerchantAccount fetchByMerchantId(String merchantId){
		MerchantAccount merchantAccount = new MerchantAccount();
		merchantAccount.setMerchantId(merchantId);
		List<MerchantAccount> list = findList(merchantAccount);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public MerchantAccount fetchBySid(String sid) {
		MerchantAccount merchantAccount = new MerchantAccount();
		merchantAccount.setSid(sid);

		List<MerchantAccount> list = findList(merchantAccount);

		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
}