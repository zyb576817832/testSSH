package com.zyb.rabbitmq.spring.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyb.rabbitmq.spring.MyProducer;
@Service
public class MyProducerImpl implements MyProducer{

	@Autowired
	private AmqpTemplate  amqpTemplate; 
	@Override
	public void sendDataToQueue(String queueKey, Object object) {
		
		amqpTemplate.convertAndSend(queueKey,object);
	}

}
