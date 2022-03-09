package com.haohan.platform.service.sys.modules.xiaodian.entity.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 库存商品管理Entity
 * @author dy
 * @version 2018-10-20
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockKeepingUnit extends DataEntity<StockKeepingUnit> {
	
	private static final long serialVersionUID = 1L;
	private String spuId;		// 标准商品id
	private String attrNameIds;		// 规格属性名id集
	private String attrValueIds;		// 规格属性值id集
	private String goodsName;		// 商品名称
	private String stockGoodsSn;		// 商品唯一编号
	private BigDecimal salePrice;		// 售价
	private String stock;		// 库存
	private String unit;		// 单位
	private String attrDetail;		// 规格详情,拼接所有属性值
	private String scanCode;		// 扫码条码
	private String attrPhoto;		// 规格图片地址
	private Integer sort;		// 排序
	private BigDecimal weight;		// 重量;单位kg
	private BigDecimal volume;		// 体积;单位立方米

	// 查询结果
	private String attrInfo;  // sku属性信息  ex:   尺码:XL,颜色:蓝
    private String generalSn; // spu商品编号
	
	public StockKeepingUnit() {
		super();
	}

	public StockKeepingUnit(String id){
		super(id);
	}

	@Length(min=0, max=64, message="标准商品id长度必须介于 0 和 64 之间")
	public String getSpuId() {
		return spuId;
	}

	public void setSpuId(String spuId) {
		this.spuId = spuId;
	}
	
	@Length(min=0, max=500, message="规格属性名id集长度必须介于 0 和 500 之间")
	public String getAttrNameIds() {
		return attrNameIds;
	}

	public void setAttrNameIds(String attrNameIds) {
		this.attrNameIds = attrNameIds;
	}
	
	@Length(min=0, max=500, message="规格属性值id集长度必须介于 0 和 500 之间")
	public String getAttrValueIds() {
		return attrValueIds;
	}

	public void setAttrValueIds(String attrValueIds) {
		this.attrValueIds = attrValueIds;
	}
	
	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=64, message="商品唯一编号长度必须介于 0 和 64 之间")
	public String getStockGoodsSn() {
		return stockGoodsSn;
	}

	public void setStockGoodsSn(String stockGoodsSn) {
		this.stockGoodsSn = stockGoodsSn;
	}
	
	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	
	@Length(min=0, max=10, message="库存长度必须介于 0 和 10 之间")
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}
	
	@Length(min=0, max=10, message="单位长度必须介于 0 和 10 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=200, message="规格详情,拼接所有属性值长度必须介于 0 和 200 之间")
	public String getAttrDetail() {
		return attrDetail;
	}

	public void setAttrDetail(String attrDetail) {
		this.attrDetail = attrDetail;
	}
	
	@Length(min=0, max=20, message="扫码条码长度必须介于 0 和 20 之间")
	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}
	
	@Length(min=0, max=500, message="规格图片长度必须介于 0 和 500 之间")
	public String getAttrPhoto() {
		return attrPhoto;
	}

	public void setAttrPhoto(String attrPhoto) {
		this.attrPhoto = attrPhoto;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

    public String getAttrInfo() {
        return attrInfo;
    }

    public void setAttrInfo(String attrInfo) {
        this.attrInfo = attrInfo;
    }

    public String getGeneralSn() {
        return generalSn;
    }

    public void setGeneralSn(String generalSn) {
        this.generalSn = generalSn;
    }
}