package com.haohan.platform.service.sys.common.utils;


import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 极光推送工具类(目前仅用于向浩瀚付推送,如需向其他应用推送,需要修改appKey,masterSecret)
 *
 * @author shenyu
 * @create 2018/7/21
 */
public class JGPushUtil {
    private static Logger logger = LoggerFactory.getLogger(JGPushUtil.class);
    private static final String appKey = "6c0133f2a432afde6b779812";
    private static final String masterSecret = "85bbf68b04b2b6ccd12fd156";
    private static final String hd_appKey = "577bb5b8f64c92b52a5b6534";
    private static final String hd_masterSecret = "5ca91474554e210bdce33c0a";

    private static JPushClient jPushClient = new JPushClient(masterSecret, appKey);
    private static JPushClient hdJPushClient = new JPushClient(hd_masterSecret, hd_appKey);

    public static final String ALIAS = "alias";
    public static final String TAG = "tag";


    /**
     * 通知推送
     * 备注：推送方式不为空时，推送的值也不能为空；推送方式为空时，推送值不做要求
     *
     * @param type  推送方式：1、“tag”标签推送，2、“alias”别名推送
     * @param dest  推送的标签或别名值
     * @param alert 推送的内容
     */
    public static void pushNotice(String type, String dest, String alert) {
        Builder builder = PushPayload.newBuilder();
        builder.setPlatform(Platform.all());//设置接受的平台，all为所有平台，包括安卓、ios、和微软的
        //设置如果用户不在线、离线消息保存的时间
        Options options = Options.sendno();
        options.setTimeToLive(86400l);    //设置为86400为保存一天，如果不设置默认也是保存一天
        builder.setOptions(options);
        //设置推送方式
        if (type.equals("alias")) {
            builder.setAudience(Audience.alias(dest));//根据别名推送
        } else if (type.equals("tag")) {
            builder.setAudience(Audience.tag(dest));//根据标签推送
        } else {
            builder.setAudience(Audience.all());//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
        }
        //设置为采用通知的方式发送消息
        builder.setNotification(Notification.alert(alert));
        PushPayload pushPayload = builder.build();
        try {
            //推送通知
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            PushResult hdPushResult = hdJPushClient.sendPush(pushPayload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PushPayload msgSetting(String type, String dest, String alert) {
        Builder builder = PushPayload.newBuilder();
        builder.setPlatform(Platform.all());//设置接受的平台
        if (type.equals("alias")) {
            builder.setAudience(Audience.alias(dest));//别名推送
        } else if (type.equals("tag")) {
            builder.setAudience(Audience.tag(dest));//标签推送
        } else {
            builder.setAudience(Audience.all());//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
        }
        Message.Builder newBuilder = Message.newBuilder();
        newBuilder.setMsgContent(alert);//消息内容
        Message message = newBuilder.build();
        builder.setMessage(message);
        PushPayload pushPayload = builder.build();
        return pushPayload;
    }

    /**
     * 自定义消息推送
     * 备注：推送方式不为空时，推送的值也不能为空；推送方式为空时，推送值不做要求
     *
     * @param type  推送方式：1、“tag”标签推送，2、“alias”别名推送
     * @param dest  推送的标签或别名值
     * @param alert 推送的内容
     */
    public static void pushMsgHhf(String type, String dest, String alert) {
        PushPayload pushPayload = msgSetting(type, dest, alert);
        try {
            PushResult pushResult = jPushClient.sendPush(pushPayload);
            logger.debug("\n--浩瀚付-极光推送--{}\n", "  type:" + type + "  destination:" + dest + "\nmsg:" + alert);
            if (0 != pushResult.statusCode) {
                //错误处理
            }
        } catch (APIRequestException e) {
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pushMsgHhfhd(String type, String dest, String alert) {
        PushPayload pushPayload = msgSetting(type, dest, alert);
        try {
            logger.debug("\n--浩瀚付HD-极光推送--{}\n", "type:" + type + "  destination:" + dest + "\nmsg:" + alert);
            PushResult hdPushResult = hdJPushClient.sendPush(pushPayload);
            if (0 != hdPushResult.statusCode) {
                //错误处理
            }
        } catch (APIRequestException e) {
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
