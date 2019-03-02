
package trywithkids.domain;

import java.util.List;
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
    
    @Test
    public void toAndFromDatabase() {
        m.addStarterExperiments();
        assertEquals(4, m.listSize());
        List<Experiment> experiments = m.getExperiments();
        for (Experiment exp : experiments) {
            m.saveToDatabase(exp);
        }
        assertEquals(4, m.databaseSize());
    }
    
    
}
