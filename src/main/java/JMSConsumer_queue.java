import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息的消费者
 */
public class JMSConsumer_queue {

    public static final String ACTIVEMQ_URL = "tcp://192.168.168.128:61616";

    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception{

        System.out.println("这里是1号消费者 ");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);

//          同步阻塞方式receive() 空参数的receive方法是阻塞，有参数的为等待时间
//          订阅者或消费者使用MessageConsumer的receive()方法接收消息，receive在接收之前一直阻塞
        while(true){
            // 这里是TextMessage是因为消息发送者是 TextMessage，接受处理的也应该是这个类型的消息
            TextMessage message = (TextMessage) messageConsumer.receive(1000L); //等待一秒钟
            if (null != message ){
                System.out.println("****消费者的消息："+((TextMessage) message).getText());
            }else {
                break;
            }
        }

//        // 通过监听的方式来消费消息
//        // 通过异步非阻塞的方式消费消息
//        // 通过messageConsumer 的setMessageListener 注册一个监听器，
//        // 当有消息发送来时，系统自动调用MessageListener 的 onMessage 方法处理消息
//        messageConsumer.setMessageListener(new MessageListener() {
//            public void onMessage(Message message)  {
//                if (null != message  && message instanceof TextMessage){
//                    TextMessage textMessage = (TextMessage)message;
//                    try {
//                        System.out.println("****消费者的消息："+textMessage.getText());
//                    }catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        messageConsumer.close();
        session.close();
        connection.close();
    }

}
