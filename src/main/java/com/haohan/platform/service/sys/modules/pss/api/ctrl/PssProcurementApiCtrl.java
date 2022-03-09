package com.haohan.platform.service.sys.modules.pss.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssDeleteCommonApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcureDetailApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcurementApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssProcurementService;
import com.haohan.platform.service.sys.modules.pss.constant.IPssConstant;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 商品采购controller
 * @author shenyu
 * @create 2018/9/26
 */
@Controller
@RequestMapping(value = "${frontPath}/pss/api/procurement")
public class PssProcurementApiCtrl extends BaseController implements IPssConstant {
    @Resource
    private IPssProcurementService mercProcurementServiceImpl;

    //采购单列表
    @RequestMapping(value = "findPage")
    @ResponseBody
    public BaseResp findPage(@Validated PssProcurementApiReq procurementApiReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        Integer pageNo = procurementApiReq.getPageNo();
        Integer pageSize = procurementApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        baseResp = mercProcurementServiceImpl.listProcurement(procurementApiReq,reqPage);
        return baseResp;
    }

    //采购明细列表
    @RequestMapping(value = "details/findPage")
    @ResponseBody
    public BaseResp findDetailPage(@Validated PssProcureDetailApiReq detailApiReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        String procureNum = detailApiReq.getProcureNum();
        if(StringUtils.isBlank(procureNum)){
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }
        Integer pageNo = detailApiReq.getPageNo();
        Integer pageSize = detailApiReq.getPageSize();

        Page page = new Page();
        page.setPageNo(null == pageNo ? 1 : pageNo);
        page.setPageSize(null == pageSize ? 20 : pageSize);
        baseResp = mercProcurementServiceImpl.listProcureDetails(page,procureNum);
        return baseResp;
    }

    //保存采购
    @RequestMapping(value = "save")
    @ResponseBody
    public BaseResp saveProcurement(@RequestBody @Validated Procurement procurement,BindingResult bindingResult, HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        if (null == procurement.getProcureDate()){
            baseResp.setMsg("未选择采购日期");
            return baseResp;
        }
        try {
            baseResp = mercProcurementServiceImpl.saveProcurement(procurement);
        } catch (StorageOperationException e1) {
            baseResp.setMsg(e1.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            baseResp.setMsg("系统错误");
        } finally {
            return baseResp;
        }
    }

    //删除采购单
    @RequestMapping(value = "delete")
    @ResponseBody
    public BaseResp delete(@Validated PssDeleteCommonApiReq commonApiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        baseResp = mercProcurementServiceImpl.delProcurement(commonApiReq.getMerchantId(),commonApiReq.getId());
        return baseResp;
    }

    //删除明细
    @RequestMapping(value = "details/delete")
    @ResponseBody
    public BaseResp deleteDetal(@Validated PssDeleteCommonApiReq commonApiReq,BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        baseResp = mercProcurementServiceImpl.delDetail(commonApiReq.getId());
        return baseResp;
    }

    //采购商品入库
    @RequestMapping(value = "details/enterStock")
    @ResponseBody
    public BaseResp enterStock(@Validated PssDeleteCommonApiReq commonApiReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        baseResp = mercProcurementServiceImpl.enterStock(commonApiReq.getId());
        return baseResp;
    }

}
