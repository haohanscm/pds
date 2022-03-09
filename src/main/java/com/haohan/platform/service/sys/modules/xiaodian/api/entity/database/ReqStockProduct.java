package com.haohan.platform.service.sys.modules.xiaodian.api.entity.database;


import java.io.Serializable;

/**
 * 公共商品库 库存商品sku 请求参数
 * Created by dy on 2018/10/17.
 */
public class ReqStockProduct implements Serializable {

    private int pageNo;
    private int pageSize;

    private String spuId;		// 标准商品id
    private String stockGoodsId;		// 库存商品id
    private String stockGoodsSn;		// 商品唯一编号
    private String goodsName;		// 库存商品名称
    private String scanCode;		// 扫码条码

//    private String attrNameIds;		// 规格属性名id集
//    private String attrValueIds;		// 规格属性值id集
//    private String salePrice;		// 售价
//    private String stock;		// 库存
//    private String unit;		// 单位
//    private String attrDetail;		// 规格详情,拼接所有属性值
//    private String attrPhoto;		// 规格图片
//    private Integer sort;		// 排序

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getStockGoodsId() {
        return stockGoodsId;
    }

    public void setStockGoodsId(String stockGoodsId) {
        this.stockGoodsId = stockGoodsId;
    }

    public String getStockGoodsSn() {
        return stockGoodsSn;
    }

    public void setStockGoodsSn(String stockGoodsSn) {
        this.stockGoodsSn = stockGoodsSn;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }
}
