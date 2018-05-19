package com.zyb.mq.rabbitmq.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zyb.mq.rabbitmq.util.ConnectionUtils;

public class Producer {
	//队列名称
	private static final String QUEUE_NAME = "test_work_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取一个链接
		Connection connection = ConnectionUtils.getConnection();
		//通过链接创建通道
		Channel channel = connection.createChannel();
		//创建队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		
		for (int i = 0; i < 50; i++) {
			String msg = "hello cusomer:"+ (i+1) ;
			//发布消息到队列
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			System.out.println(msg);
		}
		
		channel.close();
		connection.close();
	}
}
