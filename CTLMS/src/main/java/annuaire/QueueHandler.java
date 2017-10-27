
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.UnsupportedEncodingException;

public class QueueHandler {

    private static final String EXCHANGE_NAME = "ctlms_exchanger";

    private Channel channel;

    public static void main(String[] argv) throws Exception {
        QueueHandler qh = new QueueHandler();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        qh.channel = connection.createChannel();

        qh.channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        qh.sendMessage(argv);

        qh.channel.close();
        connection.close();
    }

    public void sendMessage(String[] strings) throws Exception {
        String flags = getFlags(strings);      //flags of queues to send message
        String message = getMessage(strings);  //message (JSON)

        this.channel.basicPublish(EXCHANGE_NAME, flags, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + flags + "':'" + message + "'");
    }

    private static String getFlags(String[] strings){
        if (strings.length < 1)
            return "info";
        return strings[0];
    }

    private static String getMessage(String[] strings){
        if (strings.length < 2)
            return "Hello World!";
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
