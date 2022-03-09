package com.haohan.platform.service.sys.modules.pss.api.ctrl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssDeleteCommonApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssGoodsAllotApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssGoodsAllotDetailApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssStorageAllotService;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllot;
import com.haohan.platform.service.sys.modules.pss.service.storage.GoodsAllotService;
import com.haohan.platform.service.sys.modules.xiaodian.exception.GoodsStockNotEnoughException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 商品调拨controller
 * @author shenyu
 * @create 2018/9/26
 */
@Controller
@RequestMapping(value = "${frontPath}/pss/api/goodsAllot")
public class PssGoodsAllotApiCtrl extends BaseController {
    @Resource
    private GoodsAllotService goodsAllotService;
    @Resource
    private IPssStorageAllotService mercStorageAllotService;


    //调拨记录列表
    @RequestMapping(value = "findPage")
    @ResponseBody
    public BaseResp findPage(@Validated PssGoodsAllotApiReq goodsAllotApiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        Integer pageNo = goodsAllotApiReq.getPageNo();
        Integer pageSize = goodsAllotApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        try {
            baseResp = mercStorageAllotService.findAllotPage(goodsAllotApiReq,reqPage);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setMsg("系统出错了");
        } finally {
            return baseResp;
        }
    }

    //查看调拨记录详情
    @RequestMapping(value = "details")
    @ResponseBody
    public BaseResp findDetailPage(@Validated PssGoodsAllotDetailApiReq allotDetailApiReq,BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        String goodsAllotId = allotDetailApiReq.getAllotId();
        Integer pageNo = allotDetailApiReq.getPageNo();
        Integer pageSize = allotDetailApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        GoodsAllot goodsAllot = goodsAllotService.get(goodsAllotId);
        if (null == goodsAllot){
            baseResp.setMsg("调拨记录不存在");
            return baseResp;
        }

        try {
            baseResp = mercStorageAllotService.findAllotDetailPage(allotDetailApiReq,reqPage);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setMsg("系统出错了");
        } finally {
            return baseResp;
        }
    }

    //删除明细
    @RequestMapping(value = "details/delete")
    @ResponseBody
    public BaseResp deleteDetail(@Validated PssDeleteCommonApiReq commonApiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        if (StringUtils.isBlank(commonApiReq.getId())){
            baseResp.setMsg("ID不能为空");
            return baseResp;
        }

        baseResp = mercStorageAllotService.delDetail(commonApiReq.getId());
        return baseResp;
    }

    //保存调拨记录
    @RequestMapping(value = "save")
    @ResponseBody
    public BaseResp saveAllot(@RequestBody @Validated GoodsAllot goodsAllot,BindingResult bindingResult, HttpServletRequest request) throws Exception{
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        String merchantId = goodsAllot.getMerchantId();

        baseResp = mercStorageAllotService.saveAllot(merchantId,goodsAllot);
        if (baseResp.isSuccess()){
            baseResp = mercStorageAllotService.saveAllotDetail(merchantId,goodsAllot.getGoodsAllotDetailList(),(String) baseResp.getExt());
        }
        return baseResp;
    }

    //确认调拨
    @RequestMapping(value = "confirm")
    @ResponseBody
    public BaseResp confirm(@Validated PssDeleteCommonApiReq commonApiReq, BindingResult bindingResult) throws Exception{
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        if (StringUtils.isBlank(commonApiReq.getId())){
            baseResp.setMsg("ID不能为空");
            return baseResp;
        }
        try {
            baseResp = mercStorageAllotService.confirm(commonApiReq.getId());
        }catch (GoodsStockNotEnoughException e){
            baseResp.setMsg(e.getMessage());
        }finally {
            return baseResp;
        }
    }



}
