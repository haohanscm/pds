package com.haohan.platform.service.sys.modules.weixin.open.api.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaDomainAction;
import cn.binarywang.wx.miniapp.bean.code.*;
import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.entity.BaseStatus;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.weixin.open.api.entity.*;
import com.haohan.platform.service.sys.modules.weixin.open.api.inf.IWxOpenAppService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AppOnlineManage;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AppOnlineManageService;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOssFileManageBiz;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppExtInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopTemplate;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppExtService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopTemplateService;
import com.haohan.platform.service.sys.modules.xiaodian.util.OssUploadUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by zgw on 2017/12/25.
 */

@Service
public class WxOpenAppService implements IWxOpenAppService {

    @Autowired
    private WxOpenApiService wxOpenApiService;

    @Autowired
    private AuthAppService authAppService;

    @Autowired
    private ShopTemplateService shopTemplateService;

    @Autowired
    private AppOnlineManageService appOnlineManageService;

    @Autowired
    private MerchantAppManageService merchantAppManageService;

    @Autowired
    private MerchantAppExtService merchantAppExtService;
    @Autowired
    private IOssFileManageBiz iOssFileManageBiz;


    @Override
    public BaseResp configDomain(String appId, String action) {
        BaseResp resp = BaseResp.newError();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.configDomain);
        onlineManage.setReqTime(new Date());
        onlineManage.setAppId(appId);
        //设置请求参数
        List<String> domains = new ArrayList<>();
        List<Dict> dicts = DictUtils.getDictList("open_service_domain");
        for (Dict dict : dicts) {
            domains.add(dict.getValue());
        }
        //reqData
        WxMaService wxMaService = fetchWxMaService(appId);
        WxMaDomainAction wxMaDomainAction = WxMaDomainAction.builder().action(action)
                .downloadDomain(domains)
                .requestDomain(domains)
                .uploadDomain(domains)
                .wsRequestDomain(domains)
                .build();
        onlineManage.setReqParams(wxMaDomainAction.toJson());
        WxMaDomainAction domainAction = null;
        WxErrorException exception = null;
        try {
            domainAction = wxMaService.getSettingService().modifyDomain(wxMaDomainAction);
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }
        onlineManage.setReqParams(domainAction.toJson());
        appOnlineLog(resp, onlineManage, exception);
        return resp;
    }


    @Override
    public BaseResp bindTester(String appId, String wechatId) {
        BaseResp resp = BaseResp.newError();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.bindTester);
        onlineManage.setReqTime(new Date());
        onlineManage.setAppId(appId);
        //设置请求参数
        WxBaseReq req = new WxBaseReq();
        req.setWechatid(wechatId);
        onlineManage.setReqParams(req.toJson());
        onlineManage.setReqUrl(api_member_bind);
        WxErrorException exception = null;
        try {
            fetchWxMaService(appId).getSettingService().bindTester(wechatId);
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }
        onlineManage.setStatus(RespStatus.SUCCESS.getCode().toString());
        onlineManage.setRespParams(RespStatus.SUCCESS.getMsg());
        appOnlineLog(resp,onlineManage, exception);
        return resp;
    }


    @Override
    public BaseResp unbindTester(String appId, String wechatId) {
        BaseResp resp = new BaseResp();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.unbindTester);
        onlineManage.setReqTime(new Date());
        onlineManage.setAppId(appId);
        //设置请求参数
        WxBaseReq req = new WxBaseReq();
        req.setWechatid(wechatId);
        onlineManage.setReqParams(req.toJson());
        WxErrorException exception = null;
        try {
            fetchWxMaService(appId).getSettingService().unbindTester(wechatId);
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }
        onlineManage.setReqUrl(api_member_unbind);
        appOnlineLog(resp, onlineManage, exception);
        return resp;
    }

    public BaseResp commitCode(MerchantAppManage merchantAppManage) {
        BaseResp baseResp = BaseResp.newError();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.codeCommit);
        onlineManage.setReqTime(new Date());
        String appId = merchantAppManage.getAppId();

        onlineManage.setAppId(appId);

        ShopTemplate shopTemplate = shopTemplateService.get(merchantAppManage.getTemplateId());
        if (null == shopTemplate) {
            return BaseResp.newInstance(RespStatus.NOT_FOUND_ERROR);
        }

        MerchantAppExtInfo ext = merchantAppManage.fromExt();

        HashMap<String, Object> map = new HashMap<>();
        //微信小程序必填参数项
        map.put("appId", appId);
        map.put("appName", merchantAppManage.getAppName());
        map.put("merchantId", merchantAppManage.getMerchantId());
        map.put("merchantName", merchantAppManage.getMerchantName());
        map.put("orderType", "1");//零售
        map.put("payChannel", "wechat");//与渠道参数保持一致
        map.put("payType", "04");//小程序
        map.put("jisuAppId", merchantAppManage.getJisuAppId());
        map.put("hhxdUrl", DictUtils.getDictValue("API地址", "service_api_url", "https://wxapp.haohanshop.com/"));

        map.putAll(ext.toMap());

        //商家应用扩展信息
        Map<String, Object> extMap = merchantAppExtService.fetchAppExtInfo(appId);
        if (MapUtils.isNotEmpty(extMap)) {
            map.putAll(extMap);
        }

        Long temId = Long.valueOf(shopTemplate.getWxModelId());
        WxMaCodeExtConfig codeExtConfig = WxMaCodeExtConfig.builder()
                .extAppid(appId)
                .directCommit(false)
                .extEnable(true)
                .ext(map)
                .build();
        onlineManage.setReqParams(JacksonUtils.toJson(codeExtConfig));
        WxMaCodeCommitRequest codeCommit = WxMaCodeCommitRequest.builder()
                .templateId(temId)
                .extConfig(codeExtConfig)
                .userDesc(ext.getVersionDesc())
                .userVersion(ext.getVersionNo()).build();
        WxErrorException exception = null;
        try {
            fetchWxMaService(appId).getCodeService().commit(codeCommit);
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }
        appOnlineLog(baseResp, onlineManage, exception);
        return baseResp;
    }

    // 小程序体验码
    @Override
    public BaseResp getQrcode(String appId) {
        BaseResp resp = BaseResp.newError();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.getQrcode);
        onlineManage.setReqTime(new Date());
        //返回图片流  记录onlineManage
        AuthApp authApp = authAppService.fetchByAppId(appId);
        if (null == authApp) {
            return BaseResp.newInstance(RespStatus.NOT_FOUND_ERROR);
        }
        onlineManage.setAppId(appId);
        onlineManage.setReqTime(new Date());
        onlineManage.setStatus(RespStatus.SUCCESS.getCode().toString());
        String url = String.format(api_qrcode, authApp.getAccessToken());
        onlineManage.setReqUrl(url);
        WxErrorException exception = null;
        String filePath = "";
        try {
         byte[] respParams =  fetchWxMaService(appId).getCodeService().getQrCode("");
            String wwwRootDir = DictUtils.getDictValue("服务目录", "service_dir", "/data/www/default/wxapp.haohanshop.com/files/");
            Path path = Paths.get(wwwRootDir);
            if (Files.notExists(path)) {
                    Files.createDirectories(path);
                }
             filePath = wwwRootDir + appId.concat("_qrcode.jpg");
            FileOutputStream outputStream = new FileOutputStream(new File(filePath));
                outputStream.write(respParams);
            //二维码转存
//            HttpClientUtils.getInstance().download(url, filePath);
            //上传到云服务
            String fileName = OssUploadUtils.getFilePath(appId, "04") + appId.concat("_qrcode.jpg");//体验二维码type=04
             resp = iOssFileManageBiz.uploadTempFile(filePath, fileName);
            onlineManage.setStatus(resp.getCode().toString());
            if (resp.isSuccess()) {
                onlineManage.setRespParams(resp.getExt().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof  WxErrorException){
                exception = (WxErrorException) e;
            }else {
                resp.putStatus(RespStatus.ERROR);
                resp.setMsg("获取体验二维码失败");
                return resp;
            }
        }
        appOnlineLog(resp,onlineManage,exception);
        return resp;
    }

    @Override
    public BaseResp fetchCategory(String appId) {
        BaseResp resp = BaseResp.newError();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.fetchCategory);
        onlineManage.setReqTime(new Date());
        onlineManage.setAppId(appId);
        WxErrorException exception = null;
        try {
            WxBaseResp wxBaseResp = new WxBaseResp();
            List<WxMaCategory> wxMaCategoryList = fetchWxMaService(appId).getCodeService().getCategory();
            wxBaseResp.copyCategoryList(wxMaCategoryList);
            onlineManage.setRespParams(JacksonUtils.toJson(wxBaseResp));
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }
//        return callApi(api_category, onlineManage);
        onlineManage.setReqUrl(api_category);
        appOnlineLog(resp,onlineManage,exception);
        return resp;
    }

    public BaseResp fetchPage(String appId) {
        BaseResp resp = BaseResp.newError();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.fetchPage);
        onlineManage.setReqTime(new Date());
        onlineManage.setAppId(appId);

        List<String> pages = null;
        WxErrorException exception = null;
        try {
            pages = fetchWxMaService(appId).getCodeService().getPage();
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }
        onlineManage.setRespParams(JacksonUtils.toJson(pages));
        onlineManage.setReqUrl(api_page);
        appOnlineLog(resp, onlineManage, exception);
        return resp;
    }

    @Override
    public BaseResp submitAudit(String appId, List<CategoryList> list, String tags) {

        BaseResp resp = BaseResp.newError();

        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.submitAudit);
        onlineManage.setReqTime(new Date());
        onlineManage.setAppId(appId);
        WxBaseReq req = new WxBaseReq();

        List<ItemList> itemLists = new ArrayList<>();
        //TODO 设置item  只设置首页
        // 从上线记录步骤6中读取页面路径
        AppOnlineManage online = new AppOnlineManage();
        online.setStatus("200");
        online.setAppId(onlineManage.getAppId());
        online.setStepNo("6");
        List<AppOnlineManage> listForPages = appOnlineManageService.findList(online);
        // 页面地址

        // 设置路径
        String address = "";
        int i = 0;
        List<String> pages = null;
        if (CollectionUtils.isNotEmpty(listForPages)) {
            String respParams = listForPages.get(0).getRespParams();
            pages = JacksonUtils.readValue(respParams, new ArrayList<String>().getClass());
        }

        List<WxMaCategory> wxMaCategoryList = new ArrayList<>();

        for (CategoryList category : list) {
            // 只设置首页
            if (0 < i) {
                break;
            }

            if (pages != null && pages.size() > i) {
                address = pages.get(i);
            }


            WxMaCategory maCategory = WxMaCategory.builder()
                    .firstClass(category.getFirstClass())
                    .secondClass(category.getSecondClass())
                    .thirdClass(category.getThirdClass())
                    .firstId((long) category.getFirstId())
                    .secondId((long) category.getSecondId())
                    .thirdId((long) category.getThirdId())
                    .address(address)
                    .tag(tags)
                    .title("产品").build();
            wxMaCategoryList.add(maCategory);
            i++;
        }
        req.setItemLists(itemLists);

        onlineManage.setReqParams(req.toJson());
        WxMaCodeSubmitAuditRequest submitAudit = WxMaCodeSubmitAuditRequest.builder().
                itemList(
                        wxMaCategoryList
                ).build();

        onlineManage.setReqUrl(api_code_submit_audit);
        WxErrorException exception = null;
        long auditId = 0;
        try {
            auditId = fetchWxMaService(appId).getCodeService().submitAudit(submitAudit);
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }
        onlineManage.setRespParams(auditId + "");
        appOnlineLog(resp, onlineManage, exception);
        return resp;
    }


    public BaseResp fetchAuditResult(String appId) {
        BaseResp resp = BaseResp.newSuccess();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.fetchAuditResult);
        onlineManage.setReqTime(new Date());

        onlineManage.setAppId(appId);

        onlineManage.setReqUrl(api_app_latest_auditstatus);

        WxMaCodeAuditStatus auditStatus = null;
        WxErrorException exception = null;
        try {
            auditStatus = fetchWxMaService(appId).getCodeService().getLatestAuditStatus();
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }

        onlineManage.setRespParams(JacksonUtils.toJson(auditStatus));

        resp.setExt(auditStatus);

        appOnlineLog(resp, onlineManage, exception);

        return resp;
    }

    public BaseResp publishApp(String appId) {

        BaseResp baseResp = new BaseResp();
        AppOnlineManage onlineManage = new AppOnlineManage(WxOpen_App_Api.publishApp);
        onlineManage.setReqTime(new Date());
        onlineManage.setAppId(appId);
        //按文档设置为空
        onlineManage.setReqParams("{}");

        WxMaService wxMaService = fetchWxMaService(appId);
        WxErrorException exception = null;
        try {
            wxMaService.getCodeService().release();
        } catch (WxErrorException e) {
            e.printStackTrace();
            exception = e;
        }
        appOnlineLog(baseResp, onlineManage, exception);
        return baseResp;
    }


    private void appOnlineLog(BaseResp resp, AppOnlineManage onlineManage, WxErrorException wxExcepiton) {

        if (null != wxExcepiton) {
            onlineManage.setRespParams(JacksonUtils.toJson(wxExcepiton));
            onlineManage.setStatus(RespStatus.ERROR.getCode().toString());
            resp.putStatus(RespStatus.ERROR);
            resp.setExt(wxExcepiton.getError().getErrorMsg());
        }else {
            resp.putStatus(RespStatus.SUCCESS);
        }
        onlineManage.setRespTime(new Date());
        String appId = onlineManage.getAppId();
        onlineManage.setStatus(RespStatus.SUCCESS.getCode().toString());
        //设置应用名称、应用ID
        MerchantAppManage appManage = merchantAppManageService.fetchByAppIdAll(appId);
        if (null != appManage) {
            onlineManage.setMerchantId(appManage.getMerchantId());
            onlineManage.setMerchantName(appManage.getMerchantName());
            onlineManage.setAppName(appManage.getAppName());
        }
        appOnlineManageService.save(onlineManage);
    }


    private WxMaService fetchWxMaService(String appId) {

        AuthApp authApp = authAppService.fetchByAppId(appId);
        if (null == authApp) {
            return null;
        }
        //发布小程序上线
        WxOpenComponentService wxOpenComponentService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId()).getWxOpenComponentService();

        WxMaService wxMaService = wxOpenComponentService.getWxMaServiceByAppid(appId);

        return wxMaService;

    }


    public BaseResp callApi(String apiUrl, AppOnlineManage onlineManage) {

        String method = onlineManage.getReqMethod();
        String appId = onlineManage.getAppId();
        String reqJson = onlineManage.getReqParams();


        AuthApp authApp = authAppService.fetchByAppId(appId);
        if (null == authApp) {
            return BaseResp.newInstance(RespStatus.NOT_FOUND_ERROR);
        }

        WxOpenComponentService wxOpenComponentService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId()).getWxOpenComponentService();

        WxMaService wxMaService = wxOpenComponentService.getWxMaServiceByAppid(appId);

        String url = String.format(apiUrl, authApp.getAccessToken());

        onlineManage.setAppName(authApp.getAppName());
        onlineManage.setReqUrl(url);
        onlineManage.setReqTime(new Date());
        onlineManage.setStatus(RespStatus.SUCCESS.getCode().toString());
        String resp = null;
        try {
            if (Method.GET.name().equalsIgnoreCase(method)) {
                onlineManage.setReqParams("access_token=" + authApp.getAccessToken());
                resp = wxMaService.get(url, reqJson);
//                resp = get(url, reqJson);
            }
            if (Method.POST.name().equalsIgnoreCase(method)) {
                resp = wxMaService.post(url, reqJson);
//                resp = post(url, reqJson);
            }
            onlineManage.setRespTime(new Date());
            onlineManage.setRespParams(resp);

        } catch (WxErrorException e) {
            onlineManage.setStatus(RespStatus.ERROR.getCode().toString());
            onlineManage.setRespParams(JsonMapper.toJsonString(e.getError()));
        } finally {
            //设置应用名称、应用ID
            MerchantAppManage appManage = merchantAppManageService.fetchByAppIdAll(appId);
            if (null != appManage) {
                onlineManage.setMerchantId(appManage.getMerchantId());
                onlineManage.setMerchantName(appManage.getMerchantName());
                onlineManage.setAppName(appManage.getAppName());
            }
            if (onlineManage.getStatus().equals(RespStatus.ERROR.getCode().toString())) {
                appOnlineManageService.save(onlineManage);
            }
        }
        if (StringUtils.isBlank(resp)) {
            return BaseResp.newInstance(RespStatus.NOT_FOUND_ERROR);
        }
        WxBaseResp wxResp = WxBaseResp.fromJson(resp);
        if (SUCCESS_STATUS == wxResp.getErrCode()) {
            BaseResp baseResp = new BaseResp();
            baseResp.setExt(wxResp);
            baseResp.putStatus(RespStatus.SUCCESS);

            //设置正确返回
            onlineManage.setStatus(RespStatus.SUCCESS.getCode().toString());
            appOnlineManageService.save(onlineManage);
            return baseResp;
        }

        return BaseResp.newInstance(new BaseStatus(wxResp.getErrCode(), wxResp.getErrMsg()));
    }

}
