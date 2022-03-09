package com.haohan.platform.service.sys.modules.xiaodian.entity.office;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.Office;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

/**
 * 绩效考核Entity
 * @author haohan
 * @version 2018-05-23
 */
public class CompanyPerformance extends DataEntity<CompanyPerformance> {
	
	private static final long serialVersionUID = 1L;
	private String evaluateTime;		// 考核时间
	private String evaluateWeek;		// 考核区间
	private Office office;		// 部门
	private String position;		// 岗位
	private User user;		// 被考核人姓名
	private String evaluateInfo;		// 考核项目 json串
	private String weekReport;		// 周报汇总
	private String superiorEvaluate;		// 上级点评
	private String evaluateLevel;		// 考核评级

	private Map<String,String> evaluateItems; // 考核项的键值对
	
	public CompanyPerformance() {
		super();
	}

	public CompanyPerformance(String id){
		super(id);
	}

	@Length(min=0, max=64, message="考核时间长度必须介于 0 和 64 之间")
	public String getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(String evaluateTime) {
		this.evaluateTime = evaluateTime;
	}
	
	@Length(min=0, max=64, message="考核区间长度必须介于 0 和 64 之间")
	public String getEvaluateWeek() {
		return evaluateWeek;
	}

	public void setEvaluateWeek(String evaluateWeek) {
		this.evaluateWeek = evaluateWeek;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=64, message="岗位长度必须介于 0 和 64 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEvaluateInfo() {
		return evaluateInfo;
	}

	public void setEvaluateInfo(String evaluateInfo) {
        // 对html实体字符转换
        this.evaluateInfo = StringEscapeUtils.unescapeHtml4(evaluateInfo);
	}

	public String getWeekReport() {
		return weekReport;
	}

	public void setWeekReport(String weekReport) {
		this.weekReport = weekReport;
	}

	@Length(min=0, max=2000, message="上级点评长度必须介于 0 和 2000 之间")
	public String getSuperiorEvaluate() {
		return superiorEvaluate;
	}

	public void setSuperiorEvaluate(String superiorEvaluate) {
		this.superiorEvaluate = StringEscapeUtils.unescapeHtml4(superiorEvaluate);
	}
	
	@Length(min=0, max=64, message="考核评级长度必须介于 0 和 64 之间")
	public String getEvaluateLevel() {
		return evaluateLevel;
	}

	public void setEvaluateLevel(String evaluateLevel) {
		this.evaluateLevel = evaluateLevel;
	}

	public Map<String,String> getEvaluateItems() {
		return evaluateItems;
	}

	public void setEvaluateItems(Map<String,String> evaluateItems) {
		this.evaluateItems = evaluateItems;
	}
}