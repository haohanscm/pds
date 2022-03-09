package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.DeliverServiceAreaDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliverManManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliverServiceArea;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params.ServerAreaResp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配送员服务区域Service
 * @author yu.shen
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class DeliverServiceAreaService extends CrudService<DeliverServiceAreaDao, DeliverServiceArea> {

	public DeliverServiceArea get(String id) {
		return super.get(id);
	}
	
	public List<DeliverServiceArea> findList(DeliverServiceArea deliverServiceArea) {
		return super.findList(deliverServiceArea);
	}
	
	public Page<DeliverServiceArea> findPage(Page<DeliverServiceArea> page, DeliverServiceArea deliverServiceArea) {
		return super.findPage(page, deliverServiceArea);
	}

	@Transactional(readOnly = false)
	public void save(DeliverServiceArea deliverServiceArea) {
		super.save(deliverServiceArea);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeliverServiceArea deliverServiceArea) {
		super.delete(deliverServiceArea);
	}


}