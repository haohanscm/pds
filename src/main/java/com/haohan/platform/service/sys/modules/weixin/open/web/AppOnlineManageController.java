package com.haohan.platform.service.sys.modules.weixin.open.web;

import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.weixin.open.api.inf.IWxOpenAppService.WxOpen_App_Api;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenAppService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AppOnlineManage;
import com.haohan.platform.service.sys.modules.weixin.open.service.AppOnlineManageService;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 应用上线管理Controller
 *
 * @author haohan
 * @version 2017-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/open/appOnlineManage")
public class AppOnlineManageController extends BaseController {

    @Autowired
    private AppOnlineManageService appOnlineManageService;

    @Autowired
    private WxOpenAppService wxOpenAppService;
    @Autowired
    private AuthAppService authAppService;

    @ModelAttribute
    public AppOnlineManage get(@RequestParam(required = false) String id) {
        AppOnlineManage entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = appOnlineManageService.get(id);
        }
        if (entity == null) {
            entity = new AppOnlineManage();
        }
        return entity;
    }

    @RequiresPermissions("weixin:open:appOnlineManage:view")
    @RequestMapping(value = {"list", ""})
    public String list(AppOnlineManage appOnlineManage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AppOnlineManage> page = appOnlineManageService.findPage(new Page<AppOnlineManage>(request, response), appOnlineManage);
        model.addAttribute("page", page);
        return "modules/weixin/open/appOnlineManageList";
    }

    @RequiresPermissions("weixin:open:appOnlineManage:view")
    @RequestMapping(value = "form")
    public String form(AppOnlineManage appOnlineManage, Model model) {
        model.addAttribute("appOnlineManage", appOnlineManage);
        return "modules/weixin/open/appOnlineManageForm";
    }

    @RequiresPermissions("weixin:open:appOnlineManage:edit")
    @RequestMapping(value = "callApi")
    public String callApi(AppOnlineManage appOnlineManage, Model model, RedirectAttributes redirectAttributes) throws Exception {

        AppOnlineManage onlineManage = new AppOnlineManage();
        BaseResp resp = BaseResp.newError();
        try {
            String stepNo = appOnlineManage.getStepNo();

            String appId = appOnlineManage.getAppId();

            BeanUtils.copyProperties(onlineManage, appOnlineManage);
            onlineManage.setId(null);
            onlineManage.setReqParams(HtmlUtils.htmlUnescape(appOnlineManage.getReqParams()));

            WxOpen_App_Api type = WxOpen_App_Api.valueOfApi(stepNo);

            if (WxOpen_App_Api.getQrcode == type) {
                resp = wxOpenAppService.getQrcode(appId);
            } else if (WxOpen_App_Api.bindTester == type) {
                String respParams = onlineManage.getRespParams();
                if (StringUtils.isNotEmpty(respParams)) {
                    String wechatId = JacksonUtils.readMapValue(respParams, String.class).get("wechatid");
                    if (StringUtils.isNotEmpty(wechatId)) {
                        resp = wxOpenAppService.bindTester(onlineManage.getAppId(), wechatId);
                    }
                }
            }


            model.addAttribute("appOnlineManage", onlineManage);
            addMessage(redirectAttributes, resp.getMsg());

        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "系统错误");
        }

        return "redirect:" + Global.getAdminPath() + "/weixin/open/appOnlineManage/?appId=" + appOnlineManage.getAppId() + "&stepNo=" + appOnlineManage.getStepNo();
    }

    @RequiresPermissions("weixin:open:appOnlineManage:edit")
    @RequestMapping(value = "save")
    public String save(AppOnlineManage appOnlineManage, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, appOnlineManage)) {
            return form(appOnlineManage, model);
        }
        appOnlineManageService.save(appOnlineManage);
        addMessage(redirectAttributes, "保存应用成功");
        return "redirect:" + Global.getAdminPath() + "/weixin/open/appOnlineManage/?repage";
    }

    @RequiresPermissions("weixin:open:appOnlineManage:edit")
    @RequestMapping(value = "delete")
    public String delete(AppOnlineManage appOnlineManage, RedirectAttributes redirectAttributes) {
        appOnlineManageService.delete(appOnlineManage);
        addMessage(redirectAttributes, "删除应用成功");
        return "redirect:" + Global.getAdminPath() + "/weixin/open/appOnlineManage/?repage";
    }


}