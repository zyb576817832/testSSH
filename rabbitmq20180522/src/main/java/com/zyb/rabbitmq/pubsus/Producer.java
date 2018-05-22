package com.zyb.rabbitmq.pubsus;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zyb.rabbitmq.util.ConnectionUtils;

public class Producer {

	private final static String EXCHANGE_NAME = "test_exchange_fenout";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//第二个参数 分发的意思
		
		String msg = "hello pubsus!";
		
		for (int i = 0; i < 3; i++) {
			//发送消息
			channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
			System.out.println("发送了消息："+msg+(i+1));
		}
		channel.close();
		connection.close();
	}
}
