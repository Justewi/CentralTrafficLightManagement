package ctlms;

import ctlms.model.City;
import ctlms.model.Controler;
import ctlms.model.Pattern;
import ctlms.pattern_calculator.PatternCalculator;
import ctlms.queue_handling.MessageListener;
import ctlms.queue_handling.QueueHandler;
import ctlms.repository.CityRepository;
import ctlms.repository.ControllerRepository;
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

    private City city;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(ControllerRepository controllerRepository, CityRepository cityRepository) {
        return (args) -> {
            // save a couple of customers

            City nyc = new City(10, 10, "NYC");

            PatternCalculator.greenWaveXY(nyc, 9, 9, 0, 0, 70, 30);

            cityRepository.save(nyc);

            city = cityRepository.findOne("NYC");

            //PatternCalculator.greenWaveXY(city, 1, 1, 10, 10, 70, 30);

            //cityRepository.save(city);


            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Controler controler : city.getControlerList()) {
                log.info(controler.toString());
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


            city = cityRepository.findOne("NYC");

            //Ici à terme on aura théoriquement une boucle qui attends qu'on lui dise d'envoyer des messages.
            //et qui les enverra comme ci dessous:
            System.out.println("Envoi des patterns aux controleurs connus");
            //Envoi des patterns par défaut
            for (Controler controlers : city.getControlerList()) {
                try {
                    qh.sendMessage(controlers.getFlagId(),controlers.getPattern().getDescription() );
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
                        for (Controler controlers : city.getControlerList()) {
                            try {
                                qh.sendMessage(controlers.getFlagId(),controlers.getPattern().getDescription() );

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