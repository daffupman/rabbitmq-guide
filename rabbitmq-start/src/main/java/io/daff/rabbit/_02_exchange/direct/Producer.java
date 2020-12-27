package io.daff.rabbit._02_exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.daff.rabbit.Constants;

public class Producer {


    public static void main(String[] args) throws Exception {

        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constants.RABBIT_MQ_HOST);
        connectionFactory.setPort(Constants.RABBIT_MQ_PORT);
        connectionFactory.setVirtualHost("/");

        //2 创建Connection
        Connection connection = connectionFactory.newConnection();
        //3 创建Channel
        Channel channel = connection.createChannel();
        //4 声明
        String exchangeName = "test_direct_exchange";
        String routingKey = "test_direct_routingKey";
        //5 发送

        String msg = "Hello World RabbitMQ 4  Direct Exchange Message ... ";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        channel.close();
        connection.close();

    }

}
