package com.haohan.platform.service.sys.modules.xiaodian.api.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOssConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 *
 * 备注:Demo工程编码采用UTF-8
 * 国际短信发送请勿参照此DEMO
 */

@Service
public class AliyunMsgService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId =  DictUtils.getDictValue("accessKey", IOssConstant.AliyunOssType,"xad");
    static final String accessKeySecret = DictUtils.getDictValue("secretKey",IOssConstant.AliyunOssType,"xas");

    static final boolean isSendMobileMsg = Boolean.valueOf(DictUtils.getDictValue("启用状态","send_mobile_msg","false"));

    private  IAcsClient acsClient = null;

    @Autowired
    private MerchantService merchantService;

       {

           try {
               //可自助调整超时时间
               System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
               System.setProperty("sun.net.client.defaultReadTimeout", "10000");
           //初始化acsClient,暂不支持region化
           IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
           DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            acsClient = new DefaultAcsClient(profile);
           } catch (Exception e) {
               e.printStackTrace();
           }
      }


      public BaseResp sendMsgToMerchant(OrderPayRecord payRecord){

          BaseResp resp = BaseResp.newError();
          if(!isSendMobileMsg){
              resp.setMsg("不发送短信");
              return resp;
          }
          SendSmsResponse sendSmsResponse = null;

          try {

             Merchant merchant =   merchantService.get(payRecord.getMerchantId());
              //组装请求对象-具体描述见控制台-文档部分内容
              SendSmsRequest request = new SendSmsRequest();
              //必填:待发送手机号
              request.setPhoneNumbers(merchant.getTelephone());
              //必填:短信签名-可在短信控制台中找到
              request.setSignName("小店");
              //必填:短信模板-可在短信控制台中找到
              if (StringUtils.equals("wechat",payRecord.getPayChannel())){
                  request.setTemplateCode("SMS_133261956");
              }
              if (StringUtils.equals("alipay",payRecord.getPayChannel())){
                  request.setTemplateCode("SMS_142149883");
              }

              //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
              Map<String,String> params = new HashMap<>();
              params.put("orderAmount",payRecord.getOrderAmount().toString());
              request.setTemplateParam(JacksonUtils.toJson(params));
               sendSmsResponse = acsClient.getAcsResponse(request);
              if(StringUtils.equalsAnyIgnoreCase("OK",sendSmsResponse.getCode(),sendSmsResponse.getMessage())){
                  resp.putStatus(RespStatus.SUCCESS);
                  return resp;
              }
              resp.setMsg(sendSmsResponse.getMessage());
              return resp;

          }catch (Exception e){

              e.printStackTrace();
              return resp;
          }finally {
              logger.debug("sendMsgReq:{}\n,respMsg:{}\n",JacksonUtils.toJson(payRecord),JacksonUtils.toJson(sendSmsResponse));
          }

      }


    public BaseResp sendRegMsg(String telephone,String code){
        BaseResp resp = BaseResp.newError();
        if(!isSendMobileMsg){
            resp.setMsg("不发送短信");
            return resp;
        }
        SendSmsResponse sendSmsResponse = null;

        try {

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(telephone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("注册验证");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("SMS_62330288");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            Map<String,String> params = new HashMap<>();
            params.put("product","浩瀚小店");
            params.put("code",code);
            request.setTemplateParam(JacksonUtils.toJson(params));

            sendSmsResponse = acsClient.getAcsResponse(request);
            if(StringUtils.equalsAnyIgnoreCase("OK",sendSmsResponse.getCode(),sendSmsResponse.getMessage())){
                resp.putStatus(RespStatus.SUCCESS);
                return resp;
            }
            resp.setMsg(sendSmsResponse.getMessage());
            return resp;

        }catch (Exception e){

            e.printStackTrace();
            return resp;
        }finally {
            logger.debug("sendMsgReq:{}\n,respMsg:{}\n",telephone,"-"+code,JacksonUtils.toJson(sendSmsResponse));
        }

    }





    public static SendSmsResponse sendSms() throws ClientException {


        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers("15001231346");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("注册验证");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_62330288");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"product\":\"浩瀚小店\", \"code\":\"123456\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    public static void main(String[] args) throws ClientException, InterruptedException {

        //发短信
        SendSmsResponse response = sendSms();
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

        Thread.sleep(3000L);

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }

    }
}
