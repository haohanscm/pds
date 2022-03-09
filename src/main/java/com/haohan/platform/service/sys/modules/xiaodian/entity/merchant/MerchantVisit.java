package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 商家拜访记录Entity
 * @author haohan
 * @version 2018-04-07
 */
public class MerchantVisit extends DataEntity<MerchantVisit> {
	
	private static final long serialVersionUID = 1L;
	private MerchantDatabase merchantDatabase;		// 商家资料ID
	private String visitAddress;		// 拜访地址
	private String contact;		// 联系人
	private String phoneNumber;		// 联系电话
	private Date visitTime;		// 拜访时间
	private String visitContent;		// 拜访内容
	private String visitStep;		// 进展阶段
	private String visitPic;		// 拜访照片
	private User user;		// 拜访人员
	private Date beginVisitTime;		// 开始 拜访时间
	private Date endVisitTime;		// 结束 拜访时间
	
	public MerchantVisit() {
		super();
	}

	public MerchantVisit(String id){
		super(id);
	}

	public MerchantDatabase getMerchantDatabase() {
	    // 导入Excel时防止出现NPE异常
        if (this.merchantDatabase == null) {
            this.merchantDatabase = new MerchantDatabase();
        }
        return this.merchantDatabase;
    }

	public void setMerchantDatabase(MerchantDatabase merchantDatabase) {
		this.merchantDatabase = merchantDatabase;
	}

	@Length(min=0, max=200, message="拜访地址长度必须介于 0 和 200 之间")
	public String getVisitAddress() {
		return visitAddress;
	}

	public void setVisitAddress(String visitAddress) {
		this.visitAddress = visitAddress;
	}
	
	@Length(min=0, max=64, message="联系人长度必须介于 0 和 64 之间")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Length(min=0, max=64, message="联系电话长度必须介于 0 和 64 之间")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	
	@Length(min=0, max=500, message="拜访内容长度必须介于 0 和 500 之间")
	public String getVisitContent() {
		return visitContent;
	}

	public void setVisitContent(String visitContent) {
		this.visitContent = visitContent;
	}
	
	@Length(min=0, max=64, message="进展阶段长度必须介于 0 和 64 之间")
	public String getVisitStep() {
		return visitStep;
	}

	public void setVisitStep(String visitStep) {
		this.visitStep = visitStep;
	}
	
	@Length(min=0, max=200, message="拜访照片长度必须介于 0 和 200 之间")
	public String getVisitPic() {
		return visitPic;
	}

	public void setVisitPic(String visitPic) {
		this.visitPic = visitPic;
	}
	
	public User getUser() {
        // 导入Excel时防止出现NPE异常
        if (this.user == null) {
            this.user = new User();
        }
        return this.user;
    }

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getBeginVisitTime() {
		return beginVisitTime;
	}

	public void setBeginVisitTime(Date beginVisitTime) {
		this.beginVisitTime = beginVisitTime;
	}
	
	public Date getEndVisitTime() {
		return endVisitTime;
	}

	public void setEndVisitTime(Date endVisitTime) {
		this.endVisitTime = endVisitTime;
	}
		
}