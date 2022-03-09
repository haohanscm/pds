package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.ShopTemplateExtInfoDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopTemplateExtInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 店铺模板扩展信息管理Service
 * @author haohan
 * @version 2018-02-05
 */
@Service
@Transactional(readOnly = true)
public class ShopTemplateExtInfoService extends TreeService<ShopTemplateExtInfoDao, ShopTemplateExtInfo> {


	public ShopTemplateExtInfo get(String id) {
		return super.get(id);
	}
	
	public List<ShopTemplateExtInfo> findList(ShopTemplateExtInfo shopTemplateExtInfo) {
		if (StringUtils.isNotBlank(shopTemplateExtInfo.getParentIds())){
			shopTemplateExtInfo.setParentIds(","+shopTemplateExtInfo.getParentIds());
		}
		return super.findList(shopTemplateExtInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopTemplateExtInfo shopTemplateExtInfo) {
		super.save(shopTemplateExtInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopTemplateExtInfo shopTemplateExtInfo) {
		super.delete(shopTemplateExtInfo);
	}

	public List<ShopTemplateExtInfo> findByTemplateId(String templateId){

		ShopTemplateExtInfo shopTemplateExtInfo = new ShopTemplateExtInfo();
		shopTemplateExtInfo.setTemplateId(templateId);
		List<ShopTemplateExtInfo> list = findList(shopTemplateExtInfo);

		return CollectionUtils.isEmpty(list)?null:list;
	}

	//构建店铺模板扩展参数
	@Transactional(readOnly = false)
	public void configShopTemplateExt(String templateId,String templateName){

		List<ShopTemplateExtInfo> list = findByTemplateId(templateId);
		//模板存在则不创建
		if(CollectionUtils.isNotEmpty(list)){
			return;
		}

		//从字典数据中获取后创建

		List<Dict> dicts = DictUtils.getDictList("shop_template_field");
		if(CollectionUtils.isNotEmpty(dicts)){
			int sort = 30;
			ShopTemplateExtInfo extInfo = new ShopTemplateExtInfo();
			extInfo.setTemplateId(templateId);
			extInfo.setName(templateName);
			extInfo.setIsNeed("1");
			extInfo.setFieldType("0");//都是文本类型
			extInfo.setSort(sort);
			super.save(extInfo);
			for (Dict dict:dicts){
				ShopTemplateExtInfo tepExt = new ShopTemplateExtInfo();
				tepExt.setTemplateId(templateId);
				tepExt.setName(dict.getLabel());
				tepExt.setFieldName(dict.getLabel());
				tepExt.setFieldCode(dict.getValue());
				tepExt.setIsNeed("1");
				tepExt.setFieldType("0");//都是文本类型
				tepExt.setParent(extInfo);
				tepExt.setFieldDesc(dict.getDescription());
				tepExt.setSort(sort);
				sort=sort+10;
				super.save(tepExt);
			}
		}



	}


}