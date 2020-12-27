package io.daff.rabbit._03_advanced_feature.confirmlistener;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.daff.rabbit.Constants;

public class Producer {

	public static void main(String[] args) throws Exception {
		
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(Constants.RABBIT_MQ_HOST);
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 创建Connection
		Connection connection = connectionFactory.newConnection();
		//3 创建Channel
		Channel channel = connection.createChannel();  
		
		//4 声明
		String exchangeName = "test_confirmlistener_exchange";
		String routingKey1 = "confirm.save";
		
    	//5 发送
		String msg = "Hello World RabbitMQ Confirm Listener Message ...";
		
		channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.err.println("------- error ---------");
			}
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.err.println("------- ok ---------");
			}
		});
        
		channel.basicPublish(exchangeName, routingKey1 , null , msg.getBytes());
		// channel.close();
		// connection.close();
		Thread.sleep(5000);
	}
	
}
