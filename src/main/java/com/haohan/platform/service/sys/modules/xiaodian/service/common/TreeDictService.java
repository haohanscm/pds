package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.TreeDictDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.TreeDict;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 树形字典Service
 * @author haohan
 * @version 2017-12-11
 */
@Service
@Transactional(readOnly = true)
public class TreeDictService extends TreeService<TreeDictDao, TreeDict> {

	@Autowired
	private TreeDictDao  treeDictDao;

	public TreeDict get(String id) {
		return super.get(id);
	}


	public List<TreeDict> findList(TreeDict treeDict) {
		if (StringUtils.isNotBlank(treeDict.getParentIds())){
			treeDict.setParentIds(","+treeDict.getParentIds()+",");
		}
		return super.findList(treeDict);
	}

	public List<TreeDict> findChildren(String parentId){
		TreeDict dict = new TreeDict();
		if (StringUtils.isNotEmpty(parentId)){
			dict.setParent(get(parentId));
		}
		return super.findList(dict);
	}

	public TreeDict findByCodeAndType(String code,String type){
		TreeDict treeDict = new TreeDict();
		treeDict.setCode(code);
		treeDict.setType(type);
		List<TreeDict> treeDictList = super.findList(treeDict);
		return CollectionUtils.isEmpty(treeDictList)?null:treeDictList.get(0);
	}
	
	@Transactional(readOnly = false)
	public void save(TreeDict treeDict) {
		super.save(treeDict);
	}
	
	@Transactional(readOnly = false)
	public void delete(TreeDict treeDict) {
		super.delete(treeDict);
	}


	public List<TreeDict> findChildrenDicts(String paerentIds){

		return treeDictDao.findByParentIdsLike(new TreeDict(paerentIds));
	}


}