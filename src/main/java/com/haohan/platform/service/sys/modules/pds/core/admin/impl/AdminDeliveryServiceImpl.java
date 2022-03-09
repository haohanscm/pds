package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsTruckApiReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminDeliveryService;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;
import com.haohan.platform.service.sys.modules.pds.service.delivery.TruckManageService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2019/2/14
 */
@Service
public class AdminDeliveryServiceImpl implements IPdsAdminDeliveryService {
    @Autowired
    private TruckManageService truckManageService;
    @Autowired
    private MerchantEmployeeService merchantEmployeeService;

    /**
     * 获取车辆列表
     *
     * @return
     */
    @Override
    public BaseResp truckList(String pmId, BaseResp baseResp) {
        TruckManage truckManage = new TruckManage();
        truckManage.setPmId(pmId);
        List<TruckManage> truckList = truckManageService.findEmptyEnableList(truckManage);

        if (CollectionUtils.isEmpty(truckList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(truckList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    /**
     * 添加车辆
     *
     * @param apiReq
     * @return
     */
    @Override
    public BaseResp insertTruck(PdsTruckApiReq apiReq, BaseResp baseResp) {
        TruckManage truck = new TruckManage();
        String driverId = apiReq.getDriver();
        MerchantEmployee driver = merchantEmployeeService.get(driverId);
        if (null == driver) {
            baseResp.setMsg("司机信息有误");
            return baseResp;
        } else {
            BeanUtils.copyProperties(apiReq, truck);
            truck.setStatus(IPdsConstant.TruckStatus.empty.getCode());
            truckManageService.save(truck);
            baseResp.success();
            return baseResp;
        }
    }

    @Override
    public BaseResp updateTruck(PdsTruckApiReq apiReq, BaseResp baseResp) {
        TruckManage truckManage = truckManageService.get(apiReq.getId());
        if (null == truckManage) {
            baseResp.error();
            baseResp.setMsg("data not exist");
            return baseResp;
        } else {
            if (StringUtils.isNotBlank(apiReq.getTruckNo())) {
                truckManage.setTruckNo(apiReq.getTruckNo());
            }
            if (StringUtils.isNotBlank(apiReq.getTruckName())) {
                truckManage.setTruckName(apiReq.getTruckName());
            }
            if (StringUtils.isNotBlank(apiReq.getBrand())) {
                truckManage.setBrand(apiReq.getBrand());
            }
            if (StringUtils.isNotBlank(apiReq.getDriver())) {
                MerchantEmployee driver = merchantEmployeeService.get(apiReq.getDriver());
                if (null == driver) {
                    baseResp.setMsg("司机信息有误");
                    return baseResp;
                } else {
                    truckManage.setDriver(apiReq.getDriver());
                }
            }
            if (StringUtils.isNotBlank(apiReq.getPrincipal())) {
                truckManage.setPrincipal(apiReq.getPrincipal());
            }
            if (StringUtils.isNotBlank(apiReq.getTelephone())) {
                truckManage.setTelephone(apiReq.getTelephone());
            }
            if (null != apiReq.getCarryWeight()) {
                truckManage.setCarryWeight(apiReq.getCarryWeight());
            }
            if (null != apiReq.getCarryVolume()) {
                truckManage.setCarryVolume(apiReq.getCarryVolume());
            }
            if (StringUtils.isNotBlank(apiReq.getStatus())) {
                truckManage.setStatus(apiReq.getStatus());
            }
            truckManageService.save(truckManage);
        }
        return baseResp.success();
    }

    @Override
    public BaseResp delTruck(String id, BaseResp baseResp) {
        TruckManage truckManage = truckManageService.get(id);
        if (null == truckManage) {
            baseResp.error();
            baseResp.setMsg("data not exist");
            return baseResp;
        } else {
            truckManageService.delete(truckManage);
        }
        return baseResp.success();
    }


}
