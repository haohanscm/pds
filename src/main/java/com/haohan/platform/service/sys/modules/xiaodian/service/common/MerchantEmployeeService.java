package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.MerchantEmployeeDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 员工管理Service
 * @author haohan
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly = true)
public class MerchantEmployeeService extends CrudService<MerchantEmployeeDao, MerchantEmployee> {

	public MerchantEmployee get(String id) {
		return super.get(id);
	}
	
	public List<MerchantEmployee> findList(MerchantEmployee merchantEmployee) {
		return super.findList(merchantEmployee);
	}

	// 带merchantName
    public List<MerchantEmployee> findJoinList(MerchantEmployee merchantEmployee) {
        return dao.findJoinList(merchantEmployee);
    }

	public List<MerchantEmployee> fetchByMobile(String mobile){
		MerchantEmployee merchantEmployee = new MerchantEmployee();
		merchantEmployee.setTelephone(mobile);
		return dao.findList(merchantEmployee);
	}

	public List<MerchantEmployee> findByUid(String uid){
		MerchantEmployee merchantEmployee = new MerchantEmployee();
		merchantEmployee.setPassportId(uid);
		merchantEmployee.setStatus(ICommonConstant.YesNoType.yes.getCode());
		return dao.findList(merchantEmployee);
	}

	public MerchantEmployee fetchByUidAndRole(String uid, IPdsConstant.EmployeeRole role){
		MerchantEmployee merchantEmployee = new MerchantEmployee();
		merchantEmployee.setPassportId(uid);
		merchantEmployee.setRole(role.getCode());
		merchantEmployee.setStatus(ICommonConstant.YesNoType.yes.getCode());
		List<MerchantEmployee> list = dao.findList(merchantEmployee);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public Page<MerchantEmployee> findPage(Page<MerchantEmployee> page, MerchantEmployee merchantEmployee) {
		return super.findPage(page, merchantEmployee);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantEmployee merchantEmployee) {
		super.save(merchantEmployee);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantEmployee merchantEmployee) {
		super.delete(merchantEmployee);
	}

	// 根据 pmId/name(放入姓名或手机号)/role/status 关联查询到upassport表的password信息
    public List<MerchantEmployee> fetchPassword(MerchantEmployee merchantEmployee){
        merchantEmployee.setStatus(ICommonConstant.YesNoType.yes.getCode());
        return dao.fetchPassword(merchantEmployee);
    }
	
}