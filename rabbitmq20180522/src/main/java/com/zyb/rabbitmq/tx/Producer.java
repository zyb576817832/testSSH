package com.zyb.rabbitmq.tx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zyb.rabbitmq.util.ConnectionUtils;

public class Producer {

	private final static String QUEUE_NAME = "test_queue_tx";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		try {
			String msg = "商品添加!";
			channel.txSelect();//开启事务
			int a = 1/0;
			System.out.println(a);
			for (int i = 0; i < 3; i++) {
				//发送消息
				channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
				System.out.println("发送了消息："+msg+(i+1));
			}
			channel.txCommit();//提交事务
		} catch (Exception e) {
			channel.txRollback();//事务回滚
			System.out.println("发送消息失败 rollback！");
		}
		channel.close();
		connection.close();
	}
}
