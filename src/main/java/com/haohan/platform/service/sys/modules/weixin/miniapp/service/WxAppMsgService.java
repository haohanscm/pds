package com.haohan.platform.service.sys.modules.weixin.miniapp.service;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.google.common.collect.Lists;
import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.weixin.miniapp.constant.IWxMiniAppConstant;
import com.haohan.platform.service.sys.modules.weixin.miniapp.entity.QrCodeReqParams;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOssFileManageBiz;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.message.WechatMessageTemplate;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.message.WechatMessageTemplateService;
import com.haohan.platform.service.sys.modules.xiaodian.util.OssUploadUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by zgw on 2018/4/19.
 */
@Service
public class WxAppMsgService implements IWxMiniAppConstant{


    @Autowired
    private WxAppBaseService wxAppBaseService;
    @Qualifier("ossFileManageAliyunBizImpl")
    @Autowired
    private IOssFileManageBiz iOssFileManageBiz;

    @Autowired
    private WechatMessageTemplateService messageTemplateService;



    public BaseResp sendPayMessage(OrderPayRecord payRecord){

        BaseResp resp = BaseResp.newError();

        String appId = payRecord.getAppid();

        try {
            WxMaService wxMaService = wxAppBaseService.fetchByAppId(appId);
            if(null == wxMaService){
                return resp;
            }
        WxMaMsgService maMsgService =  wxMaService.getMsgService();
        if(null == maMsgService){
            resp.setMsg("????????????????????????");
            return resp;
        }

            WechatMessageTemplate messageTemplate =  messageTemplateService.fetchByAppIdAndMsgType(appId,pay_result_notify);
        if(null == messageTemplate){
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return resp;
        }

        if(!IMerchantConstant.available.equalsIgnoreCase(messageTemplate.getStatus())){
            resp.setMsg("?????????????????????");
            return resp;
        }
            WxMaTemplateMessage customerMessag =  buildPayMessage(payRecord,messageTemplate,customerType);

        if (null == customerMessag){
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return resp;
        }
            maMsgService.sendTemplateMsg(customerMessag);
            resp.putStatus(RespStatus.SUCCESS);

        } catch (WxErrorException e) {
            e.printStackTrace();
            resp.putStatus(RespStatus.ERROR);
        }finally {
            return resp;
        }
    }


    private  WxMaTemplateMessage buildPayMessage(OrderPayRecord payRecord,WechatMessageTemplate messageTemplate,int type){


//        ????????????
//        {{keyword1.DATA}}
//
//        ????????????
//        {{keyword2.DATA}}
//
//        ????????????
//        {{keyword3.DATA}}
//
//        ????????????
//        {{keyword4.DATA}}
//
//        ?????????
//        {{keyword5.DATA}}
//
//        ????????????
//        {{keyword6.DATA}}

        String templateId = messageTemplate.getTemplateId();
        String prepayId = payRecord.getPrepayId().replace("prepay_id=","");
        String page = messageTemplate.getGoPage().replace("${orderId}",payRecord.getOrderId());

        WxMaTemplateMessage tm = WxMaTemplateMessage.builder()
                .toUser("OPENID")
                //.color("aaaaa")
                .formId(prepayId)
                .page(page)
                .data(Lists.newArrayList(
                        new WxMaTemplateData("keyword1", payRecord.getGoodsName(), "#173177"),
                        new WxMaTemplateData("keyword2", payRecord.getOrderAmount().toString(), "#173177"),
                        new WxMaTemplateData("keyword3", payRecord.getOrderId(), "#173177"),
                        new WxMaTemplateData("keyword4", DateUtils.getDateTime(), "#173177"),
                        new WxMaTemplateData("keyword5", "username", "#173177"),
                        new WxMaTemplateData("keyword6", "orderInfo", "#173177")
                        )
                )
                .templateId(templateId)
//                .emphasisKeyword("keyword1.DATA")
                .build();

        //????????????
        if(customerType == type) {
            tm.setToUser(payRecord.getOpenid());
        }
        //????????????
        return tm;
    }

    /**
     * @param appId
     * @return
     */
    public BaseResp fetchQrCodeByAppId(String appId, QrCodeReqParams params) {

        BaseResp resp = BaseResp.newError();

        try {
            StringBuffer fileNameStr = new StringBuffer();
//            String jpgName =
            fileNameStr.append(appId);
            fileNameStr.append("_");
            if (null != params && StringUtils.isNotEmpty(params.getScene())) {
                fileNameStr.append(params.getScene()).append("_");
            }
            fileNameStr.append("unlimitQrcode.jpg");
//            ????????????????????????????????????
            String scene = "auth";
            String page = "";
            if (null != params) {
                scene = StringUtils.isEmpty(params.getScene()) ? scene : params.getScene();
                page = StringUtils.isEmpty(params.getPage()) ? null : params.getPage();
            }
            // ????????????java SDK ?????????????????????????????????430?????????????????????

            WxMaService wxMaService = wxAppBaseService.fetchByAppId(appId);
            if (null == wxMaService) {
                resp.error();
                resp.setMsg("appId??????");
                return resp;
            }
            WxMaQrcodeService qrcodeService = wxMaService.getQrcodeService();
            if (null == qrcodeService) {
                return resp.error();
            }
            File file = qrcodeService.createWxaCodeUnlimit(scene, page, 430, false, new WxMaCodeLineColor("0", "0", "0"), false);

            String fileName = OssUploadUtils.getFilePath(appId, "04").concat(fileNameStr.toString());//?????????type=04
            if (fileName.contains("\\")) {
                fileName = fileName.replace("\\", "/");
            }
            //?????????????????????
            resp = iOssFileManageBiz.uploadFile(file, fileName);
        }catch (WxErrorException we){
            we.printStackTrace();
            resp.setMsg("???????????????????????????:"+we.getError());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setMsg("???????????????????????????:"+e.getCause().getClass()+","+e.getCause().getMessage());
        }
        return resp;
    }
}
