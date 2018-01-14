package annuaire;

import annuaire.repository.ControllerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(ControllerRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Controler("ctl1", "{ \"pattern\" : \"default\" "));
            repository.save(new Controler("ctl2", "{ \"pattern\" : \"default\" "));
            repository.save(new Controler("ctl3", "{ \"pattern\" : \"default\" "));
            repository.save(new Controler("ctl4", "{ \"pattern\" : \"default\" "));
            repository.save(new Controler("ctl5", "{ \"pattern\" : \"default\" "));

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
        };
    }

}