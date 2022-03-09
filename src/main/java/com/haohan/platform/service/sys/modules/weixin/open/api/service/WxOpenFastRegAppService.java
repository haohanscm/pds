package com.haohan.platform.service.sys.modules.weixin.open.api.service;

import com.haohan.platform.service.sys.modules.weixin.open.api.entity.WxOpenBaseResp;
import com.haohan.platform.service.sys.modules.weixin.open.api.inf.IWxOpenCreateMpConst;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.impl.WxOpenComponentServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 快速创建小程序Service
 * @author shenyu
 * @create 2019/2/25
 */
@Service
public class WxOpenFastRegAppService implements IWxOpenCreateMpConst {
    @Autowired
    private WxOpenApiService wxOpenApiService;

    /**
     * 创建小程序接口
     * @param appid 开放平台appid
     * @param name  企业名（需与工商部门登记信息一致）
     * @param code  企业代码
     * @param codeType  企业代码类型 1：统一社会信用代码（18位） 2：组织机构代码（9位xxxxxxxx-x） 3：营业执照注册号(15位)
     * @param legalWechat   法人微信号
     * @param legalName 法人姓名（绑定银行卡）
     * @param phone 第三方联系电话（方便法人与第三方联系）
     * @return
     * @throws WxErrorException
     */
    public WxOpenBaseResp createMiniApp(String appid, String name, String code, Integer codeType, String legalWechat, String legalName, String phone) throws WxErrorException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("code",code);
        jsonObject.put("code_type",codeType);
        jsonObject.put("legal_persona_wechat",legalWechat);
        jsonObject.put("legal_persona_name",legalName);
        jsonObject.put("component_phone",phone);
        WxOpenComponentServiceImpl service = new WxOpenComponentServiceImpl(wxOpenApiService.fetchByAppId(appid));
        String token = service.getComponentAccessToken(false);
        String action = "create";
        String resp = service.getWxOpenService().post(API_FAST_REGISTER_WEAPP.concat(String.format("?action=%s&component_access_token=%s",action,token)),jsonObject.toString());
        return WxOpenBaseResp.fromJson(resp);
    }

    /**
     * 查询创建任务状态
     * @param appid 开放平台appid
     * @param name  企业名
     * @param legalWechat  法人微信号
     * @param legalName 法人姓名
     * @return
     * @throws WxErrorException
     */
    public WxOpenBaseResp queryRegStatus(String appid,String name,String legalWechat,String legalName) throws WxErrorException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("legal_persona_wechat",legalWechat);
        jsonObject.put("legal_persona_name",legalName);
        WxOpenComponentServiceImpl service = new WxOpenComponentServiceImpl(wxOpenApiService.fetchByAppId(appid));
        String token = service.getComponentAccessToken(false);
        String action = "search";
        String resp = service.getWxOpenService().post(API_FAST_REGISTER_WEAPP.concat(String.format("?action=%s&component_access_token=%s",action,token)),jsonObject.toString());
        return WxOpenBaseResp.fromJson(resp);
    }


}
