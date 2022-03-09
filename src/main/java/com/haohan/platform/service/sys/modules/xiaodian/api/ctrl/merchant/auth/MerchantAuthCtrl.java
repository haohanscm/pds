package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.auth;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.sys.security.UsernamePasswordToken;
import com.haohan.platform.service.sys.modules.sys.utils.UserUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.MerchantAuth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zgw on 2018/9/26.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/auth")
public class MerchantAuthCtrl extends BaseController {

    @RequestMapping(value = "reg")
    @ResponseBody
    public BaseResp reg(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        return baseResp;
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public BaseResp login(String userName, String password, @RequestParam(required = false) String merchantId, HttpServletRequest request, HttpServletResponse response) {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyEmpty(userName, password)) {
            baseResp.setMsg("缺少参数userName/password");
            return baseResp;
        }
        Subject subject = UserUtils.getSubject();
        UsernamePasswordToken userToken = new UsernamePasswordToken(userName, password.toCharArray(), false, StringUtils.getRemoteAddr(request), "", false);
        try {
            subject.login(userToken);
        } catch (AuthenticationException e) {
            baseResp.setMsg("用户名和密码错误");
            return baseResp;
        }
        MerchantAuth merchantAuth = new MerchantAuth();
        merchantAuth.setPrincipal(UserUtils.getPrincipal());
        merchantAuth.setMerchantId(merchantId);
        Session session = UserUtils.getSession();
        String key = "merchantAuth-" + session.getId();
        JedisUtils.setObject(key, merchantAuth, 7200);
        baseResp.success();
        return baseResp;
    }

    @RequestMapping(value = "logout")
    @ResponseBody
    public BaseResp logout(HttpServletRequest request, ServletResponse response) {
        BaseResp baseResp = BaseResp.newError();

        Subject subject = SecurityUtils.getSubject();

        subject.logout();
        baseResp.success();
        baseResp.setMsg("退出成功");
        return baseResp;
    }


    @RequestMapping(value = "check")
    @ResponseBody
    @RequiresPermissions("merchant:auth:check")
    public BaseResp check(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        return baseResp;
    }
//
//    /**
//     * 获取商家认证信息  企业、个体户 authentication
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "authentication")
//    @ResponseBody
//    public String authentication(HttpServletRequest request) {
//
//        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        String merchantId = merchantAuth.getMerchantId();
//        // 参数验证
////        if (StringUtils.isEmpty(reqMember.getShopId())) {
////            resp.setMsg("缺少参数:goodsSn");
////            return resp.toJson();
////        }
//        ArrayList<RespPayInfo> result = merchantManageService.fetchPayChannelInfo(merchantId);
//        if (Collections3.isEmpty(result)) {
//            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
//        } else {
//            resp.success();
//            resp.setExt(result);
//        }
//        return resp.toJson();
//    }

//    /**
//     * 商家认证  企业、个体户
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "authentication")
//    @ResponseBody
//    public String authentication(HttpServletRequest request) {
//
//        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        String merchantId = merchantAuth.getMerchantId();
//        // 参数验证
////        if (StringUtils.isEmpty(reqMember.getShopId())) {
////            resp.setMsg("缺少参数:goodsSn");
////            return resp.toJson();
////        }
//        ArrayList<RespPayInfo> result = merchantManageService.fetchPayChannelInfo(merchantId);
//        if (Collections3.isEmpty(result)) {
//            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
//        } else {
//            resp.success();
//            resp.setExt(result);
//        }
//        return resp.toJson();
//    }

}
