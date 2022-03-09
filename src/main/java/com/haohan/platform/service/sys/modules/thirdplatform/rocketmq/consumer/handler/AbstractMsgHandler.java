package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.consumer.handler;

import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.MQEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.util.SerializableUtil;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenyu
 * @create 2019/1/10
 */
public abstract class AbstractMsgHandler implements IMsgHandler {
    protected Logger logger = LoggerFactory.getLogger(AbstractMsgHandler.class);

    private String classTypeName;

    public void handleMessage(MessageExt msg) {
        try {
            MQEntity entity = doStart(msg);
            execute(entity);
            doEnd(entity);
        } catch (Exception e) {
            logger.error("MQ消息处理异常。",e);
        }
    }

    /**
     * 解析mq消息前置处理
     * @param msg
     * @throws ClassNotFoundException
     */
    protected MQEntity doStart(MessageExt msg) throws ClassNotFoundException {
        Class<? extends MQEntity> clazz = (Class<? extends MQEntity>) Class.forName(classTypeName);
        return SerializableUtil.parse(msg.getBody(), clazz);
    }

    /**
     * 解析mq消息后置处理
     * @param entity
     */
    protected void doEnd(MQEntity entity) {

    }

    /**
     * 解析mq消息 MessageExt
     * @param entity
     */
    public abstract void execute(MQEntity entity);

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }
}
