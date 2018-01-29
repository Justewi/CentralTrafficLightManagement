package annuaire;

import annuaire.repository.ControllerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Transactional
@SpringBootApplication
public class Application {

    private static final String DEFAULT_RABBITMQ_IP = "localhost";
    private static final int DEFAULT_RABBITMQ_PORT = 5672;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(ControllerRepository repository) {
        return (args) -> {
            // save a couple of customers

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Controler controler : repository.findAll()) {
                log.info(controler.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Controler controler = repository.findOne("ctl1");
            log.info("Customer found with findOne(ctl1):");
            log.info("--------------------------------");
            log.info(controler.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByFlagId('ctl5'):");
            log.info("--------------------------------------------");
            for (Controler bauer : repository.findByFlagId("ctl5")) {
                log.info(bauer.toString());
            }
            log.info("");




            //A adapter avec la couche de persistance et pas controler list
            String host = DEFAULT_RABBITMQ_IP;
            if (args.length > 0) {
                host = args[0];
            }
            System.out.println("Connexion a RabbitMQ a l'adresse " + host + ":" + DEFAULT_RABBITMQ_PORT + " ...");

            final QueueHandler qh = new QueueHandler(host);

            System.out.println("Serveur connecte");

            //Ici à terme on aura théoriquement une boucle qui attends qu'on lui dise d'envoyer des messages.
            //et qui les enverra comme ci dessous:
            System.out.println("Envoi des patterns aux controleurs connus");
            //Envoi des patterns par défaut
            for (Controler controlers : repository.findAll()) {
                try {
                    qh.sendMessage(controlers.getFlagId(),"{ \"pattern\" : \"default\" }" );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Patterns initiaux envoyes");

            System.out.println(" [*] Attente de message. CTRL+C pour quitter");

            qh.onMessage(new MessageListener() {
                @Override
                public void onMessage(String tag, byte[] body) {
                    try {
                        String message = new String(body, "UTF-8");
                        System.out.println(" [x] Received '" + message + "'");
                        //Envoie des patterns après reception du message et eventuelle modification
                        for (Controler controlers : repository.findAll()) {
                            try {
                                qh.sendMessage(controlers.getFlagId(),controlers.getPattern() );

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("");
                    } catch (UnsupportedEncodingException ex) {
                    }
                }
            });

            // TODO: Things ? Or a better way to do this.
            while (true);


        };
    }

}