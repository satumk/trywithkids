
package trywithkids.domain;

import com.mongodb.MongoClient;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import trywithkids.database.Database;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class TryWithKidsTest {

    TryWithKids t;
    Database b;
    Morphia m;
    Datastore d;

    @Before
    public void setUp() {
        m = new Morphia();
        m.mapPackage("trywithkids.domain");
        m.mapPackage("trywithkids.gui");
        Datastore d = m.createDatastore(new MongoClient(), "test");
        d.ensureIndexes();;
        b = new Database(m, d);
        t = new TryWithKids(b);    
    }

    @Test
    public void noExperimentsInBeginning() {
        assertEquals(0, t.listSize());
    }
    
    @Test
    public void starterExperiments() {
        t.addStarterExperiments();
        assertEquals(4, t.listSize());
    }
    
    @Test
    public void getExperiments() {
        t.addStarterExperiments();
        assertEquals(4, t.getExperiments().size());
    }
    
    @Test
    public void saveOneToDatabase() {
        t.clearDatabase();
        t.addStarterExperiments();
        Experiment one = t.getExperiments().get(0);
        t.saveToDatabase(one);
        assertEquals(1, t.databaseSize());
    }
    
    @Test
    public void clearDatabase() {
        t.clearDatabase();
        assertEquals(0, t.databaseSize());
    }
    
    @Test
    public void sizeOfDatabase() {
        t.clearDatabase();
        t.addStarterExperiments();
        t.addStartersToDatabase();
        assertEquals(4, t.databaseSize());
    }
    
    @Test
    public void findAll() {
        t.clearDatabase();
        t.addStarterExperiments();
        t.addStartersToDatabase();
        assertEquals(4, t.findAll().size());
    }
    
    @Test
    public void setExperimentTopic() {
        
    }
    
    @Test
    public void setExperimentSubject() {
        
    }
    
    @Test
    public void setExperimentDuration() {
        
    }
    
    @Test
    public void setExperimentWaitTime() {
        
    }
    
    @Test
    public void setExperimentMaterials() {
        
    }
    
    @Test
    public void setExperimentDirections() {
        
    }
    
    @Test
    public void setExperimentTheScience() {
        
    }
    
    @Test
    public void setExperimentNotes() {
        
    }

}
