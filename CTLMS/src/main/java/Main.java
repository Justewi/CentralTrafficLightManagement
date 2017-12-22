
import annuaire.ControlerList;
import annuaire.MessageListener;
import annuaire.QueueHandler;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final String DEFAULT_RABBITMQ_IP = "localhost";
    private static final int DEFAULT_RABBITMQ_PORT = 5672;

    public static void main(String[] argv) throws Exception {

        String host = DEFAULT_RABBITMQ_IP;
        if (argv.length > 0) {
            host = argv[0];
        }
        System.out.println("Connexion a RabbitMQ a l'adresse " + host + ":" + DEFAULT_RABBITMQ_PORT + " ...");

        final QueueHandler qh = new QueueHandler(host);

        System.out.println("Serveur connecte");

        //Ici à terme on aura théoriquement une boucle qui attends qu'on lui dise d'envoyer des messages.
        //et qui les enverra comme ci dessous:
        System.out.println("Envoi des patterns aux controleurs connus");
        ControlerList.sendInitPattern(qh);
        System.out.println("Patterns initiaux envoyes");

        System.out.println(" [*] Attente de message. CTRL+C pour quitter");

        qh.onMessage(new MessageListener() {
            @Override
            public void onMessage(String tag, byte[] body) {
                try {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                    ControlerList.sendModifiedPattern(qh);
                    System.out.println("");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // TODO: Things ? Or a better way to do this.
        while (true);

        // Fermeture propre du bazar
        qh.close();
    }
}
