package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminRolesService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dy
 * @date 2019/02/13
 */
@Service
public class AdminRolesServiceImpl implements IPdsAdminRolesService {

    @Autowired
    @Lazy(true)
    private PdsBuyerService buyerService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService supplierService;
    @Autowired
    @Lazy(true)
    private MerchantEmployeeService merchantEmployeeService;
    @Autowired
    @Lazy(true)
    private MerchantService merchantService;

    @Override
    public BaseResp findBuyerList(PdsBuyer buyer) {
        BaseResp baseResp = BaseResp.newError();
        List<PdsBuyer> list = buyerService.findJoinList(buyer);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = buyer.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp buyerEdit(PdsAdminBuyerEditReq editReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsBuyer buyer = null;
        // 修改
        if (StringUtils.isNotEmpty(editReq.getId())) {
            buyer = buyerService.get(editReq.getId());
            if (null == buyer) {
                baseResp.setMsg("采购商id有误");
                return baseResp;
            }
        }
        // 新增
        if (null == buyer) {
            editReq.setId(null);
            buyer = new PdsBuyer();
        }
        // 商家
        Merchant merchant = merchantService.get(editReq.getMerchantId());
        if (null == merchant) {
            baseResp.setMsg("商家id有误");
            return baseResp;
        } else {
            editReq.setMerchantName(merchant.getMerchantName());
        }
        editReq.transToBuyer(buyer);
        buyerService.save(buyer);
        baseResp.success();
        baseResp.setExt(buyer);
        return baseResp;
    }

    @Override
    public BaseResp findSupplierList(PdsSupplier supplier) {
        BaseResp baseResp = BaseResp.newError();
        List<PdsSupplier> list = supplierService.findJoinList(supplier);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = supplier.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp supplierEdit(PdsAdminSupplierEditReq editReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsSupplier supplier = null;
        // 商家
        Merchant merchant = merchantService.get(editReq.getMerchantId());
        if (null == merchant) {
            baseResp.setMsg("商家id有误");
            return baseResp;
        } else {
            editReq.setMerchantName(merchant.getMerchantName());
        }
        // 修改
        if (StringUtils.isNotEmpty(editReq.getId())) {
            supplier = supplierService.get(editReq.getId());
            if (null == supplier) {
                baseResp.setMsg("供应商id有误");
                return baseResp;
            }
        }
        // 新增
        if (null == supplier) {
            editReq.setId(null);
            supplier = new PdsSupplier();
        }
        editReq.transToSupplier(supplier);
        supplierService.save(supplier);
        baseResp.success();
        baseResp.setExt(supplier);
        return baseResp;
    }

    @Override
    public BaseResp findEmployeeList(MerchantEmployee employee) {
        BaseResp baseResp = BaseResp.newError();
        List<MerchantEmployee> list = merchantEmployeeService.findJoinList(employee);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = employee.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp employeeEdit(PdsAdminEmployeeEditReq editReq) {
        BaseResp baseResp = BaseResp.newError();
        MerchantEmployee employee = null;
        // 商家
        Merchant merchant = merchantService.get(editReq.getMerchantId());
        if (null == merchant) {
            baseResp.setMsg("商家id有误");
            return baseResp;
        }
        // 修改
        if (StringUtils.isNotEmpty(editReq.getId())) {
            employee = merchantEmployeeService.get(editReq.getId());
            if (null == employee) {
                baseResp.setMsg("员工id有误");
                return baseResp;
            }
        }
        // 新增
        if (null == employee) {
            editReq.setId(null);
            employee = new MerchantEmployee();
        }
        editReq.transToEmployee(employee);
        merchantEmployeeService.save(employee);
        baseResp.success();
        baseResp.setExt(employee);
        return baseResp;
    }

    @Override
    public BaseResp buyerDelete(PdsAdminBuyerDeleteReq deleteReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsBuyer pdsBuyer = buyerService.get(deleteReq.getId());
        // 只可删除 所属采购商
        if (null == pdsBuyer || !StringUtils.equals(pdsBuyer.getPmId(), deleteReq.getPmId())) {
            baseResp.setMsg("采购商id有误");
            return baseResp;
        }
        buyerService.delete(pdsBuyer);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp employeeDelete(PdsAdminEmployeeDeleteReq deleteReq) {
        BaseResp baseResp = BaseResp.newError();
        MerchantEmployee merchantEmployee = merchantEmployeeService.get(deleteReq.getId());
        // 只可删除 所属员工
        if (null == merchantEmployee || !StringUtils.equals(merchantEmployee.getPmId(), deleteReq.getPmId())) {
            baseResp.setMsg("员工id有误");
            return baseResp;
        }
        merchantEmployeeService.delete(merchantEmployee);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp supplierDelete(PdsAdminSupplierDeleteReq deleteReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsSupplier supplier = supplierService.get(deleteReq.getId());
        // 只可删除 所属供应商
        if (null == supplier || !StringUtils.equals(supplier.getPmId(), deleteReq.getPmId())) {
            baseResp.setMsg("供应商id有误");
            return baseResp;
        }
        supplierService.delete(supplier);
        baseResp.success();
        return baseResp;
    }
}
