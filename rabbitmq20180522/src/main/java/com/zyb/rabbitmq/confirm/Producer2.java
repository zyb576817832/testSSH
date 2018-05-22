package com.zyb.rabbitmq.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.zyb.rabbitmq.util.ConnectionUtils;

public class Producer2 {

	private static final String QUEUE_NAME = "test_queue_confirm2";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		channel.confirmSelect();
		
		final SortedSet<Long> sortedSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
		
		//异步回调执行
		channel.addConfirmListener(new ConfirmListener() {
			
			@Override
			public void handleNack(long deliveryTag, boolean multiple)
					throws IOException {
				
				System.out.println("-------handleNack-----------:"+multiple);
				if(multiple){
					sortedSet.headSet(deliveryTag+1).clear();
				}else{
					sortedSet.remove(deliveryTag);
				}
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple)
					throws IOException {
				
				System.out.println("-------handleAck-----------:"+multiple);
				if(multiple){
					sortedSet.headSet(deliveryTag+1).clear();
				}else{
					sortedSet.remove(deliveryTag);
				}
			}
		});
		
		String msg = "confirm 模式 异步";
		
		while (true) {
			long seqNo = channel.getNextPublishSeqNo();
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			sortedSet.add(seqNo);
		}
//		channel.close();
//		connection.close();
	}
}
