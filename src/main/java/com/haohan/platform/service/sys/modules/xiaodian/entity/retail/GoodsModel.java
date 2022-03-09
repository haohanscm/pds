package com.haohan.platform.service.sys.modules.xiaodian.entity.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 商品规格管理Entity
 * @author haohan
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord", "costPrice"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsModel extends DataEntity<GoodsModel> {
	
	private static final long serialVersionUID = 1L;
	private String goodsId;		// 商品id
	private BigDecimal modelPrice;		// 规格价格
	private String modelName;		// 规格名称 拼接
	private String modelUnit;		// 规格单位
	private Integer modelStorage;		// 规格库存
	private String modelUrl;		// 规格商品图片地址
	private String modelInfo;		// 扩展信息
	private String model;//规格组合ex:31,32
	private String itemsId;//即速应用规格ID
	private String modelCode;//编码  扫码条码
	private BigDecimal virtualPrice;//虚拟价格
	private String goodsModelSn;   // 商品规格唯一编号 sku唯一
    private BigDecimal costPrice;		// 参考成本价
	private BigDecimal weight;   // 重量 单位 kg
    private BigDecimal volume;  // 体积 单位 立方米
    private String modelGeneralSn;   // 商品规格通用编号/公共商品库sku编号
    private String thirdModelSn;  // 第三方规格编号 即速商品id

    // 查询条件
    private String shopId;
    private String goodsCategoryId;  // 分类id
    private String goodsSn; // 商品编号
    private String goodsName; // 商品名称

    private BigDecimal purchasePrice;

	public GoodsModel() {
		super();
	}

	public GoodsModel(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	public BigDecimal getModelPrice() {
		return modelPrice;
	}

	public void setModelPrice(BigDecimal modelPrice) {
		this.modelPrice = modelPrice;
	}
	
	@Length(min=0, max=64, message="规格名称长度必须介于 0 和 64 之间")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Length(min=0, max=64, message="规格单位长度必须介于 0 和 64 之间")
	public String getModelUnit() {
		return modelUnit;
	}

	public void setModelUnit(String modelUnit) {
		this.modelUnit = modelUnit;
	}
	
	public Integer getModelStorage() {
		return modelStorage;
	}

	public void setModelStorage(Integer modelStorage) {
		this.modelStorage = modelStorage;
	}
	
	@Length(min=0, max=500, message="规格商品图片地址长度必须介于 0 和 500 之间")
	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}
	
	public String getModelInfo() {
		return modelInfo;
	}

	public void setModelInfo(String modelInfo) {
		this.modelInfo = modelInfo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getItemsId() {
		return itemsId;
	}

	public void setItemsId(String itemsId) {
		this.itemsId = itemsId;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

    public BigDecimal getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(BigDecimal virtualPrice) {
        this.virtualPrice = virtualPrice;
    }

    public String getGoodsModelSn() {
        return goodsModelSn;
    }

    public void setGoodsModelSn(String goodsModelSn) {
        this.goodsModelSn = goodsModelSn;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getModelGeneralSn() {
        return modelGeneralSn;
    }

    public void setModelGeneralSn(String modelGeneralSn) {
        this.modelGeneralSn = modelGeneralSn;
    }

    public String getThirdModelSn() {
        return thirdModelSn;
    }

    public void setThirdModelSn(String thirdModelSn) {
        this.thirdModelSn = thirdModelSn;
    }
}