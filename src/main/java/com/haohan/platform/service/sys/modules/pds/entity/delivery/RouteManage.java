package com.haohan.platform.service.sys.modules.pds.entity.delivery;

import org.hibernate.validator.constraints.Length;

import com.haohan.platform.service.sys.common.persistence.DataEntity;

/**
 * 路线管理Entity
 * @author haohan
 * @version 2018-12-03
 */
public class RouteManage extends DataEntity<RouteManage> {
	
	private static final long serialVersionUID = 1L;
	private String pmId;		// 平台商家ID
	private String lineNo;		// 路线编号
	private String routeName;		// 路线名称
	private String destination;		// 目的地
	private String start;		// 出发地
	private String routePlanning;		// 路线规划
	private String pathPoint;		// 途径点
	private String roadCondition;		// 路况
	private String status;		// 状态

    private String pmName;		// 平台商家名称

	public RouteManage() {
		super();
	}

	public RouteManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="平台商家ID长度必须介于 0 和 64 之间")
	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}
	
	@Length(min=0, max=64, message="路线编号长度必须介于 0 和 64 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=0, max=64, message="路线名称长度必须介于 0 和 64 之间")
	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	@Length(min=0, max=64, message="目的地长度必须介于 0 和 64 之间")
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Length(min=0, max=64, message="出发地长度必须介于 0 和 64 之间")
	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
	
	@Length(min=0, max=64, message="路线规划长度必须介于 0 和 64 之间")
	public String getRoutePlanning() {
		return routePlanning;
	}

	public void setRoutePlanning(String routePlanning) {
		this.routePlanning = routePlanning;
	}
	
	@Length(min=0, max=64, message="途径点长度必须介于 0 和 64 之间")
	public String getPathPoint() {
		return pathPoint;
	}

	public void setPathPoint(String pathPoint) {
		this.pathPoint = pathPoint;
	}
	
	@Length(min=0, max=64, message="路况长度必须介于 0 和 64 之间")
	public String getRoadCondition() {
		return roadCondition;
	}

	public void setRoadCondition(String roadCondition) {
		this.roadCondition = roadCondition;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }
}