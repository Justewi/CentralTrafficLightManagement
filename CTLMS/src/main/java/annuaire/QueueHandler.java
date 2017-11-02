package annuaire;

import com.rabbitmq.client.*;
import gestionpattern.Pattern;

import java.io.IOException;
import java.util.Scanner;

/**
 * Classe d'abstraction faisant l'interface avec RabbitMQ
 */
public class QueueHandler {

    private static final String EXCHANGE_NAME = "ctlms_exchanger";

    private Channel channel;

    private static String DEFAULTFLAG = "ALL";
    private static String DEFAULT_RABBITMQ_IP = "localhost";
    private static int DEFAULT_RABBITMQ_PORT = 5672;


    private final static String QUEUE_NAME = "serverqueue";

    public static void main(String[] argv) throws Exception {

        String host = DEFAULT_RABBITMQ_IP;
        if(argv.length > 0) {
            host = argv[0];
        }
        System.out.println("Connexion a RabbitMQ a l'adresse "+host+":"+DEFAULT_RABBITMQ_PORT+" ...");

        final QueueHandler qh = new QueueHandler();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        Connection connection = factory.newConnection();
        //On se connecte a rabbitMQ et on y crée un channel.
        qh.channel = connection.createChannel();
        //On déclare l'échangeur de type que l'on va utiliser pour nos messages(ctlms_exchanger).
        qh.channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        System.out.println("Serveur connecte");

        //Ici à terme on aura théoriquement une boucle qui attends qu'on lui dise d'envoyer des messages.
        //et qui les enverra comme ci dessous:

        qh.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        qh.channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "PEDESTRIAN");

        System.out.println("Envoi des patterns aux controleurs connus");
        ControlerList.sendInitPattern(qh);
        System.out.println("Patterns initiaux envoyes");

        System.out.println(" [*] Attente de message. CTRL+C pour quitter");

        Consumer consumer = new DefaultConsumer(qh.channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                ControlerList.sendModifiedPattern(qh);
                System.out.println("");
            }
        };

        qh.channel.basicConsume(QUEUE_NAME, true, consumer);

        //Fermeture du channel et de la connexion.

        /*qh.channel.close();
        connection.close();*/
    }

    /**
     * The method used to send messages to rabbitMQ
     * @param flag the flag where messages will be sent (Used by rabbitMQ to route Messages)
     * @param message the message containing JSON
     * @throws Exception
     */

    public void sendMessage(String flag , String message) throws Exception {
        //Envoie un message à un certain exchanger, avec un certain flag.
        this.channel.basicPublish(EXCHANGE_NAME, flag, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + flag + "' : '" + message + "'");
    }

    private static String getFlags(String[] strings){
        if (strings.length < 1)
            return DEFAULTFLAG;
        return strings[0];
    }

    private static String getMessage(String[] strings){
        Pattern pattern = new Pattern();
        if (strings.length < 2)
            return pattern.getDescription();
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0 ) return "";
        if (length < startIndex ) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }


    public QueueHandler(Channel channel) {
        this.channel = channel;
    }

    public QueueHandler() {

    }

}
