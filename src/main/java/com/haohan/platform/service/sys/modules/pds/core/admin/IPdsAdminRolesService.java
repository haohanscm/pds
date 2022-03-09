package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;

/**
 * 采购配送平台商家  角色管理: 采购商 员工
 *
 * @author dy
 * @date 2019/2/13
 */
public interface IPdsAdminRolesService {

    /**
     * 获取采购商列表
     *
     * @param buyer
     * @return
     */
    BaseResp findBuyerList(PdsBuyer buyer);

    /**
     * 编辑采购商 新增或修改
     *
     * @param editReq
     * @return
     */
    BaseResp buyerEdit(PdsAdminBuyerEditReq editReq);

    /**
     * 获取供应商列表
     *
     * @param supplier
     * @return
     */
    BaseResp findSupplierList(PdsSupplier supplier);

    /**
     * 编辑供应商 新增或修改
     *
     * @param editReq
     * @return
     */
    BaseResp supplierEdit(PdsAdminSupplierEditReq editReq);

    /**
     * 获取员工列表
     *
     * @param employee
     * @return
     */
    BaseResp findEmployeeList(MerchantEmployee employee);

    /**
     * 编辑员工 新增或修改
     *
     * @param editReq
     * @return
     */
    BaseResp employeeEdit(PdsAdminEmployeeEditReq editReq);

    /**
     * 采购商 删除
     *
     * @param deleteReq
     * @return
     */
    BaseResp buyerDelete(PdsAdminBuyerDeleteReq deleteReq);

    /**
     * 员工 删除
     *
     * @param deleteReq
     * @return
     */
    BaseResp employeeDelete(PdsAdminEmployeeDeleteReq deleteReq);

    /**
     * 供应商 删除
     *
     * @param deleteReq
     * @return
     */
    BaseResp supplierDelete(PdsAdminSupplierDeleteReq deleteReq);
}
