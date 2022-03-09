package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.sys.utils.UserUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.MerchantDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantFiles;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGroupManageService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家管理Service
 * @author haohan
 * @version 2017-08-05
 */
@Service
@Transactional(readOnly = true)
public class MerchantService extends CrudService<MerchantDao, Merchant> {

	@Autowired
	private PhotoGroupManageService photoGroupManageService;
	@Autowired
	private UPassportService passportService;

	public Merchant get(String id) {
		return super.get(id);
	}
	
	public List<Merchant> findList(Merchant merchant) {
		return super.findList(merchant);
	}

    /**
     * 带parentName
     * @param merchant
     * @return
     */
    public List<Merchant> findJoinList(Merchant merchant) {
        return dao.findJoinList(merchant);
    }
	
	public Page<Merchant> findPage(Page<Merchant> page, Merchant merchant) {
		return super.findPage(page, merchant);
	}
	
	@Transactional(readOnly = false)
	public void save(Merchant merchant) {
	    // 无归属商家设置
	    if(StringUtils.isEmpty(merchant.getParentId())){
	        merchant.setParentId("0");
        }
        // 设置平台商家和tenantId的映射
        String tenantId = StringUtils.toString(merchant.getTenantId(),"");
        if (StringUtils.equals(IMerchantConstant.PdsType.platform.getType(), merchant.getPdsType()) && StringUtils.isNotEmpty(tenantId)) {
            Map<String, String> map = new HashMap<>(4);
            map.put(merchant.getId(), tenantId);
            JedisUtils.mapPut(IPdsConstant.TENANT_MAP_KEY, map);
            // 修改平台商家时 ,设置此后都是该租户id
            Session session = UserUtils.getSession();
            session.setAttribute(IPdsConstant.TENANT_KEY, merchant.getTenantId());
        }
		super.save(merchant);
	}
	
	@Transactional(readOnly = false)
	public void delete(Merchant merchant) {
		super.delete(merchant);
	}

    public List<Merchant> findListEnabled(Merchant merchant) {
	    merchant.setStatus(StringUtils.toInteger(IMerchantConstant.available));
        return super.findList(merchant);
    }

    /**
     * 根据uid查找商家
     *
     * @param uid
     * @return
     */
    public Merchant fetchByUid(String uid) {
        Merchant merchant = new Merchant();
        merchant.setUpassport(new UPassport(uid));
        List<Merchant> list = super.findList(merchant);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }


	public MerchantFiles findMerchantFiles(String merchantId){

		MerchantFiles merchantFiles = new MerchantFiles();

		PhotoGroupManage shopPhotos = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantFiles.genGroupNum(merchantId,IMerchantConstant.MerchantFilesType.ShopPhotos));
		PhotoGroupManage merchantPhotos = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantFiles.genGroupNum(merchantId,IMerchantConstant.MerchantFilesType.MerchantPhotos));
		PhotoGroupManage protocolFiles = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantFiles.genGroupNum(merchantId,IMerchantConstant.MerchantFilesType.ProtocolFiles));
		PhotoGroupManage productPhotos = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantFiles.genGroupNum(merchantId,IMerchantConstant.MerchantFilesType.productPhotos));
		PhotoGroupManage cateringPhotos = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantFiles.genGroupNum(merchantId,IMerchantConstant.MerchantFilesType.cateringLicense));

		PhotoGroupManage licensePhotos = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantFiles.genGroupNum(merchantId,IMerchantConstant.MerchantFilesType.license));
        PhotoGroupManage idCardsPhotos = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantFiles.genGroupNum(merchantId,IMerchantConstant.MerchantFilesType.idCardPhotos));
        PhotoGroupManage bankCardsPhotos = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantFiles.genGroupNum(merchantId,IMerchantConstant.MerchantFilesType.bankCardPhotos));


        merchantFiles.setShopPhotos(shopPhotos);
		merchantFiles.setMerchantPhotos(merchantPhotos);
		merchantFiles.setProtocolFiles(protocolFiles);
		merchantFiles.setProductPhotos(productPhotos);

		merchantFiles.setMerchantId(merchantId);
        merchantFiles.setCateringPhotos(cateringPhotos);
        merchantFiles.setLicensePhotos(licensePhotos);
        merchantFiles.setIdCardsPhotos(idCardsPhotos);
        merchantFiles.setBankCardsPhotos(bankCardsPhotos);


		return merchantFiles;
	}

}