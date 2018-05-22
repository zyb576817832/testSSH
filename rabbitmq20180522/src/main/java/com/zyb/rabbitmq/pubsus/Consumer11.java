package com.zyb.rabbitmq.pubsus;

import java.io.IOException;  
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.zyb.rabbitmq.util.ConnectionUtils;

public class Consumer11 {
	//队列名称
	private static final String QUEUE_NAME = "test_work_mail";
	private final static String EXCHANGE_NAME = "test_exchange_fenout";
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取链接
				Connection connection = ConnectionUtils.getConnection();
				//创建管道
				Channel channel = connection.createChannel();
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				//绑定队列到交换机转发器
				channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, "");
				//监听队列时会执行该方法
				DefaultConsumer consumer = new DefaultConsumer(channel){
					@Override
					public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, 
							com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
					
						String msg = new String(body,"utf-8");
						System.out.println("recive11:"+msg);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}finally{
							System.out.println("recive11 done");
						}
					}
				};
				
				channel.basicConsume(QUEUE_NAME, true,consumer);
	}
}
