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
public class InitDB {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(ControllerRepository repository) {
        return (args) -> {
            repository.save(new Controler("ctl1" , "{ \"pattern\" : \"default\" }"));
            repository.save(new Controler("ctl2" , "{ \"pattern\" : \"default\" }"));
            repository.save(new Controler("ctl3" , "{ \"pattern\" : \"default\" }"));
            repository.save(new Controler("ctl4" , "{ \"pattern\" : \"default\" }"));
            repository.save(new Controler("ctl5" , "{ \"pattern\" : \"default\" }"));
        };
    }

}