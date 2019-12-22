import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer_topic {

    public static final String ACTIVEMQ_URL = "tcp://192.168.168.128:61616";

    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws Exception{

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);

        for (int i = 1; i <= 3 ; i++) {
            // 7  创建字消息
            TextMessage textMessage = session.createTextMessage("topic_name--" + i);
            // 8  通过messageProducer发布消息
            messageProducer.send(textMessage);

//            MapMessage mapMessage = session.createMapMessage();
//            mapMessage.setString("k1","v1");
//            messageProducer.send(mapMessage);
        }
        // 9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("**** TOPIC_NAME消息发送到MQ完成****");
    }
}
