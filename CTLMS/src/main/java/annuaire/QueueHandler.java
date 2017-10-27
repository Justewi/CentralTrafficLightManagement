package annuaire;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import gestionpattern.Pattern;

/**
 * Classe d'abstraction faisant l'interface avec RabbitMQ
 */
public class QueueHandler {

    private static final String EXCHANGE_NAME = "ctlms_exchanger";

    private Channel channel;

    private static String DEFAULTFLAG = "ALL";

    public static void main(String[] argv) throws Exception {
        QueueHandler qh = new QueueHandler();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        //On se connecte a rabbitMQ et on y crée un channel.
        qh.channel = connection.createChannel();
        //On déclare l'échangeur de type que l'on va utiliser pour nos messages(ctlms_exchanger).
        qh.channel.exchangeDeclare(EXCHANGE_NAME, "direct");


        //Ici à terme on aura théoriquement une boucle qui attends qu'on lui dise d'envoyer des messages.
        //et qui les enverra comme ci dessous:
        qh.sendMessage(getFlags(argv), getMessage(argv));


        //Fermeture du channel et de la connexion.
        qh.channel.close();
        connection.close();
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
        System.out.println(" [x] Sent '" + flag + "':'" + message + "'");
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
