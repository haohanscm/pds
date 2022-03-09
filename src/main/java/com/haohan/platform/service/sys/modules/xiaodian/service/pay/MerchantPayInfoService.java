package com.haohan.platform.service.sys.modules.xiaodian.service.pay;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.pay.MerchantPayInfoDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant.BankRegStatus;

/**
 * 商家支付信息Service
 * @author haohan
 * @version 2017-12-10
 */
@Service
@Transactional(readOnly = true)
public class MerchantPayInfoService extends CrudService<MerchantPayInfoDao, MerchantPayInfo> {

	public MerchantPayInfo get(String id) {
		return super.get(id);
	}
	
	public List<MerchantPayInfo> findList(MerchantPayInfo merchantPayInfo) {
		return super.findList(merchantPayInfo);
	}
	
	public Page<MerchantPayInfo> findPage(Page<MerchantPayInfo> page, MerchantPayInfo merchantPayInfo) {
		return super.findPage(page, merchantPayInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantPayInfo merchantPayInfo) {
		super.save(merchantPayInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantPayInfo merchantPayInfo) {
		super.delete(merchantPayInfo);
	}

	public MerchantPayInfo fetchByPartnerId(String partnerId){

		List<MerchantPayInfo>  list =  findList(new MerchantPayInfo(null,partnerId));

		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public MerchantPayInfo fetchByMerchantIdEnable(String merchantId, String bankChannel){
		MerchantPayInfo merchantPayInfo = new MerchantPayInfo();
		merchantPayInfo.setMerchantId(merchantId);
		merchantPayInfo.setIsEnable(ICommonConstant.YesNoType.yes.getCode());
		if(StringUtils.isNotBlank(bankChannel)) {
			merchantPayInfo.setBankChannel(bankChannel);
		}
		List<MerchantPayInfo> list = super.findList(merchantPayInfo);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public MerchantPayInfo fetchByAppId(String appId){

		MerchantPayInfo payInfo = new MerchantPayInfo();
		payInfo.setMercTrdCls(appId);
		payInfo.setRegStatus(BankRegStatus.SUCCESS.getCode());
		List<MerchantPayInfo>  list =  findList(payInfo);

		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}


}