package com.haohan.platform.service.sys.modules.xiaodian.service.pay;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.pay.ChannelRateManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.ChannelRateManage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付渠道费率管理Service
 * @author haohan
 * @version 2017-12-11
 */
@Service
@Transactional(readOnly = true)
public class ChannelRateManageService extends CrudService<ChannelRateManageDao, ChannelRateManage> {

	public ChannelRateManage get(String id) {
		return super.get(id);
	}
	
	public List<ChannelRateManage> findList(ChannelRateManage channelRateManage) {
		return super.findList(channelRateManage);
	}
	
	public Page<ChannelRateManage> findPage(Page<ChannelRateManage> page, ChannelRateManage channelRateManage) {
		return super.findPage(page, channelRateManage);
	}
	
	@Transactional(readOnly = false)
	public void save(ChannelRateManage channelRateManage) {
		super.save(channelRateManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(ChannelRateManage channelRateManage) {
		super.delete(channelRateManage);
	}


    public ChannelRateManage fetchByChannel(String payInfoId, String channel) {
		ChannelRateManage rateManage = new ChannelRateManage();
		rateManage.setPayInfoId(payInfoId);
		rateManage.setChannel(channel);
		List<ChannelRateManage>  channelRateManageList = findList(rateManage);
		return CollectionUtils.isEmpty(channelRateManageList)?null:channelRateManageList.get(0);
    }

	public List<ChannelRateManage> fetchByPayInfoId(String payInfoId) {
		ChannelRateManage rateManage = new ChannelRateManage();
		rateManage.setPayInfoId(payInfoId);
		List<ChannelRateManage>  channelRateManageList = findList(rateManage);
		return CollectionUtils.isNotEmpty(channelRateManageList)?channelRateManageList:null;
	}
}