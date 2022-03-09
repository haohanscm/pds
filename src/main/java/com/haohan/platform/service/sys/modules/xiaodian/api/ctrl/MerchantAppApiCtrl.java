package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import cn.binarywang.wx.miniapp.bean.code.WxMaCodeAuditStatus;
import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.weixin.open.api.entity.CategoryList;
import com.haohan.platform.service.sys.modules.weixin.open.api.entity.WxBaseResp;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenAppService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AppOnlineManage;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AppOnlineManageService;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.MerchantAppReq;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dy
 * @date 2020/7/22
 * 微信小程序上线相关接口 (scm使用)
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/app")
public class MerchantAppApiCtrl extends BaseController {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;
    @Autowired
    private MerchantAppExtService merchantAppExtService;
    @Autowired
    private ShopTemplateExtInfoService shopTemplateExtInfoService;
    @Autowired
    private WxOpenAppService wxOpenAppService;
    @Autowired
    private AppOnlineManageService appOnlineManageService;

    /**
     * 创建(修改)商家应用扩展
     * 小程序发布测试环境
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "onlineBeta")
    @ResponseBody
    public String onlineBate(@Validated MerchantAppReq req) {
        String merchantId = req.getMerchantId();
        String shopId = req.getShopId();
        String appId = req.getAppId();
        String testers = req.getTesters();
        BaseResp resp = BaseResp.newError();
        Merchant merchant = merchantService.get(merchantId);
        Shop shop = shopService.get(shopId);
        if (null == merchant || null == shop) {
            resp.setMsg("商家有误");
            return resp.toJson();
        }
        AuthApp app = authAppService.fetchByAppId(appId);
        if (null == app || !StringUtils.equalsAny(IMerchantConstant.available, app.getStatus())) {
            resp.setMsg("应用未审核或未启用");
            return resp.toJson();
        }

        MerchantAppManage merchantAppManage = merchantAppManageService.fetchMerchantApp(merchantId, appId);
        if (null == merchantAppManage) {
            resp.setMsg("未找到商家应用");
            return resp.toJson();
        }
        // 创建商家应用扩展, 已存在不修改

        //根据模板扩展参数创建商家应用扩展信息
        List<ShopTemplateExtInfo> shopTemplateExtInfos = shopTemplateExtInfoService.findByTemplateId(merchantAppManage.getTemplateId());

        if (CollectionUtils.isNotEmpty(shopTemplateExtInfos)) {
            // 如果不存在扩展信息则创建
            if (!merchantAppExtService.isExistExtInfo(merchantAppManage)) {
                merchantAppExtService.transToMerchantAppExt(shopTemplateExtInfos, merchantAppManage, merchant, shop, true);
            }
        }
        merchantAppManage.setAdminId(req.getAdminId());
        merchantAppManage.setAdminName(req.getAdminName());
        // 应用扩展信息
        MerchantAppExtInfo extInfo = new MerchantAppExtInfo();
        extInfo.setShopId(shopId);
        extInfo.setShopName(shop.getName());
        extInfo.setPartnerId(req.getPartnerId());
        extInfo.setVersionNo(req.getVersionNo());
        extInfo.setVersionDesc(req.getVersionDesc());
        merchantAppManage.setExt(extInfo.toJson());

        // 小程序发布测试环境

        //设置服务授权路径
        BaseResp step1 = wxOpenAppService.configDomain(merchantAppManage.getAppId(), "set");
        if (StringUtils.isNotEmpty(req.getAdminId())) {
            wxOpenAppService.bindTester(appId, req.getAdminId());
        }
        // 绑定测试用户
        if (StringUtils.isNotEmpty(testers)) {
            String[] testerArray = StringUtils.split(testers, ',');
            for (String tester : testerArray) {
                wxOpenAppService.bindTester(appId, tester);
            }
        }

        BaseResp step3 = wxOpenAppService.commitCode(merchantAppManage);
        if (step3.isSuccess()) {
            BaseResp step4 = wxOpenAppService.getQrcode(appId);
            BaseResp step5 = wxOpenAppService.fetchCategory(appId);
            BaseResp step6 = wxOpenAppService.fetchPage(appId);
            if (step4.isSuccess() && step5.isSuccess()) {
                merchantAppManage.setStatus("2");
                resp.putStatus(RespStatus.SUCCESS);
            }
        }
        if (!resp.isSuccess()) {
            merchantAppManage.setStatus("-1");
        }
        merchantAppManageService.save(merchantAppManage);
        return resp.toJson();
    }

    /**
     * 提交微信审核
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "online")
    @ResponseBody
    public String online(@Validated MerchantAppReq req) {
        String merchantId = req.getMerchantId();
        String appId = req.getAppId();
        BaseResp resp = BaseResp.newError();
        MerchantAppManage merchantAppManage = merchantAppManageService.fetchMerchantApp(merchantId, appId);
        if (null == merchantAppManage) {
            resp.setMsg("商家应用有误，不存在或状态不支持");
            return resp.toJson();
        }
        // 服务类目
        AppOnlineManage online = new AppOnlineManage();
        online.setStatus("200");
        online.setAppId(merchantAppManage.getAppId());
        online.setStepNo("5");
        List<AppOnlineManage> list = appOnlineManageService.findList(online);
        if (CollectionUtils.isEmpty(list)) {
            resp.setMsg("商家应用上线记录有误");
            return resp.toJson();
        }
        String respParams = list.get(0).getRespParams();
        WxBaseResp wxBaseResp = JacksonUtils.readValue(respParams, WxBaseResp.class);
        List<CategoryList> listList = null == wxBaseResp ? new ArrayList<>() : wxBaseResp.getCategoryLists();
        BaseResp r = wxOpenAppService.submitAudit(appId, listList, "");
        if (r.isSuccess()) {
            r.putStatus(RespStatus.SUCCESS);
            merchantAppManage.setStatus("3");
        } else {
            resp.setMsg(r.getExt().toString());
            merchantAppManage.setStatus("-1");
        }
        merchantAppManageService.save(merchantAppManage);
        return resp.toJson();
    }

    /**
     * 微信审核状态查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "checkStatus")
    @ResponseBody
    public String checkStatus(@Validated MerchantAppReq req) {
        String merchantId = req.getMerchantId();
        String appId = req.getAppId();
        BaseResp baseResp = BaseResp.newError();
        MerchantAppManage merchantAppManage = merchantAppManageService.fetchMerchantApp(merchantId, appId);
        if (null == merchantAppManage) {
            baseResp.setMsg("商家应用有误");
            return baseResp.toJson();
        }
        try {
            BaseResp resp = wxOpenAppService.fetchAuditResult(appId);
            if (resp.isSuccess()) {
                baseResp.putStatus(RespStatus.SUCCESS);
                WxMaCodeAuditStatus wxResp = (WxMaCodeAuditStatus) resp.getExt();
                String respStatus = wxResp.getStatus() + "";
                baseResp.setExt(respStatus);
                //微信返回状态与 数据字典 对应
//                status	审核状态，其中0为审核成功，1为审核失败，2为审核中 3为已撤回
//                reason	当status=1，审核被拒绝时，返回的拒绝原因
                if (StringUtils.equalsAnyIgnoreCase("1", respStatus)) {
                    merchantAppManage.setStatus("-2");
                    baseResp.setMsg(wxResp.getReason());
                    merchantAppManage.setRemarks(wxResp.getReason());
                }
                if (StringUtils.equalsAnyIgnoreCase("0", respStatus)) {
                    merchantAppManage.setStatus("4");
                    baseResp.setMsg("微信审核成功");
                }
                if (StringUtils.equalsAnyIgnoreCase("2", respStatus)) {
                    baseResp.setMsg("微信审核中...");
                }
                if (StringUtils.equalsAnyIgnoreCase("3", respStatus)) {
                    baseResp.setMsg("已撤回");
                }
            } else {
                merchantAppManage.setStatus("-1");
                baseResp.setMsg("提交失败");
            }
        } catch (Exception e) {
            merchantAppManage.setStatus("-1");
            baseResp.setMsg("提交失败");
        }
        merchantAppManageService.save(merchantAppManage);
        return baseResp.toJson();
    }

    /**
     * 正式发布上线
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "publish")
    @ResponseBody
    public String publish(@Validated MerchantAppReq req) {
        String merchantId = req.getMerchantId();
        String appId = req.getAppId();
        BaseResp baseResp = BaseResp.newError();
        MerchantAppManage merchantAppManage = merchantAppManageService.fetchMerchantApp(merchantId, appId);
        if (null == merchantAppManage || !StringUtils.equals(merchantAppManage.getStatus(), IMerchantConstant.MerchantAppStatus.wx_check_success.getCode())) {
            baseResp.setMsg("商家应用有误，不存在或状态不支持");
            return baseResp.toJson();
        }
        try {
            BaseResp resp = wxOpenAppService.publishApp(appId);
            if (resp.isSuccess()) {
                baseResp.putStatus(RespStatus.SUCCESS);
                merchantAppManage.setStatus("6");
            } else {
                merchantAppManage.setStatus("-1");
                baseResp.setMsg("提交失败");
            }
        } catch (Exception e) {
            baseResp.setMsg("提交失败");
        }
        merchantAppManageService.save(merchantAppManage);
        return baseResp.toJson();
    }
}
