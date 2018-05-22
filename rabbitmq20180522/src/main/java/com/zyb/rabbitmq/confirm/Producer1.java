package com.zyb.rabbitmq.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zyb.rabbitmq.util.ConnectionUtils;

public class Producer1 {

	private final static String QUEUE_NAME = "test_queue_confirm1";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		channel.confirmSelect();//开启confirm模式
		
		String msg = "商品添加!";
		for (int i = 0; i < 3; i++) {
			//发送消息
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			System.out.println("发送了消息："+msg+(i+1));
		}
		
		if(!channel.waitForConfirms()){//如果没有消息反馈
			
			System.out.println("消费发送失败");
		}else{
			
			System.out.println("消息发送成功");
		}
		channel.close();
		connection.close();
	}
}
