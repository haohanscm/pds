package com.haohan.platform.service.sys.modules.weixin.open.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.EnumUtils;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.weixin.open.api.entity.CategoryList;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import me.chanjar.weixin.common.error.WxErrorException;

import java.io.IOException;
import java.util.List;

/**
 * Created by zgw on 2017/12/25.
 */
public interface IWxOpenAppService {

    boolean isTest = Boolean.valueOf(DictUtils.getDictValue("是否测试","isTest","true"));

    enum WxOpen_App_Api {

        configDomain("修改服务器地址", "1", "0", "0","POST",api_change_service_url),
        bindTester("绑定测试用户", "2", "0", "2","POST",api_member_bind),
        codeCommit("上传代码", "3", "0", "1","POST",api_code_commit),
        getQrcode("获取体验二维码", "4", "0", "1","GET",api_qrcode),
        fetchCategory("获取可选类目", "5", "0", "3","GET",api_category),
        fetchPage("获取页面配置", "6", "0", "3","GET",api_page),
        submitAudit("提交代码审核", "7", "0", "3","POST",api_code_submit_audit),
        fetchAuditResult("获取审核结果", "8", "0", "3","GET",api_app_latest_auditstatus),
        publishApp("发布上线", "9", "0", "3","POST",api_pubish_app),
        unbindTester("解除绑定测试用户","10","0","2","POST",api_member_unbind),
//        configDomain("","","",""),
//        configDomain("","","",""),
//        configDomain("","","",""),
//        configDomain("","","",""),
        ;

        private String stepName;
        private String stepNo;
        private String channel;
        private String opType;
        private String method;
        private String apiUrl;

        WxOpen_App_Api(String stepName, String stepNo, String channel, String opType,String method,String apiUrl) {
            this.stepName = stepName;
            this.stepNo = stepNo;
            this.channel = channel;
            this.opType = opType;
            this.method = method;
            this.apiUrl=apiUrl;
            EnumUtils.put(getClass().getName().toString() + stepNo, this);
        }

        public static WxOpen_App_Api valueOfApi(String stepNo) {
            Object obj = EnumUtils.get(IWxOpenAppService.WxOpen_App_Api.class.getName().toString() + stepNo);
            if (null != obj) {
                return (WxOpen_App_Api) obj;
            }
            return null;
        }

        public String getStepName() {
            return stepName;
        }

        public void setStepName(String stepName) {
            this.stepName = stepName;
        }

        public String getStepNo() {
            return stepNo;
        }

        public void setStepNo(String stepNo) {
            this.stepNo = stepNo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getOpType() {
            return opType;
        }

        public void setOpType(String opType) {
            this.opType = opType;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }
    }

    //修改服务器地址 POST
    String api_change_service_url = "https://api.weixin.qq.com/wxa/modify_domain?access_token=%s";

    String service_domain[] = {"weixin.haohanwork.com", "xcx.yingyonghao8.com","log.aldwx.com"};

    enum Method {
        GET, POST
    }

    int SUCCESS_STATUS = 0;

    BaseResp configDomain(String appId, String action) throws WxErrorException;

    //一、成员管理
    //1、绑定微信用户为小程序体验者
    String api_member_bind = "https://api.weixin.qq.com/wxa/bind_tester?access_token=%s";

    BaseResp bindTester(String appId, String wechatId) throws WxErrorException;

    //解除绑定小程序的体验者
    String api_member_unbind = "https://api.weixin.qq.com/wxa/unbind_tester?access_token=%s";

    BaseResp unbindTester(String appId, String wechatId) throws WxErrorException;

    //二、代码管理
    //1、为授权的小程序帐号上传小程序代码
    String api_code_commit = "https://api.weixin.qq.com/wxa/commit?access_token=%s";

    BaseResp commitCode(MerchantAppManage merchantAppManage) throws WxErrorException;

    //2、获取体验小程序的体验二维码
    String api_qrcode = "https://api.weixin.qq.com/wxa/get_qrcode?access_token=%s";

    BaseResp getQrcode(String appId) throws IOException;

    //3、获取授权小程序帐号的可选类目
    String api_category = "https://api.weixin.qq.com/wxa/get_category?access_token=%s";

    BaseResp fetchCategory(String appId);

    //4、获取小程序的第三方提交代码的页面配置（仅供第三方开发者代小程序调用）
    String api_page = "https://api.weixin.qq.com/wxa/get_page?access_token=%s";

    BaseResp fetchPage(String appId) throws WxErrorException;

    //5、将第三方提交的代码包提交审核（仅供第三方开发者代小程序调用）POST
    String api_code_submit_audit = "https://api.weixin.qq.com/wxa/submit_audit?access_token=%s";

    BaseResp submitAudit(String appId, List<CategoryList> list,String tags) throws WxErrorException;
    //6、审核结果notify 服务端接收

    //7、查询某个指定版本的审核状态（仅供第三方代小程序调用）POST
    String api_app_audit_status = "https://api.weixin.qq.com/wxa/get_auditstatus?access_token=%s";

    //8、查询最新一次提交的审核状态（仅供第三方代小程序调用）GET
    String api_app_latest_auditstatus = "https://api.weixin.qq.com/wxa/get_latest_auditstatus?access_token=%s";

    BaseResp fetchAuditResult(String appId) throws WxErrorException;

    //9、发布已通过审核的小程序（仅供第三方代小程序调用）
    String api_pubish_app = "https://api.weixin.qq.com/wxa/release?access_token=%s";

    BaseResp publishApp(String appId) throws WxErrorException;

    //10、修改小程序线上代码的可见状态（仅供第三方代小程序调用）
    String api_change_visitstatus = "https://api.weixin.qq.com/wxa/change_visitstatus?access_token=%s";

    //11、设置小程序业务域名（仅供第三方代小程序调用）

    //12. 小程序版本回退（仅供第三方代小程序调用）

    //13、获取微信小程序access_token
    String api_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    //13、获取微信小程序任意页面的二维码，二维码无个数限制
    String api_unlimit_qrcode = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";


}

