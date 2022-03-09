package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2019/4/10
 */
public class PdsSummaryOrderApiReq {
    private String goodsId;        // 商品ID
    private String goodsCategoryId;    //商品分类
    private String goodsImg;        // 商品图片
    private String goodsName;        // 商品名称
    private String goodsModel;        // 商品规格
    private String unit;        // 单位
    private String buySeq;        //采购批次

    private BigDecimal marketPrice;        // 市场价
    private BigDecimal needBuyNum;        // 需求采购数量
    private Date buyTime;        // 采购日期
    private Date deliveryTime;        // 送货日期

}
