package com.haohan.platform.service.sys.modules.xiaodian.dao.database;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StandardProductUnit;

import java.util.List;

/**
 * 标准商品库管理DAO接口
 * @author dy
 * @version 2018-10-17
 */
@MyBatisDao
public interface StandardProductUnitDao extends CrudDao<StandardProductUnit> {

    /**
     * 查询结果 带 分类名称categoryName
     * @param standardProductUnit
     * @return
     */
    List<StandardProductUnit> findJoinList(StandardProductUnit standardProductUnit);

    /**
     * 查询结果 带 分类名称categoryName
     * @param standardProductUnit
     * @return
     */
    StandardProductUnit getWith(StandardProductUnit standardProductUnit);

    /**
     * 根据 spuIds  查询
     * @param spu id: spu列表  ","分隔
     * @return
     */
    List<StandardProductUnit> findListByIds(StandardProductUnit spu);
}