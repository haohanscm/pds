package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsEmployeeApiReq;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminEmployeeService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2019/2/14
 */
@Service
public class AdminEmployeeServiceImpl implements IPdsAdminEmployeeService {
    @Autowired
    private MerchantEmployeeService merchantEmployeeService;

    @Override
    public BaseResp selectList(PdsBaseApiReq apiBaseReq, BaseResp baseResp) {
        MerchantEmployee merchantEmployee = new MerchantEmployee();
        merchantEmployee.setPmId(apiBaseReq.getPmId());
        List<MerchantEmployee> respList = merchantEmployeeService.findList(merchantEmployee);
        if (CollectionUtils.isEmpty(respList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(respList.stream().collect(Collectors.toCollection(ArrayList::new)));
        return baseResp;
    }

    @Override
    public BaseResp add(PdsEmployeeApiReq apiReq, BaseResp baseResp) {
        MerchantEmployee merchantEmployee = new MerchantEmployee();
        BeanUtils.copyProperties(apiReq, merchantEmployee);
        merchantEmployee.setStatus(ICommonConstant.YesNoType.yes.getCode());
        merchantEmployee.setRegDate(new Date());
        merchantEmployeeService.save(merchantEmployee);
        return baseResp.success();
    }

    @Override
    public BaseResp update(PdsEmployeeApiReq apiReq, BaseResp baseResp) {
        MerchantEmployee employee = merchantEmployeeService.get(apiReq.getId());
        if (null == employee) {
            baseResp.error();
            baseResp.setMsg("data not exist");
            return baseResp;
        } else {
            if (StringUtils.isNotBlank(apiReq.getName())) {
                employee.setName(apiReq.getName());
            }
            if (StringUtils.isNotBlank(apiReq.getRole())) {
                employee.setRole(apiReq.getRole());
            }
            if (StringUtils.isNotBlank(apiReq.getTelephone())) {
                employee.setTelephone(apiReq.getTelephone());
            }
            if (StringUtils.isNotBlank(apiReq.getStatus())) {
                employee.setStatus(apiReq.getStatus());
            }
            merchantEmployeeService.save(employee);
        }
        return baseResp.success();
    }

    @Override
    public BaseResp delete(PdsEmployeeApiReq delReq, BaseResp baseResp) {
        MerchantEmployee employee = merchantEmployeeService.get(delReq.getId());
        if (null == employee) {
            baseResp.error();
            baseResp.setMsg("data not exist ");
            return baseResp;
        } else {
            merchantEmployeeService.delete(employee);
        }
        return baseResp.success();
    }
}
