package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssWarehouseStockApiReq extends PssPageApiReq{
    private String warehouseId;		// 仓库ID
    private String barCode;		// 条形码
    @JsonProperty("goodsModelId")
    private String goodsCode;		// 商品编码
    private String goodsName;		// 商品名称
    private String unit;		// 单位
    private String attr;		// 规格
    private Integer stockNum;		// 库存数量
    private BigDecimal price;		// 单价
    private BigDecimal amount;		// 金额
    private String supplierId;		// 供应商ID			待定(删除)
    private Date instockTime;		// 入库时间
    private Date lastInventoryTime;		// 上次盘点时间

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Date getInstockTime() {
        return instockTime;
    }

    public void setInstockTime(Date instockTime) {
        this.instockTime = instockTime;
    }

    public Date getLastInventoryTime() {
        return lastInventoryTime;
    }

    public void setLastInventoryTime(Date lastInventoryTime) {
        this.lastInventoryTime = lastInventoryTime;
    }
}
