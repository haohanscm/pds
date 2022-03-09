package com.haohan.platform.service.sys.modules.pds.service.cost;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.cost.SupplierPaymentDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 供应商货款统计Service
 * @author haohan
 * @version 2018-11-06
 */
@Service
@Transactional(readOnly = true)
public class SupplierPaymentService extends CrudService<SupplierPaymentDao, SupplierPayment> {

	public SupplierPayment get(String id) {
		return super.get(id);
	}
	
	public List<SupplierPayment> findList(SupplierPayment supplierPayment) {
		return super.findList(supplierPayment);
	}

    // 带buyerName/merchantId/merchantName
    public List<SupplierPayment> findJoinList(SupplierPayment supplierPayment) {
        return dao.findJoinList(supplierPayment);
    }
	
	public Page<SupplierPayment> findPage(Page<SupplierPayment> page, SupplierPayment supplierPayment) {
		return super.findPage(page, supplierPayment);
	}
	
	@Transactional(readOnly = false)
	public void save(SupplierPayment supplierPayment) {
        if(StringUtils.isEmpty(supplierPayment.getSupplierPaymentId())){
            String supplierPaymentId = PdsCommonUtil.incrIdByClass(SupplierPayment.class, IPdsConstant.SUPPLIER_PAYMENT_PREFIX);
            supplierPayment.setSupplierPaymentId(supplierPaymentId);
        }
		super.save(supplierPayment);
	}
	
	@Transactional(readOnly = false)
	public void delete(SupplierPayment supplierPayment) {
		super.delete(supplierPayment);
	}

    /**
     * 修改货款状态 批量
     * @param payment 必需: stauts/finalStatus / pmId/supplierPaymentId
     * @return
     */
    @Transactional(readOnly = false)
    public int updateStatusBatch(SupplierPayment payment) {
        return dao.updateStatusBatch(payment);
    }

    // 根据supplierId/supplyDate 查询货款
    public SupplierPayment findByDateAndSupplier(SupplierPayment payment) {
        String pmId = payment.getPmId();
        String supplierId = payment.getSupplierId();
        Date supplyDate = payment.getSupplyDate();
        if (StringUtils.isAnyEmpty(pmId, supplierId) || null == supplyDate) {
            return null;
        }
        SupplierPayment supplierPayment = new SupplierPayment();
        supplierPayment.setPmId(pmId);
        supplierPayment.setSupplierId(supplierId);
        supplierPayment.setSupplyDate(supplyDate);
        List<SupplierPayment> list = dao.findList(supplierPayment);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }
}