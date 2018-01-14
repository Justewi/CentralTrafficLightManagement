package annuaire;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 * Classe d'abstraction faisant l'interface avec RabbitMQ
 */
public class QueueHandler {

    private static final java.lang.String EXCHANGE_NAME = "ctlms_exchanger";
    private final static java.lang.String QUEUE_NAME = "serverqueue";
    private final static java.lang.String QUEUE_NAME_PING = "pingqueue";

    private Connection connection;
    private Channel channel;

    private static java.lang.String DEFAULTFLAG = "ALL";

    public QueueHandler(java.lang.String host) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        connection = factory.newConnection();
        //On se connecte a rabbitMQ et on y crée un channel.
        channel = connection.createChannel();
        //On déclare l'échangeur de type que l'on va utiliser pour nos messages(ctlms_exchanger).
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // Créer les queues
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "PEDESTRIAN");
        channel.queueDeclare(QUEUE_NAME_PING, false, false, false, null);
        channel.queueBind(QUEUE_NAME_PING, EXCHANGE_NAME, "PING");

        channel.basicConsume(QUEUE_NAME_PING, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(java.lang.String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    //TODO: Respond only to the sender (body is json, with key 'from')
                    JSONObject msg = new JSONObject(new java.lang.String(body, "UTF-8"));
                    System.out.println(body);
                    sendMessage(msg.getString("from"), "pong");
                } catch (Exception ex) {
                    Logger.getLogger(QueueHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * The method used to send messages to rabbitMQ
     *
     * @param flag the flag where messages will be sent (Used by rabbitMQ to
     * route Messages)
     * @param message the message containing JSON
     * @throws Exception
     */
    public void sendMessage(java.lang.String flag, java.lang.String message) throws Exception {
        //Envoie un message à un certain exchanger, avec un certain flag.
        this.channel.basicPublish(EXCHANGE_NAME, flag, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + flag + "' : '" + message + "'");
    }

    private static java.lang.String getFlags(java.lang.String[] strings) {
        if (strings.length < 1) {
            return DEFAULTFLAG;
        }
        return strings[0];
    }

    private static java.lang.String getMessage(java.lang.String[] strings) {
        String pattern = new String();
        if (strings.length < 2) {
            return pattern;
        }
        return joinStrings(strings, " ", 1);
    }

    private static java.lang.String joinStrings(java.lang.String[] strings, java.lang.String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        if (length < startIndex) {
            return "";
        }
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    public void onMessage(MessageListener messageListener) throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(java.lang.String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                messageListener.onMessage(consumerTag, body);

            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    public void close() throws IOException {
        channel.close();
        connection.close();
    }
}
