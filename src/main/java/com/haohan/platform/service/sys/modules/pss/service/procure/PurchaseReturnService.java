package com.haohan.platform.service.sys.modules.pss.service.procure;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import com.haohan.platform.service.sys.modules.pss.dao.procure.PurchaseReturnDao;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;
import com.haohan.platform.service.sys.modules.xiaodian.util.PssCommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购退货Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class PurchaseReturnService extends CrudService<PurchaseReturnDao, PurchaseReturn> {

	public PurchaseReturn get(String id) {
		return super.get(id);
	}
	
	public List<PurchaseReturn> findList(PurchaseReturn purchaseReturn) {
		return super.findList(purchaseReturn);
	}
	
	public Page<PurchaseReturn> findPage(Page<PurchaseReturn> page, PurchaseReturn purchaseReturn) {
		return super.findPage(page, purchaseReturn);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaseReturn purchaseReturn) {
		if (StringUtils.isEmpty(purchaseReturn.getReturnNum())){
			String returnNum = PdsCommonUtil.incrIdByClass(Procurement.class, IPdsConstant.RETURN_ORDER_SN_PRE);
			purchaseReturn.setReturnNum(returnNum);
		}
		super.save(purchaseReturn);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaseReturn purchaseReturn) {
		super.delete(purchaseReturn);
	}
	
}