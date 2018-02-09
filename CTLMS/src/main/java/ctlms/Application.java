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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Transactional
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final String DEFAULT_RABBITMQ_IP = "localhost";
    private static final int DEFAULT_RABBITMQ_PORT = 5672;

    private PatternCalculator patternCalculator = new PatternCalculator();

    private QueueHandler qh;

    private City city;


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Bean
    public CommandLineRunner demo(ControllerRepository controllerRepository, CityRepository cityRepository) {
        return (args) -> {

            initSystem(cityRepository, args);

            log.info(" [*] Attente de message. CTRL+C pour quitter");

            qh.onMessage(new MessageListener() {
                @Override
                public void onMessage(String tag, byte[] body) {
                    try {

                        String message = new String(body, "UTF-8");
                        log.info(" Received '" + message + "'");
                        //Envoie des patterns après reception du message et eventuelle modification
                        for (Controler controlers : city.getControlerList()) {
                            try {
                                qh.sendMessage(controlers.getFlagId(), controlers.getPattern().getDescription());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        log.info("");
                    } catch (UnsupportedEncodingException ex) {
                    }
                }
            });


            // TODO: Things ? Or a better way to do this.
            while (true) ;


        };
    }


    private void initSystem(CityRepository cityRepository, String[] args) throws IOException {
        City nyc = new City(10, 10, "NYC");


        patternCalculator.manifestation(nyc, 4,4,50,30);

        //PatternCalculator.greenWaveXY(nyc, 9, 9, 0, 0, 70, 30);

        cityRepository.save(nyc);

        city = cityRepository.findOne("NYC");


        String host = (args.length > 0) ? args[0] : DEFAULT_RABBITMQ_IP;

        log.info("Connexion a RabbitMQ a l'adresse " + host + ":" + DEFAULT_RABBITMQ_PORT + " ...");

        qh = new QueueHandler(host);

        log.info("Serveur connecte");


        //Ici à terme on aura théoriquement une boucle qui attends qu'on lui dise d'envoyer des messages.
        //et qui les enverra comme ci dessous:
        log.info("Envoi des patterns aux controleurs connus");
        //Envoi des patterns par défaut
        for (Controler controlers : city.getControlerList()) {
            try {
                qh.sendMessage(controlers.getFlagId(), controlers.getPattern().getDescription());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("Patterns initiaux envoyes");
    }

}