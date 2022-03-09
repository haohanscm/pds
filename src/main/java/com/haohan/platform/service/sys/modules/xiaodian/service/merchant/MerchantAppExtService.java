package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.MerchantAppExtDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGroupManageService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家应用扩展信息管理Service
 * @author haohan
 * @version 2018-02-05
 */
@Service
@Transactional(readOnly = true)
public class MerchantAppExtService extends TreeService<MerchantAppExtDao, MerchantAppExt> {

	@Autowired
	private PhotoGroupManageService photoGroupManageService;

	@Autowired
	private MerchantAppExtDao merchantAppExtDao;


	public MerchantAppExt get(String id) {
		return super.get(id);
	}

	public Page<MerchantAppExt> findPage(Page<MerchantAppExt> page, MerchantAppExt merchantAppExt) {
		return super.findPage(page, merchantAppExt);
	}

	// 按appIp分页查询
	public Page<MerchantAppExt> fetchAppIdPage(Page<MerchantAppExt> page, MerchantAppExt merchantAppExt) {
	    merchantAppExt.setPage(page);
	    page.setList(dao.fetchAppId(merchantAppExt));
		return page;
	}

	public List<MerchantAppExt> findList(MerchantAppExt merchantAppExt) {
		return super.findList(merchantAppExt);
	}


	@Transactional(readOnly = false)
	public void save(MerchantAppExt merchantAppExt) {
		super.save(merchantAppExt);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantAppExt merchantAppExt) {
		super.delete(merchantAppExt);
	}

	@Transactional(readOnly = false)
	public void deleteWithChildren(MerchantAppExt merchantAppExt) {
		dao.deleteWithChildren(merchantAppExt);
	}


	//是否存在模板参数
	public boolean isExistExtInfo(MerchantAppManage merchantAppManage){
		MerchantAppExt merchantAppExt = new MerchantAppExt();
		merchantAppExt.setMerchantId(merchantAppManage.getMerchantId());
		merchantAppExt.setTemplateId(merchantAppManage.getTemplateId());
		merchantAppExt.setAppId(merchantAppManage.getAppId());
        // 包括del_flag不为0的记录
        int count = dao.existExtInfoCount(merchantAppExt);
        return count > 0 ? true : false;
	}

    public Map<String,Object> fetchAppExtInfo(String appId) {

		MerchantAppExt merchantAppExt = new MerchantAppExt();
		merchantAppExt.setAppId(appId);
		List<MerchantAppExt>  exts = findList(merchantAppExt);
		if(CollectionUtils.isEmpty(exts)){
			return null;
		}
		Map<String,Object> extMap = new HashMap<>();

		//构建map parentId为0则不取,子项加入Map作参数返回
		for(MerchantAppExt appExt:exts){

			if("0".equals(appExt.getParentId())){
				continue;
			}
			extMap.put(appExt.getFieldCode(),appExt.getFieldValue());
		}
		return extMap;
    };


	//查询树转换为Json对象


	//转移店铺模板参数到商家应用扩展
	@Transactional(readOnly = false)
	public void transToMerchantAppExt(List<ShopTemplateExtInfo> shopTemplateExtInfos, MerchantAppManage merchantAppManage, Merchant merchant,Shop shop,boolean isInsert){

		Map<String,String> merchantMap =  new HashMap<>();
		merchantMap.putAll(CommonUtils.beanToStrMap(merchantAppManage));
		merchantMap.putAll(CommonUtils.beanToStrMap(merchant));
		merchantMap.putAll(CommonUtils.beanToStrMap(shop));

		//创建对应规则

		merchantMap.put("phoneNum",shop.getTelephone());
		merchantMap.put("longitude",shop.getMapLongitude());
		merchantMap.put("latitude",shop.getMapLatitude());
		if(StringUtils.isNotEmpty(shop.getShopLogo())) {
			PhotoGroupManage groupManage = photoGroupManageService.fetchByGroupNum(shop.getShopLogo());
			if(null != groupManage) {
				List<PhotoManage> photoManages = groupManage.getPhotoManageList();
				if (CollectionUtils.isNotEmpty(photoManages)) {
					merchantMap.put("appLogo", photoManages.get(0).getPicUrl());//设置应用Logo
				}
			}
		}
		merchantMap.put("shopId", shop.getId());
		merchantMap.put("shopName", shop.getName());
		merchantMap.put("appTitle",merchantAppManage.getAppName());
		merchantMap.put("appDesc",merchant.getBizDesc());

        MerchantAppExt tempAppExt;

		for (ShopTemplateExtInfo template:shopTemplateExtInfos){
			MerchantAppExt merchantAppExt = new MerchantAppExt();
			merchantAppExt.setMerchantId(merchantAppManage.getMerchantId());
			merchantAppExt.setTemplateId(merchantAppManage.getTemplateId());
			String appId = merchantAppManage.getAppId();
			merchantAppExt.setAppId(appId);
			merchantAppExt.setId(template.getId().concat("-"+appId));
			if("0".equals(template.getParentId())){
				merchantAppExt.setParent(new MerchantAppExt(template.getParentId()));
			}else {
				merchantAppExt.setParent(new MerchantAppExt(template.getParentId().concat("-") + appId));
			}
			String[] parentIds = template.getParentIds().split(",");
			String[] ids = new String[parentIds.length];
			int i = 0;
			if(1== parentIds.length){
				ids[i] = "0";
			}else {
				for (String parentId : parentIds) {
					if (parentId.equals("0")) {
						ids[i] = parentId;
					} else {
						ids[i] = parentId.concat("-" + appId);
					}
					i++;
				}
			}
			merchantAppExt.setParentIds(org.apache.commons.lang3.StringUtils.join(ids,","));
			merchantAppExt.setName(template.getName());
			merchantAppExt.setSort(template.getSort());
			merchantAppExt.setFieldCode(template.getFieldCode());
			merchantAppExt.setFieldName(template.getFieldName());
			merchantAppExt.setFieldType(template.getFieldType());
			merchantAppExt.setIsNeed(template.getIsNeed());
			//从商家Map中获取对应的字段值
			merchantAppExt.setFieldValue(merchantMap.get(template.getFieldCode()));

			//以默认值为准
			if(StringUtils.isNotEmpty(template.getDefaultValue())) {
				merchantAppExt.setFieldValue(template.getDefaultValue());
			}

			merchantAppExt.setRemarks(template.getRemarks());
            // 插入或更新
			tempAppExt = dao.get(merchantAppExt.getId());
			if (null == tempAppExt) {
				merchantAppExt.setIsNewRecord(true);
				merchantAppExt.preInsert();
				super.dao.insert(merchantAppExt);
			}else{
				merchantAppExt.preUpdate();
				super.dao.update(merchantAppExt);
			}
		}


	}

}