package com.haohan.platform.service.sys.modules.pss.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssStockInventoryApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssWarehouseStockAdjustApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssWarehouseStockApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssStorageInventoryService;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseStock;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseStockService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 商品库存盘点
 * @author shenyu
 * @create 2018/10/16
 */
@Controller
@RequestMapping(value = "${frontPath}/pss/api/storage")
public class PssStorageInventoryApiCtrl extends BaseController {
    @Resource
    private WarehouseStockService warehouseStockService;
    @Resource
    private IPssGoodsStorageService mercGoodsStorageService;
    @Resource
    private IPssStorageInventoryService mercGoodsStockInventoryService;

    //商品库存列表(分页)
    @RequestMapping(value = "findPage")
    @ResponseBody
    public BaseResp findPage(@Validated PssWarehouseStockApiReq stockApiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        Integer pageNo = stockApiReq.getPageNo();
        Integer pageSize = stockApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);
        try {
            baseResp = mercGoodsStorageService.findGoodsStoragePage(stockApiReq,reqPage);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setMsg("系统出错了");
        } finally {
            return baseResp;
        }
    }


    //数量确认(弃用)
    @RequestMapping(value = "confirm")
    @ResponseBody
    public BaseResp confirm(String merchantId, String warehouseStockId, BigDecimal stockNum, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        if (StringUtils.isEmpty(warehouseStockId) || null == stockNum){
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        baseResp = mercGoodsStockInventoryService.confirm(merchantId,warehouseStockId,stockNum);
        return baseResp;
    }


    //数量调整
    @RequestMapping(value = "adjustNum")
    @ResponseBody
    public BaseResp editStockNum(@Validated PssWarehouseStockAdjustApiReq adjustApiReq,BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        String stockId = adjustApiReq.getWarehouseStockId();
        WarehouseStock warehouseStock = warehouseStockService.get(stockId);
        if (null == warehouseStock){
            baseResp.setMsg("请求数据有误");
            return baseResp;
        }
        //记录库存变更
        baseResp = mercGoodsStockInventoryService.addInventoryRecord(stockId,warehouseStock.getStockNum(),adjustApiReq.getStockNum(),adjustApiReq.getMerchantId());
        if (baseResp.isSuccess()){
            //变更库存数量
            mercGoodsStorageService.adjustNum(stockId,adjustApiReq.getStockNum());
        }
        return baseResp;
    }


    //盘点记录
    @RequestMapping(value = "inventory/history")
    @ResponseBody
    public BaseResp findDetailPage(@Validated PssStockInventoryApiReq inventoryApiReq,BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        Integer pageNo = inventoryApiReq.getPageNo();
        Integer pageSize = inventoryApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        baseResp = mercGoodsStockInventoryService.inventoryRecordPage(inventoryApiReq.getWarehouseStockId(),reqPage);
        return baseResp;
    }



}
