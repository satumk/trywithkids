
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
        t.clearDatabase();
    }

    @Test
    public void noExperimentsInBeginning() {
        assertEquals(0, t.databaseSize());
    }
    
    @Test
    public void starterExperiments() {
        t.addStarterExperiments();
        assertEquals(4, t.databaseSize());
    }
    
    @Test
    public void getExperiments() {
        t.addStarterExperiments();
        assertEquals(4, t.databaseSize());
    }
    
    @Test
    public void databaseEmptyInBeginning() {
        assertEquals(0, t.findAll().size());
    }
    
    @Test
    public void saveOneToDatabase() {
        String subject = "Physics";
        String topic = "test topic";
        String materials ="patience and time";
        String directions = "set test, test and study results";
        String waittime = "none";
        int duration = 10;
        String notes = "none";
        String science = "very little";
        Experiment one = new Experiment();
       
        t.createExperimentAndSave(subject, topic, duration, waittime, materials, directions, notes, science);
        assertEquals(1, t.databaseSize());
    }
    
    @Test
    public void clearDatabase() {
        t.addStarterExperiments();
        t.clearDatabase();
        assertEquals(0, t.databaseSize());
    }
    
    @Test
    public void sizeOfDatabase() {
        t.addStarterExperiments();
        assertEquals(4, t.databaseSize());
    }
    
    @Test
    public void findAll() {
        t.addStarterExperiments();
        assertEquals(4, t.findAll().size());
    }
    
    @Test
    public void searchBothCriteria() {
        t.addStarterExperiments();
        String subject = "Biology";
        List<Experiment> search = t.search(subject, 20);
        assertEquals(2, search.size());
    }
    
    @Test
    public void searchPhysics() {
        t.addStarterExperiments();
        String subject = "Physics";
        List<Experiment> subjectSearch = t.search(subject, 0);
        assertEquals(1, subjectSearch.size());
    }
    
    @Test
    public void searchDuration() {
        t.addStarterExperiments();
        String subject = "empty";
        List<Experiment> durationSearch = t.search(subject, 5);
        assertEquals(1, durationSearch.size());
    }
    
    @Test
    public void searchEmptyCriteria() {
        t.addStarterExperiments();
        String subject = "empty";
        List<Experiment> durationSearch = t.search(subject, 0);
        assertEquals(0, durationSearch.size());
    }
    
    @Test
    public void deleteOne() {
        t.addStarterExperiments();
        String subject = "empty";
        List<Experiment> results = t.search(subject, 5);
        Experiment one = results.remove(0);
        t.deleteOne(one);
        assertEquals(3, t.findAll().size());
    }    
    
    @Test
    public void updateEmptySubject() {
        String subject = "Physics";
        String topic = "test topic";
        String materials ="patience and time";
        String directions = "set test, test and study results";
        String waittime = "none";
        int duration = 10;
        String notes = "none";
        String science = "very little";
        
        t.createExperimentAndSave(subject, topic, duration, waittime, materials, directions, notes, science);
        
        List<Experiment> firstExperiment = t.findAll();
        Experiment oldOne = firstExperiment.get(0);
        
        String subject2 = "empty";
        
        t.update(oldOne, subject2, topic, duration, waittime, materials, directions, notes, science);
        List<Experiment> testingItem = t.findAll();
        Experiment testexp = testingItem.get(0);
        String subjectTest = testexp.getSubject();
        
        assertEquals("Physics", subjectTest);
    }
    
    @Test
    public void updateEmptyDuration() {
        String subject = "Physics";
        String topic = "test topic";
        String materials ="patience and time";
        String directions = "set test, test and study results";
        String waittime = "none";
        int duration = 20;
        String notes = "none";
        String science = "very little";
        
        t.createExperimentAndSave(subject, topic, duration, waittime, materials, directions, notes, science);
        
        List<Experiment> firstExperiment = t.findAll();
        Experiment oldOne = firstExperiment.get(0);
        
        int duration2 = 0;
        
        t.update(oldOne, subject, topic, duration2, waittime, materials, directions, notes, science);
        List<Experiment> testingItem = t.findAll();
        Experiment testexp = testingItem.get(0);
        int durationTest = testexp.getDuration();
        
        assertEquals(20, durationTest);
    }
    
    @Test
    public void update() {
        String subject = "Physics";
        String topic = "test topic";
        String materials ="patience and time";
        String directions = "set test, test and study results";
        String waittime = "none";
        int duration = 20;
        String notes = "none";
        String science = "very little";
        
        t.createExperimentAndSave(subject, topic, duration, waittime, materials, directions, notes, science);
        
        List<Experiment> firstExperiment = t.findAll();
        Experiment oldOne = firstExperiment.get(0);
        
        Experiment one = new Experiment();
        String subject2 = "Physics";
        one.setSubject(subject2);
        one.setTopic("another test");
        String topic2 = "another test";
        one.setMaterials("another set of materials");
        String materials2 = "another set of materials";
        one.setDirections("still time and patience");
        String directions2 = "still time and patience";
        int duration2 = 15;
        one.setDuration(15);
        one.setDirections(directions);
        one.setNotes(notes);
        one.setTheScience(science);
        
        t.update(oldOne, subject2, topic2, duration2, waittime, materials2, directions2, notes, science);
        List<Experiment> testingItem = t.findAll();
        Experiment testexp = testingItem.get(0);
        int durationTest = testexp.getDuration();
        String topicTest =testexp.getTopic();
        String materialsTest = testexp.getMaterials();
        String directionsTest = testexp.getDirections();
        
        assertEquals("another test", topicTest);
        assertEquals("another set of materials", materialsTest);
        assertEquals("still time and patience", directionsTest);
        assertEquals(15, durationTest);

    }
    
    @Test
    public void shortInfo() {
        String subject = "Physics";
        String topic = "test topic";
        String materials ="patience and time";
        String directions = "set test, test and study results";
        String waittime = "none";
        int duration = 10;
        String notes = "none";
        String science = "very little";
        
        Experiment one = new Experiment(subject, topic, duration, waittime, materials, directions, notes, science);
        String shortInfo = one.shortInfo();
        assertEquals("Experiment: test topic\nDuration of experiment: 10 minutes\nWaiting time: none\nMaterials: patience and time", shortInfo);
        /*
                public String shortInfo() {
        String info = "Experiment: " + this.topic + "\nDuration of experiment: "
                + this.duration + " minutes\nWaiting time: " + this.waitTime + "\nMaterials: " + this.materials;

        return info;
    }
*/
    }
}
