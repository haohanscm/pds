package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.beanvalidator.AddGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import com.haohan.platform.service.sys.common.beanvalidator.EditGroup;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsEmployeeApiReq;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shenyu
 * @create 2019/2/14
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/employee")
public class PdsAdminEmployeeCtrl extends BaseController {
    @Autowired
    private IPdsAdminEmployeeService pdsAdminEmployeeService;

    @RequestMapping(value = "list")
    public BaseResp selectList(@Validated(DeleteGroup.class) PdsBaseApiReq apiBaseReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminEmployeeService.selectList(apiBaseReq, baseResp);
        return baseResp;
    }

    @RequestMapping(value = "add")
    public BaseResp add(@Validated(AddGroup.class) PdsEmployeeApiReq employeeApiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminEmployeeService.add(employeeApiReq, baseResp);
        return baseResp;
    }

    @RequestMapping(value = "update")
    public BaseResp update(@Validated(EditGroup.class) PdsEmployeeApiReq employeeApiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminEmployeeService.update(employeeApiReq, baseResp);
        return baseResp;
    }

    @RequestMapping(value = "delete")
    public BaseResp delete(@Validated(value = DeleteGroup.class) PdsEmployeeApiReq delReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminEmployeeService.delete(delReq, baseResp);
        return baseResp;
    }

}
