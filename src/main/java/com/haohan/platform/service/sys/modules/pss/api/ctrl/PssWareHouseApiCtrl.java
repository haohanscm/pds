package com.haohan.platform.service.sys.modules.pss.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssDeleteCommonApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssPageApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssWarehouseStockApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssWarehouseService;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseStock;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 仓库管理controller
 * @author shenyu
 * @create 2018/9/26
 */
@Controller
@RequestMapping(value = "${frontPath}/pss/api/wareHouse")
public class PssWareHouseApiCtrl extends BaseController {
    @Resource
    private IPssWarehouseService mercWarehouseServiceImpl;
    @Resource
    private IPssGoodsStorageService mercGoodsStorageServiceImpl;

    //仓库列表
    @RequestMapping(value = "findPage")
    @ResponseBody
    public BaseResp findPage(@Validated PssPageApiReq pageApiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        Integer pageNo = pageApiReq.getPageNo();
        Integer pageSize = pageApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        baseResp = mercWarehouseServiceImpl.findWarehousePage(pageApiReq,reqPage);
        return baseResp;
    }

    //新增仓库
    @RequestMapping(value = "save")
    @ResponseBody
    public BaseResp save(@Validated PssWarehouse warehouse, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        mercWarehouseServiceImpl.saveWarehouse(warehouse);
        baseResp.success();
        return baseResp;
    }

    //删除仓库
    @RequestMapping(value = "delete")
    @ResponseBody
    public BaseResp delete(@Validated PssDeleteCommonApiReq commonApiReq,BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        if (StringUtils.isEmpty(commonApiReq.getId())){
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        baseResp = mercWarehouseServiceImpl.delWarehouse(commonApiReq.getId());
        return baseResp;
    }

    //查询仓库库存详情列表
    @RequestMapping(value = "storageGoods")
    @ResponseBody
    public BaseResp findStorageGoodsPage(@Validated PssWarehouseStockApiReq stockApiReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        String warehouseId = stockApiReq.getWarehouseId();

        Integer pageNo = stockApiReq.getPageNo();
        Integer pageSize = stockApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        if (StringUtils.isEmpty(warehouseId)){
            baseResp.setMsg("缺少请求参数warehouseId");
            return baseResp;
        }

        WarehouseStock warehouseStock = new WarehouseStock();
        warehouseStock.setWarehouseId(warehouseId);

        try {
            baseResp = mercGoodsStorageServiceImpl.findGoodsStoragePage(stockApiReq,reqPage);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setMsg("系统出错");
        } finally {
            return baseResp;
        }
    }




}
