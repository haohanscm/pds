package com.haohan.platform.service.sys.modules.pss.api.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssPageApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssWarehouseService;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;
import com.haohan.platform.service.sys.modules.pss.service.info.WarehouseService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shenyu
 * @create 2018/11/27
 */
@Service
public class PssWarehouseServiceImpl implements IPssWarehouseService {
    @Resource
    private WarehouseService warehouseService;

    @Override
    public BaseResp findWarehousePage(PssPageApiReq pageApiReq,Page page) {
        BaseList<PssWarehouse> baseList = new BaseList<>();

        PssWarehouse warehouse = new PssWarehouse();
        warehouse.setMerchantId(pageApiReq.getMerchantId());
        warehouseService.findPage(page,warehouse);
        if (CollectionUtils.isEmpty(page.getList())){
            baseList.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseList;
        }

        baseList.setTotalPage(page.getTotalPage());
        baseList.setCurPage(page.getPageNo());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(page.getList());
        baseList.success();
        return baseList;
    }

    @Override
    public BaseResp saveWarehouse(PssWarehouse warehouse) {
        BaseResp baseResp = BaseResp.newError();
        // 默认启用
        String status = ICommonConstant.IsEnable.disable.getCode();
        if(!StringUtils.equals(warehouse.getStatus(),status)){
            status = ICommonConstant.IsEnable.enable.getCode();
        }
        warehouse.setStatus(status);
        warehouseService.save(warehouse);
        baseResp.success();
        return baseResp;
    }


    @Override
    public BaseResp delWarehouse(String id) {
        BaseResp baseResp = BaseResp.newError();
        PssWarehouse warehouse = warehouseService.get(id);
        if (null == warehouse){
            baseResp.setMsg("数据不存在");
            return baseResp;
        }
        warehouseService.delete(warehouse);
        baseResp.success();
        return baseResp;
    }

}
