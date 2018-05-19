package com.zyb.mq.rabbitmq.workfair;

import java.io.IOException; 
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.zyb.mq.rabbitmq.util.ConnectionUtils;

public class Consumer1 {
	//队列名称
	private static final String QUEUE_NAME = "test_work_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取链接
				Connection connection = ConnectionUtils.getConnection();
				//创建管道
				final Channel channel = connection.createChannel();
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				
				channel.basicQos(1);	
				//监听队列时会执行该方法
				DefaultConsumer consumer = new DefaultConsumer(channel){
					@Override
					public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, 
							com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
					
						String msg = new String(body,"utf-8");
						System.out.println("recive1:"+msg);
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}finally{
							System.out.println("recive1 done");
						}
						//手动确认消息，不自动应答
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				};
				
				boolean ack = false;
				channel.basicConsume(QUEUE_NAME, ack,consumer);
	}
}
