package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminRolesService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色管理 : 采购商/员工
 *
 * @author dy
 * @date 2019/2/13
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/roles")
public class PdsAdminRolesCtrl extends BaseController {

    @Autowired
    private IPdsAdminRolesService pdsAdminRolesService;

    /**
     * 获取采购商列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "buyer/findList")
    @ResponseBody
    public BaseResp buyerList(@Validated PdsAdminBuyerListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        PdsBuyer buyer = new PdsBuyer();
        buyer.setPmId(listReq.getPmId());
        buyer.setPassportId(listReq.getUid());
        buyer.setMerchantId(listReq.getMerchantId());
        buyer.setMerchantName(listReq.getMerchantName());
        buyer.setBuyerName(listReq.getBuyerName());
        buyer.setShortName(listReq.getShortName());
        buyer.setTelephone(listReq.getTelephone());
        buyer.setBuyerType(listReq.getBuyerType());
        buyer.setNeedConfirmation(listReq.getNeedConfirmation());
        buyer.setNeedPush(listReq.getNeedPush());
        buyer.setBindStatus(listReq.getBindStatus());
        buyer.setStatus(listReq.getStatus());
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        buyer.setPage(new Page<>(pageNo, pageSize));
        baseResp = pdsAdminRolesService.findBuyerList(buyer);
        return baseResp;
    }

    /**
     * 编辑采购商 新增或修改
     *
     * @param editReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "buyer/edit")
    @ResponseBody
    public BaseResp buyerEdit(@Validated PdsAdminBuyerEditReq editReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        // 默认项
        String merchantId = editReq.getMerchantId();
        String buyerType = editReq.getBuyerType();
        if (StringUtils.isEmpty(merchantId)) {
            editReq.setMerchantId(editReq.getPmId());
        }
        if (null == IPdsConstant.BuyerType.getTypeByCode(buyerType)) {
            editReq.setBuyerType(IPdsConstant.BuyerType.employee.getCode());
        }
        baseResp = pdsAdminRolesService.buyerEdit(editReq);
        return baseResp;
    }

    /**
     * 采购商 删除
     *
     * @param deleteReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "buyer/delete")
    @ResponseBody
    public BaseResp buyerDelete(@Validated PdsAdminBuyerDeleteReq deleteReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminRolesService.buyerDelete(deleteReq);
        return baseResp;
    }

    /**
     * 获取 员工列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "employee/findList")
    @ResponseBody
    public BaseResp employeeList(@Validated PdsAdminEmployeeListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        MerchantEmployee employee = new MerchantEmployee();
        employee.setPmId(listReq.getPmId());
        employee.setMerchantId(listReq.getMerchantId());
        employee.setMerchantName(listReq.getMerchantName());
        employee.setPassportId(listReq.getUid());
        employee.setRole(listReq.getRole());
        employee.setTelephone(listReq.getTelephone());
        employee.setName(listReq.getName());
        employee.setOrigin(listReq.getOrigin());
        employee.setStatus(listReq.getStatus());
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        employee.setPage(new Page<>(pageNo, pageSize));
        baseResp = pdsAdminRolesService.findEmployeeList(employee);
        return baseResp;
    }

    /**
     * 编辑员工 新增或修改
     *
     * @param editReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "employee/edit")
    @ResponseBody
    public BaseResp employeeEdit(@Validated PdsAdminEmployeeEditReq editReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        // 默认项
        String merchantId = editReq.getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            editReq.setMerchantId(editReq.getPmId());
        }
        baseResp = pdsAdminRolesService.employeeEdit(editReq);
        return baseResp;
    }

    /**
     * 员工 删除
     *
     * @param deleteReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "employee/delete")
    @ResponseBody
    public BaseResp employeeDelete(@Validated PdsAdminEmployeeDeleteReq deleteReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminRolesService.employeeDelete(deleteReq);
        return baseResp;
    }

    /**
     * 获取 供应商列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "supplier/findList")
    @ResponseBody
    public BaseResp supplierList(@Validated PdsAdminSupplierListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        PdsSupplier supplier = new PdsSupplier();
        supplier.setPmId(listReq.getPmId());
        supplier.setPassportId(listReq.getUid());
        supplier.setShortName(listReq.getShortName());
        supplier.setSupplierName(listReq.getSupplierName());
        supplier.setStatus(listReq.getStatus());
        supplier.setNeedPush(listReq.getNeedPush());
        supplier.setSupplierType(listReq.getSupplierType());
        supplier.setMerchantId(listReq.getMerchantId());
        supplier.setMerchantName(listReq.getMerchantName());
        supplier.setTelephone(listReq.getTelephone());
        supplier.setBindStatus(listReq.getBindStatus());
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        supplier.setPage(new Page<>(pageNo, pageSize));
        baseResp = pdsAdminRolesService.findSupplierList(supplier);
        return baseResp;
    }

    /**
     * 编辑供应商 新增或修改
     *
     * @param editReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "supplier/edit")
    @ResponseBody
    public BaseResp supplierEdit(@Validated PdsAdminSupplierEditReq editReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        // 默认项
        String merchantId = editReq.getMerchantId();
        String supplierType = editReq.getSupplierType();
        if (StringUtils.isEmpty(merchantId)) {
            editReq.setMerchantId(editReq.getPmId());
        }
        if (null == IPdsConstant.BuyerType.getTypeByCode(supplierType)) {
            editReq.setSupplierType(IPdsConstant.SupplierType.normal.getCode());
        }
        baseResp = pdsAdminRolesService.supplierEdit(editReq);
        return baseResp;
    }

    /**
     * 供应商 删除
     *
     * @param deleteReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "supplier/delete")
    @ResponseBody
    public BaseResp supplierDelete(@Validated PdsAdminSupplierDeleteReq deleteReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminRolesService.supplierDelete(deleteReq);
        return baseResp;
    }
}
