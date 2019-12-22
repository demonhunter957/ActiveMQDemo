import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息的创建者
 */
public class JMSProducer_queue {

    //  linux上部署的activemq服务的IP地址 + 端口号，服务端默认端口号为61616
    public static final String ACTIVEMQ_URL = "tcp://192.168.168.128:61616";

    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception{

        // 1. 按照给定的url创建连接工程，这个构造器采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2. 通过连接工厂创建connection和启动
        Connection connection = activeMQConnectionFactory.createConnection();
        // 启动connection
        connection.start();

        // 3. 通过connection创建session
        // 两个参数，第一个叫事务，第二个叫签收。当前演示为非事务+自动签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4. 通过session创建目的地（两种 ：队列/主题  这里用队列）
        Queue queue = session.createQueue(QUEUE_NAME);

        // 5. 通过session创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

        // 6 通过messageProducer 生产 3 条 消息发送到消息队列中
        for (int i = 1; i <= 3 ; i++) {
            // 7  创建文字消息
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            // 8  通过messageProducer发布消息
            messageProducer.send(textMessage);
        }
        // 9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println(" ******** 消息发送到MQ完成 *******");
    }
}
