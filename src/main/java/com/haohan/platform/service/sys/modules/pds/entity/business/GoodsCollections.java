package com.haohan.platform.service.sys.modules.pds.entity.business;

import org.hibernate.validator.constraints.Length;

import com.haohan.platform.service.sys.common.persistence.DataEntity;

/**
 * 商品收藏Entity
 * @author yu
 * @version 2018-12-13
 */
public class GoodsCollections extends DataEntity<GoodsCollections> {
	
	private static final long serialVersionUID = 1L;
	private String pmId;		// 平台商家
	private String uid;		// 通行证ID
	private String goodsId;		// 商品ID
	private String modelId;		// 规格ID

	//自定义分页查询参数
	private Integer pageStart;
	private Integer pageSize;
	private String buyerId;
    /**
     *  商品上下架状态 yes_no
     */
    private String queryStatus;
    private String buyerMerchantId;

	public GoodsCollections() {
		super();
	}

	public GoodsCollections(String id){
		super(id);
	}

	@Length(min=0, max=64, message="平台商家长度必须介于 0 和 64 之间")
	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}
	
	@Length(min=0, max=64, message="通行证ID长度必须介于 0 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=0, max=64, message="商品ID长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="规格ID长度必须介于 0 和 64 之间")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

    public String getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

    public String getBuyerMerchantId() {
        return buyerMerchantId;
    }

    public void setBuyerMerchantId(String buyerMerchantId) {
        this.buyerMerchantId = buyerMerchantId;
    }
}