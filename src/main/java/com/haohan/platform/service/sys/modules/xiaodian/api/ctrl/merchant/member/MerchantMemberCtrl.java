package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.member;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.MerchantAuth;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.ReqMember;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.RespPage;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.member.MerchantMemberService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 商家 会员管理
 * Created by dy on 2018/9/26.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/v2.0/member")
public class MerchantMemberCtrl extends BaseController {

    @Autowired
    private MerchantMemberService merchantMemberService;


    /**
     * 会员列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchMemberList")
    @ResponseBody
    public String fetchMemberList(ReqMember reqMember, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 参数验证
//        if (StringUtils.isEmpty(reqMember.getShopId())) {
//            resp.setMsg("缺少参数:shopId");
//            return resp.toJson();
//        }
        // 传入参数处理
        // 分页查询
        int pageNo = StringUtils.toInteger(reqMember.getPageNo());
        int pageSize = StringUtils.toInteger(reqMember.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;

        RespPage<UserOpenPlatform> result = merchantMemberService.fetchMemberList(merchantId, reqMember.getShopId(), pageNo, pageSize);
        if (Collections3.isEmpty(result.getList())) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }




    /**
     * 会员详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchMemberInfo")
    @ResponseBody
    public String fetchMemberInfo(ReqMember reqMember, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
//        if (StringUtils.isEmpty(reqMember.getShopId())) {
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
//        GoodsInfoExt goodsInfoExt = merchantGoodsService.getGoodsInfoExt(reqGoods.getGoodsSn());
//        if (null == goodsInfoExt) {
//            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
//        } else {
//            resp.success();
//            resp.setExt(goodsInfoExt);
//        }

        return resp.toJson();
    }



    /**
     * 会员 信息修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "memberInfoModify")
    @ResponseBody
    public String memberInfoModify(ReqMember reqMember, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
//        if (StringUtils.isEmpty(reqMember.getShopId())) {
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
//        GoodsInfoExt goodsInfoExt = merchantGoodsService.getGoodsInfoExt(reqGoods.getGoodsSn());
//        if (null == goodsInfoExt) {
//            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
//        } else {
//            resp.success();
//            resp.setExt(goodsInfoExt);
//        }

        return resp.toJson();
    }








}
