package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.consumer.impl;

import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.consumer.AbstractRocketMqPushConsumer;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.MQEntity;

/**
 * @author shenyu
 * @create 2019/1/11
 */
public class TestPushConsumer extends AbstractRocketMqPushConsumer {

    @Override
    protected void execute(MQEntity entity) {
        logger.debug("消费了一条信息: {}",entity.toString());
    }

}
