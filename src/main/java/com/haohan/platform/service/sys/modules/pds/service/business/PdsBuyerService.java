package com.haohan.platform.service.sys.modules.pds.service.business;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisOverViewReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.business.PdsBuyerDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 采购商管理Service
 *
 * @author haohan
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class PdsBuyerService extends CrudService<PdsBuyerDao, PdsBuyer> {

    public PdsBuyer get(String id) {
        return super.get(id);
    }

    // 带 pmName
    public PdsBuyer getWithName(String id) {
        return dao.getWithName(id);
    }

    public List<PdsBuyer> findList(PdsBuyer pdsBuyer) {
        return super.findList(pdsBuyer);
    }

    public PdsBuyer fetchByPmMerType(String destPmId, String merchantId, IPdsConstant.BuyerType buyerType){
        PdsBuyer pdsBuyer = new PdsBuyer();
        pdsBuyer.setPmId(destPmId);
        pdsBuyer.setMerchantId(merchantId);
        pdsBuyer.setBuyerType(buyerType.getCode());
        List<PdsBuyer> buyerList = super.findList(pdsBuyer);
        return CollectionUtils.isEmpty(buyerList)?null:buyerList.get(0);
    }

    // 带 pmName
    public List<PdsBuyer> findJoinList(PdsBuyer pdsBuyer) {
        return dao.findJoinList(pdsBuyer);
    }

    public List<PdsBuyer> findUsableList(PdsBuyer pdsBuyer) {
        pdsBuyer.setStatus(ICommonConstant.YesNoType.yes.getCode());
        return super.findList(pdsBuyer);
    }

    //获取自提订单采购商列表
//	public List<PdsBuyer> findOrderBuyerList(PdsAdmSelfOrderReq pdsAdmSelfOrderReq){
//		return dao.findOrderBuyerList(pdsAdmSelfOrderReq);
//	}

    public Page<PdsBuyer> findPage(Page<PdsBuyer> page, PdsBuyer pdsBuyer) {
        return super.findPage(page, pdsBuyer);
    }

    @Transactional(readOnly = false)
    public void save(PdsBuyer pdsBuyer) {
        // 默认排序值
        if(StringUtils.isEmpty(pdsBuyer.getSort())){
            pdsBuyer.setSort("100");
        }
        super.save(pdsBuyer);
    }

    @Transactional(readOnly = false)
    public void delete(PdsBuyer pdsBuyer) {
        super.delete(pdsBuyer);
    }

    // 根据手机号查找
    public PdsBuyer fetchByMobile(String telephone) {
        if (StringUtils.isEmpty(telephone)) {
            return null;
        }
        PdsBuyer buyer = new PdsBuyer();
        buyer.setTelephone(telephone);
        List<PdsBuyer> list = super.findList(buyer);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }

    // 根据PassPortId(uid)查找
    public PdsBuyer fetchByPassPortId(String uid,String pmId,String merchantId) {
        if (StringUtils.isEmpty(uid)) {
            return null;
        }
        PdsBuyer buyer = new PdsBuyer();
        buyer.setPassportId(uid);
        buyer.setPmId(pmId);
        List<PdsBuyer> list = dao.findJoinList(buyer);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }

    // 采购商所属商家列表
    public List<PdsBuyer> findMerchantList() {
        return dao.findMerchantList(new PdsBuyer());
    }

    public List<PdsBuyer> findMerchantList(String pmId) {
        PdsBuyer buyer = new PdsBuyer();
        buyer.setPmId(pmId);
        return dao.findMerchantList(buyer);
    }

    // 平台运营商家列表查询(采购商/供应商关联pmId)
    public List<Merchant> findPlatformMerchantList() {
        Merchant merchant = new Merchant();
        merchant.setStatus(StringUtils.toInteger(IMerchantConstant.available));
        return dao.findPlatformMerchantList(merchant);
    }

    public int countUserNum(PdsApiStatisOverViewReq statisOverViewReq) {
        return dao.countUserNum(statisOverViewReq);
    }
}