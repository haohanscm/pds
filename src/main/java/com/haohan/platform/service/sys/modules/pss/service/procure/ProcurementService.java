package com.haohan.platform.service.sys.modules.pss.service.procure;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import com.haohan.platform.service.sys.modules.pss.dao.procure.ProcurementDao;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.xiaodian.util.PssCommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品采购Service
 * @author haohan
 * @version 2018-09-07
 */
@Service
@Transactional(readOnly = true)
public class ProcurementService extends CrudService<ProcurementDao, Procurement> {

	public Procurement get(String id) {
		return super.get(id);
	}
	
	public List<Procurement> findList(Procurement procurement) {
		return super.findList(procurement);
	}

	public Procurement fetchByProcureNum(String procureNum){
		Procurement procurement = new Procurement();
		procurement.setProcureNum(procureNum);
		List<Procurement> procurementList = super.findList(procurement);
		return CollectionUtils.isEmpty(procurementList)?null:procurementList.get(0);
	}

	public Page<Procurement> findPage(Page<Procurement> page, Procurement procurement) {
		return super.findPage(page, procurement);
	}

	public Page<Procurement> findJoinPage(Page<Procurement> page,Procurement procurement){
		procurement.setPage(page);
		page.setList(dao.findJoinList(procurement));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(Procurement procurement) {
		if (StringUtils.isEmpty(procurement.getProcureNum())){
			String procureNum = PdsCommonUtil.incrIdByClass(Procurement.class, IPdsConstant.PURCHASE_SN_PRE);
			procurement.setProcureNum(procureNum);
		}
		super.save(procurement);
	}
	
	@Transactional(readOnly = false)
	public void delete(Procurement procurement) {
		super.delete(procurement);
	}
	
}