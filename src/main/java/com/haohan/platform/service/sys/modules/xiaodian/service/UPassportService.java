package com.haohan.platform.service.sys.modules.xiaodian.service;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.UPassportDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 通行证管理Service
 * @author haohan
 * @version 2017-08-05
 */
@Service
@Transactional(readOnly = true)
public class UPassportService extends CrudService<UPassportDao, UPassport> {

	public UPassport get(String id) {
		return super.get(new UPassport(id));
	}
	
	public List<UPassport> findList(UPassport uPassport) {
		return super.findList(uPassport);
	}
	
	public Page<UPassport> findPage(Page<UPassport> page, UPassport uPassport) {
		return super.findPage(page, uPassport);
	}
	
	@Transactional(readOnly = false)
	public void save(UPassport uPassport) {
		super.save(uPassport);
	}
	
	@Transactional(readOnly = false)
	public void delete(UPassport uPassport) {
		super.delete(uPassport);
	}

	public UPassport fetchByUnionID(String unionId){
		UPassport passport = new UPassport();
		passport.setUnionId(unionId);
		List<UPassport> list = findList(passport);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public UPassport fetchByMobile(String mobile){
		UPassport passport = new UPassport();
		passport.setTelephone(mobile);
		List<UPassport> list = findList(passport);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}


	public UPassport fetchByEmail(String email) {
		UPassport passport = new UPassport();
		passport.setEmail(email);
		List<UPassport> list = findList(passport);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public UPassport fetchByLoginName(String loginAccount) {
		UPassport passport = new UPassport();
		passport.setLoginName(loginAccount);
		List<UPassport> list = findList(passport);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public Integer countUserNum(Date startTime, Date endTime) {
		Integer result = dao.countUserNum(startTime,endTime);
		return null == result ? 0 : result;
	}
}