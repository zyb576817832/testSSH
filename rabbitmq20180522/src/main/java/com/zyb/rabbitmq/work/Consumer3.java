package com.zyb.rabbitmq.work;

import java.io.IOException;  
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.zyb.rabbitmq.util.ConnectionUtils;
 
public class Consumer3 {
	//队列名称
	private static final String QUEUE_NAME = "test_work_queue";
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
						System.out.println("recive3:"+msg);
					}
				};
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					System.out.println("recive3 done");
				}
				channel.basicConsume(QUEUE_NAME, true,consumer);
	}
}
