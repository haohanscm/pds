package com.haohan.platform.service.sys.modules.xiaodian.service;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.IndustryDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.Industry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 行业分类管理Service
 * @author haohan
 * @version 2017-08-05
 */
@Service
@Transactional(readOnly = true)
public class IndustryService extends TreeService<IndustryDao, Industry> {

	public Industry get(String id) {
		return super.get(id);
	}
	
	public List<Industry> findList(Industry industry) {
		if (StringUtils.isNotBlank(industry.getParentIds())){
			industry.setParentIds(","+industry.getParentIds()+",");
		}
		return super.findList(industry);
	}
	
	@Transactional(readOnly = false)
	public void save(Industry industry) {
		super.save(industry);
	}
	
	@Transactional(readOnly = false)
	public void delete(Industry industry) {
		super.delete(industry);
	}

	public Map<String,String> fetchNameMap(){
		return Collections3.extractToMap(findList(new Industry()),"id","name");
	}

	public Industry fetchByName(String name) {
        Industry industry = new Industry();
        industry.setName(name);
        List<Industry> list = dao.fetchByName(industry);
        // 有重复的情况下返回空
        return list.size() == 1 ? list.get(0) : null;
    }
	
}