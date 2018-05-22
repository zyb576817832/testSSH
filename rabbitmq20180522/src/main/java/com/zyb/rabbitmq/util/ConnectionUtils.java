package com.zyb.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

	/**
	 * 获取MQ的链接
	 * 
	 * @return
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static Connection getConnection() throws IOException, TimeoutException{
		
		ConnectionFactory factory = new ConnectionFactory();
		//设置IP地址
		factory.setHost("192.168.0.104");
		//设置amqp的端口号
		factory.setPort(5672);
		
		factory.setVirtualHost("/vhost_zyb");
		//用户名
		factory.setUsername("user_zyb");
		//密码
		factory.setPassword("123");
		
		return factory.newConnection();
	}
}
