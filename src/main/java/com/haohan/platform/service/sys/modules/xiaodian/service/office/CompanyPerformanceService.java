package com.haohan.platform.service.sys.modules.xiaodian.service.office;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import com.haohan.platform.service.sys.modules.xiaodian.dao.office.CompanyPerformanceDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.office.CompanyPerformance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 绩效考核Service
 * @author haohan
 * @version 2018-05-22
 */
@Service
@Transactional(readOnly = true)
public class CompanyPerformanceService extends CrudService<CompanyPerformanceDao, CompanyPerformance> {

	public CompanyPerformance get(String id) {
		return super.get(id);
	}
	
	public List<CompanyPerformance> findList(CompanyPerformance companyPerformance) {
		return findListFilter(companyPerformance);
	}
	
	public Page<CompanyPerformance> findPage(Page<CompanyPerformance> page, CompanyPerformance companyPerformance) {
        companyPerformance.setPage(page);
		page.setList(findListFilter(companyPerformance));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyPerformance companyPerformance) {
		super.save(companyPerformance);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyPerformance companyPerformance) {
		super.delete(companyPerformance);
	}

	// 根据用户类型过滤列表显示数据
	private List<CompanyPerformance> findListFilter(CompanyPerformance companyPerformance){
        User user = companyPerformance.getCurrentUser();
        StringBuilder sqlString = new StringBuilder();
        // 用户类型 判断
        if (StringUtils.equals("1", user.getUserType())) {
            // 用户类型为系统管理员 查看所有信息 不做过滤
        } else if (StringUtils.equals("2", user.getUserType())) {
            // 用户类型为部门经理  查看部门的信息
            sqlString.append(" AND EXISTS (SELECT 1 FROM sys_office");
            sqlString.append(" WHERE (id = '" + user.getOffice().getId() + "'");
            sqlString.append(" OR parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%')");
            sqlString.append(" AND id=o4.id )");
        } else if (StringUtils.equals("3", user.getUserType())) {
            // 用户类型为普通用户  只查看个人的信息
            sqlString.append(" AND EXISTS (SELECT 1 FROM sys_user");
            sqlString.append(" WHERE id='" + user.getId() + "'");
            sqlString.append(" AND id=a.user_name )");
        } else {
            // 按角色数据权限过滤
            dataScopeFilter(companyPerformance, "dsf", "id=o4.id", "id=a.user_name");
        }
        companyPerformance.getSqlMap().put("dsf", sqlString.toString());
	    return dao.findList(companyPerformance);
    }
	
}