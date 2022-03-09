package com.haohan.platform.service.sys.modules.xiaodian.web;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.weixin.mp.user.WxMpUserManageService;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenApiService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * 开放平台用户管理Controller
 *
 * @author haohan
 * @version 2017-08-05
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/userOpenPlatform")
public class UserOpenPlatformController extends BaseController {

    @Autowired
    private MerchantAppManageService merchantAppManageService;

    @Autowired
    private UserOpenPlatformService userOpenPlatformService;
    @Autowired
    private WxOpenApiService wxOpenApiService;
    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private WxMpUserManageService wxMpUserManageService;

    @ModelAttribute
    public UserOpenPlatform get(@RequestParam(required = false) String id) {
        UserOpenPlatform entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userOpenPlatformService.get(id);
        }
        if (entity == null) {
            entity = new UserOpenPlatform();
        }
        return entity;
    }

    @RequiresPermissions("xiaodian:userOpenPlatform:view")
    @RequestMapping(value = {"list", ""})
    public String list(UserOpenPlatform userOpenPlatform, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserOpenPlatform> page = userOpenPlatformService.findPage(new Page<UserOpenPlatform>(request, response), userOpenPlatform);
        // 获取授权应用
        List<AuthApp> appList = authAppService.findList(new AuthApp());
        model.addAttribute("appList", appList);
        model.addAttribute("page", page);
        return "modules/xiaodian/userOpenPlatformList";
    }


    @RequiresPermissions("xiaodian:userOpenPlatform:view")
    @RequestMapping(value = "form")
    public String form(UserOpenPlatform userOpenPlatform, Model model) {
        model.addAttribute("userOpenPlatform", userOpenPlatform);
        return "modules/xiaodian/userOpenPlatformForm";
    }

    @RequiresPermissions("xiaodian:userOpenPlatform:edit")
    @RequestMapping(value = "save")
    public String save(UserOpenPlatform userOpenPlatform, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, userOpenPlatform)) {
            return form(userOpenPlatform, model);
        }
        userOpenPlatformService.save(userOpenPlatform);
        addMessage(redirectAttributes, "保存用户成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/userOpenPlatform/?repage";
    }

    @RequiresPermissions("xiaodian:userOpenPlatform:edit")
    @RequestMapping(value = "delete")
    public String delete(UserOpenPlatform userOpenPlatform, RedirectAttributes redirectAttributes) {
        userOpenPlatformService.delete(userOpenPlatform);
        addMessage(redirectAttributes, "删除用户成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/userOpenPlatform/?repage";
    }


    @RequiresPermissions("xiaodian:userOpenPlatform:edit")
    @RequestMapping(value = "syncMpUser")
    public String syncMpUser(UserOpenPlatform userOpenPlatform, Model model, RedirectAttributes redirectAttributes) {

        String appId = userOpenPlatform.getAppId();
        if (StringUtils.isEmpty(appId)) {
            addMessage(redirectAttributes, "AppId为空同步微信公众号用户失败!");
            return "redirect:" + Global.getAdminPath() + "/xiaodian/userOpenPlatform/?repage";
        }
        AuthApp authApp = authAppService.fetchByAppId(appId);

        WxMpService wxMpService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId()).getWxOpenComponentService().getWxMpServiceByAppid(appId);

        List<WxMpUser> wxMpUserList = wxMpUserManageService.fetchSubscribeUser(wxMpService);

        MerchantAppManage merchantApp = merchantAppManageService.fetchByAppId(authApp.getAppId());

        String merchantId = (null == merchantApp)?"":merchantApp.getMerchantId();

        for (WxMpUser wxMpUser : wxMpUserList) {
            try {
                userOpenPlatformService.addUserByWxMpUser(merchantId, authApp, wxMpUser);
            }catch (Exception e){
                if (e instanceof  SQLException){
                    wxMpUser.setNickname("昵称有误");
                    userOpenPlatformService.addUserByWxMpUser(merchantId, authApp, wxMpUser);
                }
                e.printStackTrace();
            }
        }

        addMessage(redirectAttributes, "同步微信用户成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/userOpenPlatform/?repage";
    }


}