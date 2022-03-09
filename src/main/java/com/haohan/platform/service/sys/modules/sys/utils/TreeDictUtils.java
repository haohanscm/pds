package com.haohan.platform.service.sys.modules.sys.utils;

import com.haohan.platform.service.sys.common.utils.SpringContextHolder;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.TreeDictDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.TreeDict;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author shenyu
 * @create 2018/8/21
 */
public class TreeDictUtils {
    private static TreeDictDao treeDictDao = SpringContextHolder.getBean(TreeDictDao.class);

    public static String getTreeDictName(String type,String code){
        if(StringUtils.isEmpty(code)){
            return "";
        }
        TreeDict treeDict = new TreeDict();
        treeDict.setType(type);
        treeDict.setCode(code);
        List<TreeDict> list = treeDictDao.findList(treeDict);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0).getName();
        }else {
            return "";
        }

    }


}
