package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer;

import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.MQEntity;
import org.apache.rocketmq.client.producer.SendCallback;

/**
 * @author shenyu
 * @create 2019/1/10
 */
public interface IProducer {

    /**
     * 同步发送MQ
     * @param topic
     * @param entity
     */
    public void send(String topic, MQEntity entity);

    /**
     * 发送MQ,提供回调函数，超时时间默认3s
     * @param topic
     * @param entity
     * @param sendCallback
     */
    public void send( String topic, MQEntity entity, SendCallback sendCallback );

    /**
     * 单向发送MQ，不等待服务器回应且没有回调函数触发，适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
     * @param topic
     * @param entity
     */
    public void sendOneway(String topic, MQEntity entity);

    /**
     * 指定MessageQueue发送, 适用于多线程情况下发送一组具有相同特征且需要顺序消费的消息 ,例如订单的创建,支付,退款
     * @param topic
     * @param entity
     * @param selectBy 选择Queue的依据,如订单ID
     */
    public void sendOrderly(String topic,MQEntity entity,int selectBy);

    /**
     * 发送延迟消息(messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h)
     * @param topic
     * @param entity
     * @param delayLevel
     */
    public void sendDelay(String topic,MQEntity entity,int delayLevel);
}
