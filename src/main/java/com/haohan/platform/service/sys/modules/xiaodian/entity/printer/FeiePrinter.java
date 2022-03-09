package com.haohan.platform.service.sys.modules.xiaodian.entity.printer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 飞鹅打印机管理Entity
 * @author haohan
 * @version 2018-08-02
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeiePrinter extends DataEntity<FeiePrinter> {
	
	private static final long serialVersionUID = 1L;
	private String shopId;		// 店铺ID
	private String merchantId;		// 商家ID
	private String printerType;		// 打印机类型  printer_type
	private String printerSn;		// 打印机编号
	private String printerKey;		// 打印机秘钥
	private String printerName;		// 打印机名
	private String templateType;	//模板类型
	private String template;		// 打印模板,小票样式  json
	private String status;		// 云打印状态,是否添加至飞鹅云,0 -否 1-是
	private String useable;     // 打印机是否启用,0 -否 1-是
    private String category;    // 可打印分类  对应菜品/商品的分类名称  暂未启用
    private String times;       // 打印次数
	private String shopName;		// 店铺名称
	private String merchantName;		// 商家名称

    // 删除部分不展示属性
    public void delProperties(){
        this.remarks = null;
        this.printerKey = null;
        this.template = null;
    }

	public FeiePrinter() {
		super();
	}

	public FeiePrinter(String id){
		super(id);
	}

	@Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=32, message="打印机类型长度必须介于 0 和 32 之间")
	public String getPrinterType() {
		return printerType;
	}

	public void setPrinterType(String printerType) {
		this.printerType = printerType;
	}
	
	@Length(min=0, max=64, message="打印机编号长度必须介于 0 和 64 之间")
	public String getPrinterSn() {
		return printerSn;
	}

	public void setPrinterSn(String printerSn) {
		this.printerSn = printerSn;
	}
	
	@Length(min=0, max=64, message="打印机秘钥长度必须介于 0 和 64 之间")
	public String getPrinterKey() {
		return printerKey;
	}

	public void setPrinterKey(String printerKey) {
		this.printerKey = printerKey;
	}
	
	@Length(min=0, max=64, message="打印机名长度必须介于 0 和 64 之间")
	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	@Length(min=0, max=32, message="云打印状态长度必须介于 0 和 32 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    @Length(min=0, max=10, message="启用状态长度必须介于 0 和 10 之间")
    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Length(min=0, max=5, message="打印次数长度必须介于 0 和 5 之间")
    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
}