package com.haohan.platform.service.sys.modules.weixin.open.api.inf;

/**
 * Created by zgw on 2017/12/25.
 */
public interface IWxOpenMpService {
   //公众号调用或第三方代公众号调用对公众号的所有API调用（包括第三方代公众号调用）次数进行清零
   String api_clear_quota ="https://api.weixin.qq.com/cgi-bin/clear_quota?access_token=%s";

   //1. 获取公众号关联的小程序
    String api_app_list = "https://api.weixin.qq.com/cgi-bin/wxopen/wxamplinkget?access_token=%s";

    //2. 关联小程序
    String api_app_link = "https://api.weixin.qq.com/cgi-bin/wxopen/wxamplink?access_token=%s";

    //3. 解除已关联的小程序
    String api_app_unlink = "https://api.weixin.qq.com/cgi-bin/wxopen/wxampunlink?access_token=%s";



}


