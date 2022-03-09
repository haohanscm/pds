/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.modules.gen.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.gen.entity.GenTable;
import com.haohan.platform.service.sys.modules.gen.entity.GenTableColumn;

import java.util.List;

/**
 * 业务表字段DAO接口
 *
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenDataBaseDictDao extends CrudDao<GenTableColumn> {

    /**
     * 查询表列表
     *
     * @param genTable
     * @return
     */
    List<GenTable> findTableList(GenTable genTable);

    /**
     * 获取数据表字段
     *
     * @param genTable
     * @return
     */
    List<GenTableColumn> findTableColumnList(GenTable genTable);

    /**
     * 获取数据表主键
     *
     * @param genTable
     * @return
     */
    List<String> findTablePK(GenTable genTable);

}
