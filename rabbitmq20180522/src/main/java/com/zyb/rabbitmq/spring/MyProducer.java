package com.zyb.rabbitmq.spring;

public interface MyProducer {

	/**
     * 发送消息到指定队列
     * @param queueKey
     * @param object
     */
	void sendDataToQueue(String queueKey, Object object);
}
