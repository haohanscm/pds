package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.DeliverManManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliverManManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配送员管理Service
 * @author yu.shen
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class DeliverManManageService extends CrudService<DeliverManManageDao, DeliverManManage> {

    @Autowired
    @Lazy(true)
    private DeliverServiceAreaService deliverServiceAreaService;

	public DeliverManManage get(String id) {
		return super.get(id);
	}
	
	public List<DeliverManManage> findList(DeliverManManage deliverManManage) {
		return super.findList(deliverManManage);
	}
	
	public Page<DeliverManManage> findPage(Page<DeliverManManage> page, DeliverManManage deliverManManage) {
		return super.findPage(page, deliverManManage);
	}

	public List<DeliverManManage> fetchByUid(String uid){
	    DeliverManManage deliverManManage = new DeliverManManage();
	    deliverManManage.setUid(uid);
	    deliverManManage.setStatus(IMerchantConstant.available);
        return dao.findList(deliverManManage);
    }
	
	@Transactional(readOnly = false)
	public void save(DeliverManManage deliverManManage) {
		super.save(deliverManManage);
	}


}