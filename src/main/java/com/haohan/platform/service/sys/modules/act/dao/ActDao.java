/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.modules.act.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.act.entity.Act;

/**
 * 审批DAO接口
 *
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

    public int updateProcInsIdByBusinessId(Act act);

}
