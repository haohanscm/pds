package com.haohan.platform.service.sys.modules.pss.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.ApiPssGoodsModelReq;
import com.haohan.platform.service.sys.modules.pss.api.impl.PssGoodsDatabaseServiceImpl;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseStockService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 进销存 商品获取
 * @author shenyu
 * @create 2018/10/11
 */
@Controller
@RequestMapping(value = "${frontPath}/pss/api/goods")
public class PssGoodsDatabaseApiCtrl {

    @Autowired
    private PssGoodsDatabaseServiceImpl pssGoodsDatabaseServiceImpl;
    @Resource
    private WarehouseStockService warehouseStockService;

    /**
     * 规格商品列表
     * goodsModelList
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsModelList")
    @ResponseBody
    public String goodsModelList(ApiPssGoodsModelReq apiPssGoodsModelReq, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        // 参数验证
            if(StringUtils.isEmpty(apiPssGoodsModelReq.getShopId())){
                resp.setMsg("缺少shopId");
                return resp.toJson();
            }
        // 请求参数
        GoodsModel goodsModel = new GoodsModel();
//        goods.setMerchantId(merchantId);
        goodsModel.setShopId(apiPssGoodsModelReq.getShopId());
        goodsModel.setGoodsCategoryId(apiPssGoodsModelReq.getGoodsCategoryId());
        goodsModel.setGoodsName(apiPssGoodsModelReq.getGoodsName());
        goodsModel.setGoodsModelSn(apiPssGoodsModelReq.getGoodsModelSn());
        goodsModel.setModelCode(apiPssGoodsModelReq.getModelCode());

        // 分页查询
        int pageNo = StringUtils.toInteger(apiPssGoodsModelReq.getPageNo());
        int pageSize = StringUtils.toInteger(apiPssGoodsModelReq.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 3 ? 3 : pageSize;

        ApiRespPage<GoodsModel> result = pssGoodsDatabaseServiceImpl.goodsModelList(goodsModel, pageNo, pageSize);
        if (Collections3.isEmpty(result.getList())) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }



}
