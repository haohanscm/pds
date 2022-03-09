package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer;

import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.MQEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.util.SerializableUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.UUID;

/**
 * @author shenyu
 * @create 2019/1/10
 */
public class RocketMqProducerImpl implements IProducer, InitializingBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String namesrvAddr;

    private String producerGroup;

    private Boolean retryAnotherBrokerWhenNotStoreOK;

    private DefaultMQProducer producer;

    /**
     * spring 容器初始化所有属性后调用此方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultMQProducer();
        producer.setProducerGroup( this.producerGroup );
        producer.setNamesrvAddr( this.namesrvAddr );
        producer.setRetryAnotherBrokerWhenNotStoreOK( this.retryAnotherBrokerWhenNotStoreOK );
        producer.setRetryTimesWhenSendFailed(3);
//        producer.setVipChannelEnabled(false);
        producer.start();
        logger.info( "[{}:{}] start successd!",producerGroup,namesrvAddr );
    }

    /**
     * 销毁
     */
    public void destroy() throws Exception {
        if (producer != null) {
            logger.info("producer: [{}:{}] destroy ",producerGroup,namesrvAddr);
            producer.shutdown();
        }

    }

    @Override
    public void send(String topic, MQEntity entity) {
        String keys = UUID.randomUUID().toString();
        entity.setMqKey(keys);
//        String tags = entity.getClass().getName();
        String tags = entity.getMqTags();
        logger.info("业务:{},tags:{},keys:{},entity:{}",topic, tags, keys, entity);
        Message msg = new Message(topic, tags, keys,
                SerializableUtil.toByte(entity));
        try {
            producer.send(msg);
        } catch (Exception e) {
            logger.error(keys.concat(":发送消息失败"), e);
            throw new RuntimeException("send message fail!",e);
        }
    }

    @Override
    public void send(String topic, MQEntity entity, SendCallback sendCallback) {
        String keys = UUID.randomUUID().toString();
        entity.setMqKey(keys);
//        String tags = entity.getClass().getName();
        String tags = entity.getMqTags();
        logger.info("业务:{},tags:{},keys:{},entity:{}",topic, tags, keys, entity);
        Message msg = new Message(topic, tags, keys,
                SerializableUtil.toByte(entity));
        try {
            producer.send(msg, sendCallback);
        } catch (Exception e) {
            logger.error(keys.concat(":发送消息失败"), e);
            throw new RuntimeException("send message fail!",e);
        }
    }

    @Override
    public void sendOneway(String topic, MQEntity entity) {
        String keys = UUID.randomUUID().toString();
        entity.setMqKey(keys);
//        String tags = entity.getClass().getName();
        String tags = entity.getMqTags();
        logger.info("业务:{},tags:{},keys:{},entity:{}",topic, tags, keys, entity);
        Message msg = new Message(topic, tags, keys,
                SerializableUtil.toByte(entity));
        try {
            producer.sendOneway(msg);
        } catch (Exception e) {
            logger.error(keys.concat(":发送消息失败"), e);
            throw new RuntimeException("send message fail!",e);
        }
    }

    @Override
    public void sendOrderly(String topic, MQEntity entity,int selectBy) {
        String keys = UUID.randomUUID().toString();
        entity.setMqKey(keys);
//        String tags = entity.getClass().getName();
        String tags = entity.getMqTags();
        logger.info("业务:{},tags:{},keys:{},entity:{}",topic, tags, keys, entity);
        Message msg = new Message(topic, tags, keys,
                SerializableUtil.toByte(entity));
        try {
            producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % list.size();
                    return list.get(index);
                }
            }, selectBy);
        } catch (Exception e) {
            logger.error(keys.concat(":发送消息失败"), e);
            throw new RuntimeException("send message fail!",e);
        }
    }

    @Override
    public void sendDelay(String topic, MQEntity entity, int delayLevel) {
        String keys = UUID.randomUUID().toString();
        entity.setMqKey(keys);
        String tags = entity.getMqTags();
        logger.info("业务:{},tags:{},keys:{},entity:{}",topic, tags, keys, entity);
        Message msg = new Message(topic, tags, keys,
                SerializableUtil.toByte(entity));
        msg.setDelayTimeLevel(delayLevel);
        try {
            producer.send(msg);
        } catch (Exception e) {
            logger.error(keys.concat(":发送消息失败"), e);
            throw new RuntimeException("send message fail!",e);
        }
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }


    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }


    public void setProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }


    public void setRetryAnotherBrokerWhenNotStoreOK(
            Boolean retryAnotherBrokerWhenNotStoreOK) {
        this.retryAnotherBrokerWhenNotStoreOK = retryAnotherBrokerWhenNotStoreOK;
    }


}
