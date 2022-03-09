package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.consumer.handler;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author shenyu
 * @create 2019/1/10
 */
public interface IMsgHandler {
    /**
     * 消费端解析消息
     * @param msg
     */
    void handleMessage(MessageExt msg );
}
