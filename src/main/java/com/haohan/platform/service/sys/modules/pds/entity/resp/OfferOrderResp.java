package com.haohan.platform.service.sys.modules.pds.entity.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商 报价单查看 返回参数
 * Created by dy on 2018/10/26.
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferOrderResp extends OfferOrder implements Serializable {

    private String goodsId;        // 商品ID
    private String goodsImg;        // 商品图片
    private String goodsName;        // 商品名称
    private String goodsModel;        // 商品规格
    private String unit;        // 单位
    private Date buyTime;        // 采购日期
    private Integer realBuyNum;        // 实际采购数量
    private Integer needBuyNum;        // 需求采购数量
    private Integer limitSupplyNum;        // 最小供应量

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getRealBuyNum() {
        return realBuyNum;
    }

    public void setRealBuyNum(Integer realBuyNum) {
        this.realBuyNum = realBuyNum;
    }

    public Integer getNeedBuyNum() {
        return needBuyNum;
    }

    public void setNeedBuyNum(Integer needBuyNum) {
        this.needBuyNum = needBuyNum;
    }

    public Integer getLimitSupplyNum() {
        return limitSupplyNum;
    }

    public void setLimitSupplyNum(Integer limitSupplyNum) {
        this.limitSupplyNum = limitSupplyNum;
    }

}
