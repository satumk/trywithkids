
package trywithkids.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TryWithKidsTest {

    TryWithKids m;

    @Before
    public void setUp() {
        m = new TryWithKids();
    }

    
    @Test
    public void noExperimentsInBeginning() {
        assertEquals(0, m.listSize());
    }
    
    @Test
    public void starterExperiments() {
        m.addStarterExperiments();
        assertEquals(4, m.listSize());
    }
/*
    @Test
    public void alkuarvoEiNegatiivinen() {
        TryWithKids m2 = new TryWithKids(0);
        assertEquals(0, m2.lukema());
    }

    @Test
    public void alkuarvoNegatiivinen() {
        TryWithKids m2 = new TryWithKids(-1);
        assertEquals(0, m2.lukema());
    }

    @Test
    public void lisaaToimii() {
        m.lisaa();
        assertEquals(1, m.lukema());
    }
*/
}
