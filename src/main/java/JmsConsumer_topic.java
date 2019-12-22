import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer_topic {

    public static final String ACTIVEMQ_URL = "tcp://192.168.168.128:61616";

    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws Exception{
        System.out.println(" 这里是1号消费者 ");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        javax.jms.Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener( (message) -> {
            if (null != message  && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("****消费者text的消息："+textMessage.getText());
                }catch (JMSException e) {
                }
            }
            if (null != message  && message instanceof MapMessage){
                MapMessage mapMessage = (MapMessage)message;
                try {
                    System.out.println("****消费者的map消息："+mapMessage.getString("k1"));
                }catch (JMSException e) {
                }
            }

        });


        // 保证控制台不灭  不然activemq 还没连上就关掉了连接
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
