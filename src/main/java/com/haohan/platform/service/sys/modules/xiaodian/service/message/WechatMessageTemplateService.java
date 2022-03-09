package com.haohan.platform.service.sys.modules.xiaodian.service.message;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.message.WechatMessageDetailDao;
import com.haohan.platform.service.sys.modules.xiaodian.dao.message.WechatMessageTemplateDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.message.WechatMessageDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.message.WechatMessageTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息模板管理Service
 * @author haohan
 * @version 2018-04-26
 */
@Service
@Transactional(readOnly = true)
public class WechatMessageTemplateService extends CrudService<WechatMessageTemplateDao, WechatMessageTemplate> {

	@Autowired
	private WechatMessageDetailDao wechatMessageDetailDao;
	
	public WechatMessageTemplate get(String id) {
		WechatMessageTemplate wechatMessageTemplate = super.get(id);
		wechatMessageTemplate.setWechatMessageDetailList(wechatMessageDetailDao.findList(new WechatMessageDetail(wechatMessageTemplate)));
		return wechatMessageTemplate;
	}
	
	public List<WechatMessageTemplate> findList(WechatMessageTemplate wechatMessageTemplate) {
		return super.findList(wechatMessageTemplate);
	}
	
	public Page<WechatMessageTemplate> findPage(Page<WechatMessageTemplate> page, WechatMessageTemplate wechatMessageTemplate) {
		return super.findPage(page, wechatMessageTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(WechatMessageTemplate wechatMessageTemplate) {
		super.save(wechatMessageTemplate);
		for (WechatMessageDetail wechatMessageDetail : wechatMessageTemplate.getWechatMessageDetailList()){
			if (wechatMessageDetail.getId() == null){
				continue;
			}
			if (WechatMessageDetail.DEL_FLAG_NORMAL.equals(wechatMessageDetail.getDelFlag())){
				if (StringUtils.isBlank(wechatMessageDetail.getId())){
					wechatMessageDetail.setMsgTemplate(wechatMessageTemplate);
					wechatMessageDetail.preInsert();
					wechatMessageDetailDao.insert(wechatMessageDetail);
				}else{
					wechatMessageDetail.preUpdate();
					wechatMessageDetailDao.update(wechatMessageDetail);
				}
			}else{
				wechatMessageDetailDao.delete(wechatMessageDetail);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WechatMessageTemplate wechatMessageTemplate) {
		super.delete(wechatMessageTemplate);
		wechatMessageDetailDao.delete(new WechatMessageDetail(wechatMessageTemplate));
	}

    public WechatMessageTemplate fetchByAppIdAndMsgType(String appId, String msgType) {

		WechatMessageTemplate wechatMessageTemplate = new WechatMessageTemplate();
		wechatMessageTemplate.setAppId(appId);
		wechatMessageTemplate.setMsgType(msgType);

		List<WechatMessageTemplate> templateList = findList(wechatMessageTemplate);

		wechatMessageTemplate = CollectionUtils.isNotEmpty(templateList) ? templateList.get(0) : null;

		if (null != wechatMessageTemplate) {
			return super.get(wechatMessageTemplate.getId());
		}
	   return null;
    }
}