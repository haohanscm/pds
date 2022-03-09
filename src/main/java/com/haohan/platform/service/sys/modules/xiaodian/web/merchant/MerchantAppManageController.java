package com.haohan.platform.service.sys.modules.xiaodian.web.merchant;

import cn.binarywang.wx.miniapp.bean.code.WxMaCodeAuditStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.weixin.open.api.entity.WxBaseResp;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenAppService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AppOnlineManage;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AppOnlineManageService;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.*;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.List;
import java.util.Map;

/**
 * 商家应用Controller
 *
 * @author haohan
 * @version 2017-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/merchant/merchantAppManage")
public class MerchantAppManageController extends BaseController {

    @Autowired
    private MerchantAppManageService merchantAppManageService;

    @Autowired
    private ShopTemplateService shopTemplateService;
    @Autowired
    private ShopService shopService;

    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private WxOpenAppService wxOpenAppService;
    @Autowired
    private AppOnlineManageService appOnlineManageService;

    @Autowired
    private ShopTemplateExtInfoService shopTemplateExtInfoService;
    @Autowired
    private MerchantAppExtService merchantAppExtService;

    @ModelAttribute
    public MerchantAppManage get(@RequestParam(required = false) String id) {
        MerchantAppManage entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = merchantAppManageService.get(id);
        }
        if (entity == null) {
            entity = new MerchantAppManage();
        }
        return entity;
    }

    @RequiresPermissions("xiaodian:merchant:merchantAppManage:view")
    @RequestMapping(value = {"list", ""})
    public String list(MerchantAppManage merchantAppManage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MerchantAppManage> page = merchantAppManageService.findPage(new Page<MerchantAppManage>(request, response), merchantAppManage);
        model.addAttribute("page", page);
        return "modules/xiaodian/merchant/merchantAppManageList";
    }

    @RequiresPermissions("xiaodian:merchant:merchantAppManage:view")
    @RequestMapping(value = "form")
    public String form(MerchantAppManage merchantAppManage, Model model) {
        AuthApp authApp = new AuthApp();
        List<AuthApp> authApps = authAppService.findList(authApp);
        List<ShopTemplate> shopTemplates = shopTemplateService.findList(new ShopTemplate());
        List<Merchant> merchants = merchantService.findListEnabled(new Merchant());
        model.addAttribute("merchants", merchants);
        model.addAttribute("authApps", authApps);
        model.addAttribute("shopTemplates", shopTemplates);

        MerchantApp merchantApp = new MerchantApp();
        //如果存在上线记录
        if (StringUtils.equalsAny(IMerchantConstant.available, merchantAppManage.getStatus())) {
            AppOnlineManage online = new AppOnlineManage();
            online.setStatus("200");
            online.setAppId(merchantAppManage.getAppId());
            online.setStepNo("5");
            List<AppOnlineManage> list = appOnlineManageService.findList(online);
            if (CollectionUtils.isNotEmpty(list)) {
                String respParams = list.get(0).getRespParams();
                WxBaseResp resp = JacksonUtils.readValue(respParams, WxBaseResp.class);
                if(null != resp){
                    model.addAttribute("categoryList", resp.getCategoryLists());
                    merchantApp.setCategoryLists(resp.getCategoryLists());
                }
            }
        }
        Shop shop = new Shop();
        shop.setMerchantId(merchantAppManage.getMerchantId());
//        shop.setStatus(IMerchantConstant.available);
        List<Shop> shops = shopService.findList(shop);

        model.addAttribute("shops", shops);

        merchantApp.setMerchantAppManage(merchantAppManage);
        merchantApp.setMerchantAppExtInfo(merchantAppManage.fromExt());

        model.addAttribute("merchantApp", merchantApp);

//         商家应用扩展信息
        if(merchantAppManage.getAppId()!=null && !"".equals(merchantAppManage.getAppId())){
            Map<String,Object> merchantAppExtMap = merchantAppExtService.fetchAppExtInfo(merchantAppManage.getAppId());
            String extInfoJson = JacksonUtils.toJson(merchantAppExtMap);
            model.addAttribute("merchantAppExtJson", extInfoJson);

            model.addAttribute("merchantAppExt", merchantApp.getMerchantAppExtInfo());
        }
        return "modules/xiaodian/merchant/merchantAppManageForm";
    }

    @RequiresPermissions("xiaodian:merchant:merchantAppManage:edit")
    @RequestMapping(value = "save")
    public String save(MerchantApp merchantApp, Model model, RedirectAttributes redirectAttributes) {
        MerchantAppManage merchantAppManage = merchantApp.getMerchantAppManage();
        MerchantAppExtInfo merchantAppExtInfo = merchantApp.getMerchantAppExtInfo();
        String isUpdateExt = merchantApp.getIsUpdateExt();

        if (!beanValidator(model, merchantAppManage)) {
            return form(merchantAppManage, model);
        }

        //通过账户获取商家信息
        Merchant merchant=merchantService.get(merchantAppManage.getMerchantId());

        if(null == merchant){
            addMessage(model,"商家不能为空");
            return form(merchantAppManage, model);
        }

        if (StringUtils.isEmpty(merchantAppManage.getAppName())) {
            AuthApp app = authAppService.fetchByAppId(merchantAppManage.getAppId());
            if (null != app && StringUtils.isNotEmpty(app.getAppName())) {
                merchantAppManage.setAppName(app.getAppName());
            }
        }

        //根据模板扩展参数创建商家应用扩展信息

        List<ShopTemplateExtInfo> shopTemplateExtInfos =  shopTemplateExtInfoService.findByTemplateId(merchantAppManage.getTemplateId());

        if(CollectionUtils.isNotEmpty(shopTemplateExtInfos)){
            Shop shop = shopService.get(merchantAppExtInfo.getShopId());
            if(null == shop){
                addMessage(model,"店铺不能为空");
                return form(merchantAppManage, model);
            }
            //如果不存在扩展信息则创建
            if(!merchantAppExtService.isExistExtInfo(merchantAppManage)){
                merchantAppExtService.transToMerchantAppExt(shopTemplateExtInfos,merchantAppManage,merchant,shop,true);
            }else{
                // 已存在时，选择更新扩展信息
                if(StringUtils.equals("1",isUpdateExt) ){
                    merchantAppExtService.transToMerchantAppExt(shopTemplateExtInfos,merchantAppManage,merchant,shop,false);
                }
            }
        }


        ShopTemplate shopTemplate = shopTemplateService.get(merchantAppManage.getTemplateId());
        if (null != shopTemplate) {
            merchantAppManage.setTemplateName(shopTemplate.getTemplateName());
        }

        merchantAppManage.setExt(merchantAppExtInfo.toJson());
        merchantAppManageService.save(merchantAppManage);
        addMessage(redirectAttributes, "保存商家应用成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/merchant/merchantAppManage/?repage";
    }

    @RequiresPermissions("xiaodian:merchant:merchantAppManage:edit")
    @RequestMapping(value = "delete")
    public String delete(MerchantAppManage merchantAppManage, RedirectAttributes redirectAttributes) {
        merchantAppManageService.delete(merchantAppManage);
        addMessage(redirectAttributes, "删除商家应用成功");
        return "redirect:" + Global.getAdminPath() + "/xiaodian/merchant/merchantAppManage/?repage";
    }

    @RequiresPermissions("xiaodian:merchant:merchantAppManage:edit")
    @RequestMapping(value = "onlineBeta")
    public String onlineBate(MerchantAppManage merchantAppManage, Model model, RedirectAttributes redirectAttributes,@RequestParam(value = "testers",required = false) String testers) {
        String appId = merchantAppManage.getAppId();
        merchantAppManage = merchantAppManageService.fetchByAppIdAll(appId);
        try {
            if (StringUtils.isEmpty(appId)) {
                addMessage(model, "应用ID信息为空");
                return form(merchantAppManage, model);
            }
            AuthApp app = authAppService.fetchByAppId(appId);
            if (null == app || !StringUtils.equalsAny(IMerchantConstant.available, app.getStatus())) {
                addMessage(model, "应用未审核或未启用");
                return form(merchantAppManage, model);
            }

            //是否待上线状态
            if (!StringUtils.equalsIgnoreCase("1", merchantAppManage.getStatus())) {
                addMessage(model, "商家应用未达到待上线状态");
                return form(merchantAppManage, model);
            }

            //TODO 验证必要参数 测试微信号、即速应用ID、阿拉丁AppKey
            //设置服务授权路径
            BaseResp step1 = wxOpenAppService.configDomain(merchantAppManage.getAppId(), "set");
            BaseResp step2 = wxOpenAppService.bindTester(appId, merchantAppManage.getAdminId());

            // 绑定测试用户
            if(StringUtils.isNotEmpty(testers)){
                String[] testerArray = StringUtils.split(testers,',');
                for (String tester:testerArray) {
                    wxOpenAppService.bindTester(appId, tester);
                }
            }

            BaseResp step3 = wxOpenAppService.commitCode(merchantAppManage);
            BaseResp step4 = wxOpenAppService.getQrcode(appId);
            BaseResp step5 = wxOpenAppService.fetchCategory(appId);
            BaseResp step6 = wxOpenAppService.fetchPage(appId);

            if (step3.isSuccess() && step4.isSuccess() && step5.isSuccess()) {
                merchantAppManage.setStatus("2");
                addMessage(redirectAttributes, "提交成功");
            } else {
                merchantAppManage.setStatus("-1");
                addMessage(redirectAttributes, "提交失败");
            }

            merchantAppManageService.save(merchantAppManage);

        } catch (Exception e) {
            addMessage(redirectAttributes, "系统错误");
        }
        return "redirect:" + Global.getAdminPath() + "/xiaodian/merchant/merchantAppManage/form?id=" + merchantAppManage.getId();
    }

    @RequiresPermissions("xiaodian:merchant:merchantAppManage:edit")
    @RequestMapping(value = "online")
    public String online(MerchantApp merchantApp,  RedirectAttributes redirectAttributes) {
        MerchantAppManage merchantAppManage = merchantApp.getMerchantAppManage();
        try {
            //提交代码至微信平台审核
            String appId = merchantAppManage.getAppId();
            merchantAppManage = merchantAppManageService.fetchByAppIdAll(appId);

            BaseResp resp = wxOpenAppService.submitAudit(appId, merchantApp.getCategoryLists(), merchantApp.getTags());
            if (resp.isSuccess()) {
                addMessage(redirectAttributes, "提交完成");
                merchantAppManage.setStatus("3");
            } else {
                merchantAppManage.setStatus("-1");
                addMessage(redirectAttributes, "提交失败");
            }
            if (null != merchantAppManage) {
                merchantAppManageService.save(merchantAppManage);
            }

        } catch (Exception e) {
            addMessage(redirectAttributes, "系统错误");
        }

        return "redirect:" + Global.getAdminPath() + "/xiaodian/merchant/merchantAppManage/form?id=" + merchantAppManage.getId();
    }

    @RequiresPermissions("xiaodian:merchant:merchantAppManage:edit")
    @RequestMapping(value = "checkStatus")
    public String checkStatus(MerchantAppManage merchantAppManage, Model model, RedirectAttributes redirectAttributes) {
        //提交代码至微信平台审核
        String appId = merchantAppManage.getAppId();
        merchantAppManage = merchantAppManageService.fetchByAppIdAll(appId);
        try {
            BaseResp resp = wxOpenAppService.fetchAuditResult(appId);
            if (resp.isSuccess()) {
                WxMaCodeAuditStatus wxResp = (WxMaCodeAuditStatus) resp.getExt();
                String respStatus = wxResp.getStatus()+"";
                //微信返回状态与 数据字典 对应
//                status	审核状态，其中0为审核成功，1为审核失败，2为审核中 3为已撤回
//                reason	当status=1，审核被拒绝时，返回的拒绝原因
                if(StringUtils.equalsAnyIgnoreCase("1",respStatus)){
                    merchantAppManage.setStatus("-2");
                    addMessage(redirectAttributes, wxResp.getReason());
                    merchantAppManage.setRemarks(wxResp.getReason());
                }
                if(StringUtils.equalsAnyIgnoreCase("0",respStatus)){
                    merchantAppManage.setStatus("4");
                    addMessage(redirectAttributes, "微信审核成功");
                }
                if(StringUtils.equalsAnyIgnoreCase("2",respStatus)){
                    addMessage(redirectAttributes, "微信审核中...");
                }
                if(StringUtils.equalsAnyIgnoreCase("3",respStatus)){
                    addMessage(redirectAttributes, "已撤回");
                }
            } else {
                merchantAppManage.setStatus("-1");
                addMessage(redirectAttributes, "提交失败");
            }
            merchantAppManageService.save(merchantAppManage);
        } catch (Exception e) {
            addMessage(redirectAttributes, "系统错误");
        }

        return "redirect:" + Global.getAdminPath() + "/xiaodian/merchant/merchantAppManage/form?id=" + merchantAppManage.getId();
    }


    @RequiresPermissions("xiaodian:merchant:merchantAppManage:edit")
    @RequestMapping(value = "publish")
    public String publish(MerchantAppManage merchantAppManage, Model model, RedirectAttributes redirectAttributes) {
        //提交代码至微信平台审核
        String appId = merchantAppManage.getAppId();
        try {
            BaseResp resp = wxOpenAppService.publishApp(appId);
            if (resp.isSuccess()) {
                addMessage(redirectAttributes, "发布上线成功");
                merchantAppManage.setStatus("6");
                merchantAppManageService.save(merchantAppManage);
            } else {
                merchantAppManage.setStatus("-1");
                addMessage(redirectAttributes, "提交失败");
            }
        } catch (Exception e) {
            addMessage(redirectAttributes, "系统错误");
        }

        return "redirect:" + Global.getAdminPath() + "/xiaodian/merchant/merchantAppManage/form?id=" + merchantAppManage.getId();
    }



}