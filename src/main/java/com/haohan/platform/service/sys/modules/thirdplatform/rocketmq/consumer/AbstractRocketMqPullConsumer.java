package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.consumer;

import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.MQEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.util.SerializableUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

/**
 * pull消费者
 * @author shenyu
 * @create 2019/1/17
 */
public abstract class AbstractRocketMqPullConsumer implements InitializingBean {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    DefaultMQPullConsumer consumer;

    private String namesrvAddr;

    private String consumerGroup;

    private String topic;

//    private String subExpression;

    private String classTypeName;

    private String messageModel;

    long consumerPullTimeoutMillis = 1000L;

    private static Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();

    public void startConsumer() {
        try {
            consumer = new DefaultMQPullConsumer(consumerGroup);
            consumer.setNamesrvAddr(namesrvAddr);
//            consumer.setConsumerPullTimeoutMillis(consumerPullTimeoutMillis);
            if ("CLUSTERING".equals(messageModel)){
                consumer.setMessageModel(MessageModel.CLUSTERING);
            }else if ("BROADCASTING".equals(messageModel)){
                consumer.setMessageModel(MessageModel.BROADCASTING);
            }
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public Set<MQEntity> getMsgSet(String subExpression){
        MQEntity entity = null;
        Set<MQEntity> entitySet = new HashSet<>();
        try {
            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(topic);
            for(MessageQueue mq:mqs) {
                long offset = consumer.fetchConsumeOffset(mq,true);
                //	PullResultExt pullResult =(PullResultExt)consumer.pull(mq, null, getMessageQueueOffset(mq), 32);
                PullResult pullResult = consumer.pull(mq, subExpression, offset, 5,consumerPullTimeoutMillis);
                long nextOffset = pullResult.getNextBeginOffset();
                putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
//                logger.debug("offset : [{}] next : [{}] queueId : [{}] msgSize : [{}]",offset,nextOffset,mq.getQueueId(),nextOffset-offset);
                switch (pullResult.getPullStatus()) {
                    case FOUND:
                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                        for (MessageExt msg : messageExtList) {
//                            logger.debug( String.format( "received a new message: [message:id:%s] [topic:%s] [tags:%s]", msg.getMsgId(), msg.getTopic(), msg.getTags()) );
                            entity = doStart(msg);
                            entitySet.add(entity);
                        }
                        consumer.updateConsumeOffset(mq,offset + messageExtList.size());
                        break;
                    case NO_MATCHED_MSG:
                        break;
                    case NO_NEW_MSG:
                        break ;
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                }
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return entitySet;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startConsumer();
    }

    protected MQEntity doStart(MessageExt msg) throws ClassNotFoundException {
        Class<? extends MQEntity> clazz = (Class<? extends MQEntity>) Class.forName(classTypeName);
        return SerializableUtil.parse(msg.getBody(), clazz);
    }

//    protected abstract void execute(MQEntity mqEntity);

//    protected void doEnd(MQEntity entity) {}

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offsetTable.put(mq, offset);
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetTable.get(mq);
        if (offset != null)
            return offset;
        return 0;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

//    public void setSubExpression(String subExpression) {
//        this.subExpression = subExpression;
//    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }

    public void setMessageModel(String messageModel) {
        this.messageModel = messageModel;
    }
}
