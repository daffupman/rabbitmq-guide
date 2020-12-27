package io.daff.rabbit._03_advanced_feature.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.daff.rabbit.Constants;

import java.util.HashMap;
import java.util.Map;

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
        String exchangeName = "test_dlx_exchange";
        String routingKey = "group.bfxy";
        //5 发送

        Map<String, Object> headers = new HashMap<String, Object>();

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                //	TTL
                .expiration("10000")
                .headers(headers).build();

        String msg = "Hello World RabbitMQ 4 DLX Exchange Message ... ";
        channel.basicPublish(exchangeName, routingKey, props, msg.getBytes());
        channel.close();
        connection.close();
    }

}
