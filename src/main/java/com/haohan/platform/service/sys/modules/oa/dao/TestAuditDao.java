/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.modules.oa.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.oa.entity.TestAudit;

/**
 * 审批DAO接口
 *
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface TestAuditDao extends CrudDao<TestAudit> {

    public TestAudit getByProcInsId(String procInsId);

    public int updateInsId(TestAudit testAudit);

    public int updateHrText(TestAudit testAudit);

    public int updateLeadText(TestAudit testAudit);

    public int updateMainLeadText(TestAudit testAudit);

}
