package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ProductParams;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.RetailApiService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by dy on 2018/6/22.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/retail")
public class RetailApiCtrl extends BaseController{


    @Autowired
    private RetailApiService retailApiService;


    /**
     * 获取零售店铺商品分类列表
     * @param request 必需参数 shopId
     * @return
     */
    @RequestMapping(value = "fetchGoodsCategoryList")
    @ResponseBody
    public String fetchGoodsCategoryList(HttpServletRequest request){
        BaseResp resp = BaseResp.newError();
        try {
            // 参数解密
            ProductParams params = CommonUtils.mapToBean(getRequestParameters(request), new ProductParams());
            String shopId = params.getShopId();
            if(StringUtils.isEmpty(shopId)){
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }

            ArrayList<Map<String, String>> list = retailApiService.fetchGoodsCategoryList(shopId);
            if (list.size() > 0) {
                resp.setExt(list);
                resp.success();
            } else {
                resp.setMsg("找不到对应商品分类信息");
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.error();
        }finally {
            return resp.toJson();
        }
    }

    /**
     * 根据商品分类 获取零售店铺商品列表
     * @param request 必需参数 categoryId
     * @return
     */
    @RequestMapping(value = "fetchGoodsList")
    @ResponseBody
    public String fetchGoodsList(HttpServletRequest request){
        BaseResp resp = BaseResp.newError();
        try {
            ProductParams params = CommonUtils.mapToBean(getRequestParameters(request), new ProductParams());
            String categoryId = params.getCategoryId();
            if(StringUtils.isEmpty(categoryId)){
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }
            ArrayList<Map<String, String>> list = retailApiService.fetchGoodsList(categoryId);
            if (list.size() > 0) {
                resp.setExt(list);
                resp.success();
            } else {
                resp.setMsg("找不到对应商品信息");
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.error();
        }finally {
            return resp.toJson();
        }
    }

    /**
     * 根据goodsId 获取零售店铺商品信息
     * @param request 必需参数 goodsId
     * @return
     */
    @RequestMapping(value = "fetchGoodsInfo")
    @ResponseBody
    public String fetchGoodsInfo(HttpServletRequest request){
        BaseResp resp = BaseResp.newError();
        try {
            ProductParams params = CommonUtils.mapToBean(getRequestParameters(request), new ProductParams());
            String goodsId = params.getProductId();
            if(StringUtils.isEmpty(goodsId)){
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }
            LinkedHashMap<String,String> info = retailApiService.fetchGoodsInfo(goodsId);
            if(info==null){
                resp.putStatus(RespStatus.RECORD_NOT_FOUND);
                return resp.toJson();
            }
            resp.setExt(info);
            resp.success();
        }catch (Exception e){
            e.printStackTrace();
            resp.error();
        }finally {
            return resp.toJson();
        }
    }

}
