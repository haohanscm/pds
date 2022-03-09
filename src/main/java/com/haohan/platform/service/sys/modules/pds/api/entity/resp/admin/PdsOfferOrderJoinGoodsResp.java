package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2019/1/5
 */
public class PdsOfferOrderJoinGoodsResp {
    private String pmId; // 平台商家id
    private String offerOrderId;        // 报价单号
    private String askOrderId;        // 询价单号
    private String goodsId;            //商品id
    private String goodsName;        //商品名称
    private Integer buyNum;        // 采购数量
    private String supplierId;        // 供应商
    private String supplierName;    //供应商名称
    private Integer supplyNum;        // 供应数量
    private Integer minSaleNum;        //起售数量
    private BigDecimal supplyPrice;        // 供应商报价
    private String supplyImg;        // 实物图片
    private String supplyDesc;        // 供应说明
    private String status;        // 状态
    private Date askPriceTime;        // 询价时间
    private Date offerPriceTime;        // 报价时间
    private Date dealTime;        //成交时间
    private BigDecimal dealPrice;    //成交单价
    private String shipStatus;    //发货状态
    private Date prepareDate;    //备货日期
    private String buySeq;        //采购批次
}
