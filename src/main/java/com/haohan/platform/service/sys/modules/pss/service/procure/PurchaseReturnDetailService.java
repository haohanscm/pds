package com.haohan.platform.service.sys.modules.pss.service.procure;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.dao.procure.PurchaseReturnDetailDao;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturnDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购退货明细Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class PurchaseReturnDetailService extends CrudService<PurchaseReturnDetailDao, PurchaseReturnDetail> {

	public PurchaseReturnDetail get(String id) {
		return super.get(id);
	}
	
	public List<PurchaseReturnDetail> findList(PurchaseReturnDetail purchaseReturnDetail) {
		return super.findList(purchaseReturnDetail);
	}

	public List<PurchaseReturnDetail> findByReturnId(String returnId){
		PurchaseReturnDetail purchaseReturnDetail = new PurchaseReturnDetail();
		purchaseReturnDetail.setReturnId(returnId);
		List<PurchaseReturnDetail> list = dao.findList(purchaseReturnDetail);
		return list;
	}
	
	public Page<PurchaseReturnDetail> findPage(Page<PurchaseReturnDetail> page, PurchaseReturnDetail purchaseReturnDetail) {
		return super.findPage(page, purchaseReturnDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaseReturnDetail purchaseReturnDetail) {
		super.save(purchaseReturnDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaseReturnDetail purchaseReturnDetail) {
		super.delete(purchaseReturnDetail);
	}
	
}