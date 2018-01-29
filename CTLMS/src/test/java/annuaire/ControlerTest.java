package annuaire;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ControlerTest {
    //To refactor with persistance layer (not done yet)
    /*@Test
    public void initializationTest() throws Exception {
        assertEquals(3, ControlerList.getControlers().size());
    }

    @Test
    public void flagsTest() throws Exception {
        assertEquals("ctrl2", ControlerList.getControler(1).getFlagId());
    }

    @Test
    public void sendPatternTest() throws Exception {
        QueueHandler qh_mocked = Mockito.mock(QueueHandler.class);
        ArgumentCaptor<String> flag_captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> desc_captor = ArgumentCaptor.forClass(String.class);
        doNothing().when(qh_mocked).sendMessage(flag_captor.capture(), desc_captor.capture());

        ControlerList.sendModifiedPattern(qh_mocked);

        for (int i = 0; i < flag_captor.getAllValues().size(); i++) {
            assertEquals("ctrl" + Integer.toString(i + 1), flag_captor.getAllValues().get(i));
        }
    }*/
}