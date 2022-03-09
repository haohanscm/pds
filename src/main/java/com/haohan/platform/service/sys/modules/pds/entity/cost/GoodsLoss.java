package com.haohan.platform.service.sys.modules.pds.entity.cost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 商品损耗管理Entity
 *
 * @author haohan
 * @version 2018-12-03
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsLoss extends DataEntity<GoodsLoss> {

    private static final long serialVersionUID = 1L;
    private String pmId;        // 平台商家ID
    private String goodsId;        // 商品ID
    private String goodsModel;        // 商品规格
    private BigDecimal lossPercent;        // 损耗百分比
    private Integer lossLimit;        // 损耗阀值
    private String lossExplain;        // 损耗说明

    private String pmName;        // 平台商家名称

    public GoodsLoss() {
        super();
    }

    public GoodsLoss(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "平台商家ID长度必须介于 0 和 64 之间")
    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    @Length(min = 0, max = 64, message = "商品ID长度必须介于 0 和 64 之间")
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Length(min = 0, max = 64, message = "商品规格长度必须介于 0 和 64 之间")
    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public BigDecimal getLossPercent() {
        return lossPercent;
    }

    public void setLossPercent(BigDecimal lossPercent) {
        this.lossPercent = lossPercent;
    }

    public Integer getLossLimit() {
        return lossLimit;
    }

    public void setLossLimit(Integer lossLimit) {
        this.lossLimit = lossLimit;
    }

    @Length(min = 0, max = 64, message = "损耗说明长度必须介于 0 和 64 之间")
    public String getLossExplain() {
        return lossExplain;
    }

    public void setLossExplain(String lossExplain) {
        this.lossExplain = lossExplain;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }
}