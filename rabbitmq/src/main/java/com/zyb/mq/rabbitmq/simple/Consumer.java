package com.zyb.mq.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;
import com.zyb.mq.rabbitmq.util.ConnectionUtils;

public class Consumer {
	//队列名称
	private static final String QUEUE_NAME = "test_simple_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取链接
				Connection connection = ConnectionUtils.getConnection();
				//创建管道
				Channel channel = connection.createChannel();
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				
				DefaultConsumer consumer = new DefaultConsumer(channel){
					@Override
					public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, 
							com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
					
						String msg = new String(body,"utf-8");
						System.out.println("recive:"+msg);
					}
				};
				channel.basicConsume(QUEUE_NAME, true,consumer);
	}
	public  void oldApi(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//获取链接
		Connection connection = ConnectionUtils.getConnection();
		//创建管道
		Channel channel = connection.createChannel();
		
		//定义队列消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//监听队列
		channel.basicConsume(QUEUE_NAME, true,consumer);
		
		while (true) {
				Delivery delivery = consumer.nextDelivery();
				
				String msgString = new String(delivery.getBody());
				
				System.out.println("recive msg :"+msgString);
		}
	}
}
