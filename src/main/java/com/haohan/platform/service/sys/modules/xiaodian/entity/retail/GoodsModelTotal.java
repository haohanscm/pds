package com.haohan.platform.service.sys.modules.xiaodian.entity.retail;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 零售商品规格名称管理Entity
 * @author haohan
 * @version 2018-09-27
 */
public class GoodsModelTotal extends DataEntity<GoodsModelTotal> {
	
	private static final long serialVersionUID = 1L;
	private String goodsId;		// 商品id
	private String modelName;		// 规格名称
	private String modelId;		// 规格ID
	private String subModelId;		// 子规格ID
	private String subModelName;		// 子规格名称
	
	public GoodsModelTotal() {
		super();
	}

	public GoodsModelTotal(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="规格名称长度必须介于 0 和 64 之间")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Length(min=0, max=64, message="规格ID长度必须介于 0 和 64 之间")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Length(min=0, max=5, message="子规格ID长度必须介于 0 和 5 之间")
	public String getSubModelId() {
		return subModelId;
	}

	public void setSubModelId(String subModelId) {
		this.subModelId = subModelId;
	}
	
	@Length(min=0, max=64, message="子规格名称长度必须介于 0 和 64 之间")
	public String getSubModelName() {
		return subModelName;
	}

	public void setSubModelName(String subModelName) {
		this.subModelName = subModelName;
	}
	
}