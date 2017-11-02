package annuaire;

import org.junit.Test;

import static org.junit.Assert.*;

public class ControlerListTest {

    @Test
    public void initializationTest() throws Exception {
        assertEquals(3, ControlerList.getControlers().size());
    }

    @Test
    public void flagsTest() throws Exception {
        assertEquals("ctrl2", ControlerList.getControler(1).getFlagId());
    }
}