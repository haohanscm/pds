package com.haohan.platform.service.sys.modules.pss.api.ctrl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssDeleteCommonApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcureReturnApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcureReturnDetailApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssProcureReturnService;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;
import com.haohan.platform.service.sys.modules.xiaodian.exception.DataNotFoundException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.GoodsStockNotEnoughException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 采购退货
 * @author shenyu
 * @create 2018/9/26
 */
@Controller
@RequestMapping(value = "${frontPath}/pss/api/procureReturn")
public class PssProcureReturnApiCtrl extends BaseController {
    @Resource
    private IPssProcureReturnService mercProcureReturnServiceImpl;

    //退货列表
    @RequestMapping(value = "findPage")
    @ResponseBody
    public BaseResp findPage(@Validated PssProcureReturnApiReq returnApiReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        Integer pageNo = returnApiReq.getPageNo();
        Integer pageSize = returnApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);
        try {
            baseResp = mercProcureReturnServiceImpl.procureReturnPage(returnApiReq,reqPage);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setMsg("系统错误");
        } finally {
            return baseResp;
        }
    }

    //查看退货详情
    @RequestMapping(value = "details")
    @ResponseBody
    public BaseResp returnDetail(@Validated PssProcureReturnDetailApiReq detailApiReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        Integer pageNo = detailApiReq.getPageNo();
        Integer pageSize = detailApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);
        baseResp = mercProcureReturnServiceImpl.procureReturnDetails(detailApiReq,reqPage);
        return baseResp;
    }

    //删除退货
    @RequestMapping(value = "delete")
    @ResponseBody
    public BaseResp delete(@Validated PssDeleteCommonApiReq commonApiReq,BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        baseResp = mercProcureReturnServiceImpl.deleteProcureReturn(commonApiReq.getId());
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

        baseResp = mercProcureReturnServiceImpl.delDetail(commonApiReq.getId());
        return baseResp;
    }

    //保存退货
    @RequestMapping(value = "save")
    @ResponseBody
    public BaseResp saveReturn(@RequestBody @Validated PurchaseReturn purchaseReturn,BindingResult bindingResult, HttpServletRequest request) throws Exception{
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        try {
            baseResp = mercProcureReturnServiceImpl.saveReturn(purchaseReturn);
        }catch (DataNotFoundException e){
            baseResp.setMsg(e.getMessage());
        }
        return baseResp;
    }

    //确定退货
    @RequestMapping(value = "confirm")
    @ResponseBody
    public BaseResp confirm(@Validated PssDeleteCommonApiReq commonApiReq,BindingResult bindingResult){
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        try {
            baseResp = mercProcureReturnServiceImpl.confirm(commonApiReq.getId());
        }catch (GoodsStockNotEnoughException e){
            baseResp.setMsg(e.getMessage());
        }
        return baseResp;
    }

}
