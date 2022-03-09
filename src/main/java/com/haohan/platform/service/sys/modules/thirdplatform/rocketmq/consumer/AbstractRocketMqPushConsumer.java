package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.consumer;

import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common.IRocketMqConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.MQEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.util.SerializableUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author shenyu
 * @create 2019/1/10
 */
public abstract class AbstractRocketMqPushConsumer implements InitializingBean,IRocketMqConstant {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DefaultMQPushConsumer consumer;

    private String namesrvAddr;

    private String consumerGroup;

    private String messageModel;

    private String topic;

    private String subExpression;

    private String messageListener;

    private String classTypeName;

    private void initializingMessageConsumer() throws InterruptedException, MQClientException {
        consumer = new DefaultMQPushConsumer();
        if( this.consumerGroup != null && this.consumerGroup.trim().length() > 0 ) {
            consumer.setConsumerGroup( this.consumerGroup );
            logger.debug( "set consumer group : " + this.consumerGroup );
        }
        consumer.setNamesrvAddr( this.namesrvAddr );
        consumer.setConsumeMessageBatchMaxSize( 1 );
        consumer.setMaxReconsumeTimes(10);
        logger.debug( "set consumer name server address : " + this.namesrvAddr );
        logger.debug( "set consumer message batch max size : " + 1 );

        if( "BROADCASTING".equals( messageModel ) ) {
            consumer.setMessageModel( MessageModel.BROADCASTING );
            logger.debug( "set consumer message model BROADCASTING " );
        } else if( "CLUSTERING".equals( messageModel ) ) {
            consumer.setMessageModel( MessageModel.CLUSTERING );
            logger.debug( "set consumer message model CLUSTERING" );
        } else {
            logger.debug( "set consumer message model should be BROADCASTING or CLUSTERING " );
            throw new RuntimeException( "set consumer message model should be BROADCASTING or CLUSTERING" );
        }
        /**
         * 订阅指定topic下所有消息
         * 注意：一个consumer对象可以订阅多个topic
         */
        if (null == topic || "".equals(topic)){
            logger.debug("consumer must to subscribe at least one topic");
            throw new RuntimeException("consumer must to subscribe at last one topic");
        }
        consumer.subscribe( topic, subExpression );
        logger.debug( "consumer subscribe topic [{}] tag [{}]" , topic , subExpression );

        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere( ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET );
        if( "CONCURRENTLY".equals( messageListener ) ) {
            consumer.registerMessageListener( new MessageListenerConcurrently() {

                /**
                 * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
                 */
                public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context ) {
                    try {
                        if( msgs != null && !msgs.isEmpty() ) {
                            for( MessageExt msg : msgs ) {
                                logger.debug( String.format( "start consum message : [message:id:%s] [topic:%s] [tags:%s] ", msg.getMsgId(), msg.getTopic(), msg.getTags()) );
                                MQEntity entity = doStart(msg);
                                execute(entity);
                                doEnd(entity);
                                logger.debug( String.format( "end consume message ! [message:id:%s] [topic:%s] [tags:%s] ", msg.getMsgId(), msg.getTopic(), msg.getTags()) );
                            }
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch( Exception e ) {
                        logger.error( "consume message error!", e );
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
            } );
        } else if( "ORDERLY".equals( messageListener ) ) {
            consumer.registerMessageListener( new MessageListenerOrderly() {
                /**
                 * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
                 */
                public ConsumeOrderlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeOrderlyContext context ) {
                    try {
                        if( msgs != null && !msgs.isEmpty() ) {
                            for( MessageExt msg : msgs ) {
                                logger.debug( String.format( "start consum message: message:id:%s topic:%s tags:%s", msg.getMsgId(), msg.getTopic(), msg.getTags()) );
                                MQEntity entity = doStart(msg);
                                execute(entity);
                                doEnd(entity);
                                logger.debug( String.format( "consume message success! message:id:%s topic:%s tags:%s", msg.getMsgId(), msg.getTopic(), msg.getTags()) );
                            }
                        }
                        return ConsumeOrderlyStatus.SUCCESS;
                    } catch( Exception e ) {
                        logger.error( "consume message error!", e );
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                }
            } );
        } else {
            logger.error( "please set consumer model to be CONCURRENTLY or ORDERLY" );
            throw new RuntimeException();
        }

        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可
         */
        consumer.start();
        logger.debug( "consumer start successd!" );
    }

    protected MQEntity doStart(MessageExt msg) throws ClassNotFoundException {
        Class<? extends MQEntity> clazz = (Class<? extends MQEntity>) Class.forName(classTypeName);
        return SerializableUtil.parse(msg.getBody(), clazz);
    }

    protected abstract void execute(MQEntity mqEntity);

    protected void doEnd(MQEntity entity) {

    }

    public void afterPropertiesSet() throws Exception {
        initializingMessageConsumer();
    }

    public void destroy() throws Exception {
        if (consumer != null) {
            consumer.shutdown();
            logger.debug( "consumer shutdown!" );
        }
    }

    public void setNamesrvAddr( String namesrvAddr ) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup( String consumerGroup ) {
        this.consumerGroup = consumerGroup;
    }

    public void setMessageModel( String messageModel ) {
        this.messageModel = messageModel;
    }

    public void setMessageListener( String messageListener ) {
        this.messageListener = messageListener;
    }

    public String getSubExpression() {
        return subExpression;
    }

    public void setSubExpression(String subExpression) {
        this.subExpression = subExpression;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }

}
