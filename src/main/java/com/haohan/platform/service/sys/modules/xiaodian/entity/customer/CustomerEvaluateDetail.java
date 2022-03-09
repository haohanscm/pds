package com.haohan.platform.service.sys.modules.xiaodian.entity.customer;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 客户服务评价明细Entity
 * @author haohan
 * @version 2018-04-22
 */
public class CustomerEvaluateDetail extends DataEntity<CustomerEvaluateDetail> {
	
	private static final long serialVersionUID = 1L;
	private String evaluateId;		// 评分ID
	private String evaluateName;		// 评分名称
	private String score;		// 分值
	private String evaluateType;		// 评分类型
	
	public CustomerEvaluateDetail() {
		super();
	}

	public CustomerEvaluateDetail(String id){
		super(id);
	}

	@Length(min=0, max=64, message="评分ID长度必须介于 0 和 64 之间")
	public String getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}
	
	@Length(min=0, max=64, message="评分名称长度必须介于 0 和 64 之间")
	public String getEvaluateName() {
		return evaluateName;
	}

	public void setEvaluateName(String evaluateName) {
		this.evaluateName = evaluateName;
	}
	
	@Length(min=0, max=200, message="分值长度必须介于 0 和 200 之间")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@Length(min=0, max=64, message="评分类型长度必须介于 0 和 64 之间")
	public String getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}
	
}