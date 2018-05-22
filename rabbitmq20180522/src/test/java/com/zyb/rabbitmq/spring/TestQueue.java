package com.zyb.rabbitmq.spring;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context.xml"})
public class TestQueue {

	@Autowired
	MyProducer myProducer;
	
	public static final String QUEUE_NAME = "com.zyb.test";
	
	@Test
	public void send(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("data","hello,rabbmitmq!");
		myProducer.sendDataToQueue("hello", map);
		
		
	}
}
