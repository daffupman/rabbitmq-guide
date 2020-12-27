package io.daff.rabbit._01_start;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import io.daff.rabbit.Constants;

/**
 * @author daffupman
 * @since 2020/12/26
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory() ;

        connectionFactory.setHost(Constants.RABBIT_MQ_HOST);
        connectionFactory.setPort(Constants.RABBIT_MQ_PORT);
        connectionFactory.setVirtualHost("/");

        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String queueName = "test001";
        //	durable 是否持久化消息
        channel.queueDeclare(queueName, false, false, false, null);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //	参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, consumer);
        //	循环获取消息
        while(true){
            //	获取消息，如果没有消息，这一步将会一直阻塞
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息：" + msg);
        }
    }
}
