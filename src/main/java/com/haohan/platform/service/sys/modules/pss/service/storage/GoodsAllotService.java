package com.haohan.platform.service.sys.modules.pss.service.storage;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import com.haohan.platform.service.sys.modules.pss.dao.storage.GoodsAllotDao;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllot;
import com.haohan.platform.service.sys.modules.xiaodian.util.PssCommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品调拨Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class GoodsAllotService extends CrudService<GoodsAllotDao, GoodsAllot> {

	public GoodsAllot get(String id) {
		return super.get(id);
	}
	
	public List<GoodsAllot> findList(GoodsAllot goodsAllot) {
		return super.findList(goodsAllot);
	}
	
	public Page<GoodsAllot> findPage(Page<GoodsAllot> page, GoodsAllot goodsAllot) {
		return super.findPage(page, goodsAllot);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsAllot goodsAllot) {
		if (StringUtils.isEmpty(goodsAllot.getAllotNum())){
			String allotNum = PdsCommonUtil.incrIdByClass(GoodsAllot.class, IPdsConstant.WAREHOUSE_ALLOT_SN_PRE);
			goodsAllot.setAllotNum(allotNum);
		}
		super.save(goodsAllot);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsAllot goodsAllot) {
		super.delete(goodsAllot);
	}
	
}