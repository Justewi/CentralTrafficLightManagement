package annuaire;

import annuaire.repository.ControllerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringMongoConfiguration.class})
public class ControlerTest {

    @Autowired
    ControllerRepository controllerRepository;


    @Before
    public void init(){

        controllerRepository.save(new Controler("ctl1" , "{ \"pattern\" : \"default\" }"));
        controllerRepository.save(new Controler("ctl2" , "{ \"pattern\" : \"default\" }"));
        controllerRepository.save(new Controler("ctl3" , "{ \"pattern\" : \"default\" }"));
        controllerRepository.save(new Controler("ctl4" , "{ \"pattern\" : \"default\" }"));

    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void flagsTest() throws Exception {
        assertEquals("ctl2", controllerRepository.findOne("ctl2").getFlagId());
    }

    @Test
    public void sendPatternTest() throws Exception {
        QueueHandler qh_mocked = Mockito.mock(QueueHandler.class);
        ArgumentCaptor<String> flag_captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> desc_captor = ArgumentCaptor.forClass(String.class);
        doNothing().when(qh_mocked).sendMessage(flag_captor.capture(), desc_captor.capture());


        for (Controler controlers : controllerRepository.findAll()) {
            try {
                qh_mocked.sendMessage(controlers.getFlagId(),controlers.getPattern() );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < flag_captor.getAllValues().size(); i++) {
            assertEquals("ctl" + Integer.toString(i + 1), flag_captor.getAllValues().get(i));
        }
    }
}