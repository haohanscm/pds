package com.haohan.platform.service.sys.modules.pds.service.business;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.business.PdsSupplierDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商管理Service
 * @author haohan
 * @version 2018-10-26
 */
@Service
@Transactional(readOnly = true)
public class PdsSupplierService extends CrudService<PdsSupplierDao, PdsSupplier> {

	public PdsSupplier get(String id) {
		return super.get(id);
	}
	
	public List<PdsSupplier> findList(PdsSupplier pdsSupplier) {
		return super.findList(pdsSupplier);
	}

    /**
     * 带 pmName
     * @param pdsSupplier
     * @return
     */
    public List<PdsSupplier> findJoinList(PdsSupplier pdsSupplier) {
        return dao.findJoinList(pdsSupplier);
    }
	
	public Page<PdsSupplier> findPage(Page<PdsSupplier> page, PdsSupplier pdsSupplier) {
		return super.findPage(page, pdsSupplier);
	}

    /**
     * 查询启用状态的供应商
      * @return
     */
	public List<PdsSupplier> findUsableList() {
        PdsSupplier pdsSupplier = new PdsSupplier();
        pdsSupplier.setStatus(ICommonConstant.YesNoType.yes.getCode());
		return super.findList(pdsSupplier);
	}

    public List<PdsSupplier> findUsableList(PdsSupplier pdsSupplier) {
        pdsSupplier.setStatus(ICommonConstant.YesNoType.yes.getCode());
        return super.findList(pdsSupplier);
    }
	
	@Transactional(readOnly = false)
	public void save(PdsSupplier pdsSupplier) {
        // 默认排序值
        if(StringUtils.isEmpty(pdsSupplier.getSort())){
            pdsSupplier.setSort("100");
        }
		super.save(pdsSupplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(PdsSupplier pdsSupplier) {
		super.delete(pdsSupplier);
	}

    /**
     * 根据PassPortId(uid)查找
     * @param uid
     * @param pmId
     * @return
     */
	public PdsSupplier fetchByPassPortId(String uid,String pmId) {
        PdsSupplier supplier = new PdsSupplier();
		supplier.setPassportId(uid);
		supplier.setPmId(pmId);
		List<PdsSupplier> list = dao.findJoinList(supplier);
		return Collections3.isEmpty(list) ? null : list.get(0);
	}

    /**
     * 根据手机号查找
     * @param telephone
     * @return
     */
    public PdsSupplier fetchByMobile(String telephone) {
        PdsSupplier supplier = new PdsSupplier();
        supplier.setTelephone(telephone);
        List<PdsSupplier> list = super.findList(supplier);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 供应商所属商家列表
     * @return
     */
    public List<PdsSupplier> findMerchantList(PdsSupplier supplier) {
        return dao.findMerchantList(supplier);
    }

    /**
     * 供应商所属商家列表
     * @return
     */
    public List<PdsSupplier> findMerchantList(String pmId) {
	    PdsSupplier supplier = new PdsSupplier();
	    supplier.setPmId(pmId);
        return dao.findMerchantList(supplier);
    }

    /**
     * 查询供应商的商品(sku)报价
     * 用于查询提供该商品的供应商及供应商报价(supplyPrice);
     * 供应商作为平台商家时无采购商采购价,则价格为市场价;
     * @param supplierGoods  必需goodsModelId/pmId
     * @return
     */
    public List<PdsSupplier> findListWithSupplyPrice(SupplierGoods supplierGoods) {
        // 供应商启用状态;供应商商品启用状态;供应商商家为平台商时该商品上架状态/采购商商品上下架状态;  都是yes_no 共用传入
        supplierGoods.setStatus(ICommonConstant.YesNoType.yes.getCode());
        return dao.findListWithSupplyPrice(supplierGoods);
    }
}