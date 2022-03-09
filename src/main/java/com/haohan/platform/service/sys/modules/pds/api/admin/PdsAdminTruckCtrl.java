package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.beanvalidator.AddGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import com.haohan.platform.service.sys.common.beanvalidator.EditGroup;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsTruckApiReq;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminDeliveryService;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;
import com.haohan.platform.service.sys.modules.pds.service.delivery.TruckManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shenyu
 * @create 2019/2/14
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/truck")
public class PdsAdminTruckCtrl extends BaseController {
    @Autowired
    private IPdsAdminDeliveryService pdsAdminDeliveryService;
    @Autowired
    private TruckManageService truckManageService;

    /**
     * 车辆列表
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public BaseResp list(@Validated(DefaultGroup.class) PdsBaseApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminDeliveryService.truckList(apiReq.getPmId(), baseResp);
        return baseResp;
    }

    /**
     * 新增车辆
     *
     * @param apiReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public BaseResp addTruck(@Validated(value = AddGroup.class) PdsTruckApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminDeliveryService.insertTruck(apiReq, baseResp);
        return baseResp;
    }

    /**
     * 新增车辆(不设置司机)
     *
     * @param truckManage
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public BaseResp saveTruck(TruckManage truckManage) {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyBlank(truckManage.getPmId(), truckManage.getTruckNo(), truckManage.getTruckName())) {
            baseResp.setMsg("缺少参数");
            return baseResp;
        }
        truckManageService.save(truckManage);
        baseResp.success();
        baseResp.setExt(truckManage);
        return baseResp;
    }

    /**
     * 编辑车辆
     *
     * @param apiReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "edit")
    @ResponseBody
    public BaseResp editTruck(@Validated(value = EditGroup.class) PdsTruckApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminDeliveryService.updateTruck(apiReq, baseResp);
        return baseResp;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public BaseResp delTruck(@Validated(DeleteGroup.class) PdsTruckApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminDeliveryService.delTruck(apiReq.getId(), baseResp);
        return baseResp;
    }

}
