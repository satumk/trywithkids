
package trywithkids.domain;

import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import trywithkids.database.Database;
import trywithkids.database.DatabaseUsers;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

/**
 *
 * @author satu
 */
public class TryWithKidsTest {

    TryWithKids t;
    Database b;
    Morphia m;
    Datastore d;
    DatabaseUsers u;

    /**
     * Before each test morphia is setup, trywithkids and gui is mapped, datastore is created, database +
     * databaseUsers is created and both are cleared.
     */
    @Before
    public void setUp() {
        m = new Morphia();
        m.mapPackage("trywithkids.domain");
        m.mapPackage("trywithkids.gui");
        Datastore d = m.createDatastore(new MongoClient(), "test");
        d.ensureIndexes();;
        b = new Database(m, d);
        u = new DatabaseUsers(m, d);
        t = new TryWithKids(b, u); 
        t.clearDatabase();
        u.clearDatabaseOfUsers();
        
    }

    /**
     * tests for presense of experiments in the beginning
     */
    @Test
    public void noExperimentsInBeginning() {
        assertEquals(0, t.databaseSize());
    }
    
    /**
     * tests whether there are four startup experiments in the database as there should be
     * in the beginning. Does the databaseSize-method work?
     */
    @Test
    public void starterExperiments() {
        t.addStarterExperiments();
        assertEquals(4, t.databaseSize());
    }
    
    
    /**
     * one experiment is saved to database and then checked if the database size is increased by 1
     */
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
    
    /**
     * clears database test - does it clear the database of experiments
     */
    @Test
    public void clearDatabase() {
        t.addStarterExperiments();
        t.clearDatabase();
        assertEquals(0, t.databaseSize());
    }
    
    /**
     * does findAll work and bring back four as it should
     */
    @Test
    public void findAll() {
        t.addStarterExperiments();
        assertEquals(4, t.findAll().size());
    }
    
    /**
     * tests search by subject and duration
     */
    @Test
    public void searchBothCriteria() {
        t.addStarterExperiments();
        String subject = "Biology";
        List<Experiment> search = t.search(subject, 20);
        assertEquals(2, search.size());
    }
    
    /**
     * search by subject is tested
     */
    @Test
    public void searchPhysics() {
        t.addStarterExperiments();
        String subject = "Physics";
        List<Experiment> subjectSearch = t.search(subject, 0);
        assertEquals(1, subjectSearch.size());
    }
    
    /**
     * search by duration is tested
     */
    @Test
    public void searchDuration() {
        t.addStarterExperiments();
        String subject = "empty";
        List<Experiment> durationSearch = t.search(subject, 5);
        assertEquals(1, durationSearch.size());
    }
    
    /**
     * search without criteria with empty subject, should return empty.
     */
    @Test
    public void searchEmptyCriteria() {
        t.addStarterExperiments();
        String subject = "empty";
        List<Experiment> durationSearch = t.search(subject, 0);
        assertEquals(0, durationSearch.size());
    }
    
    /**
     * tests delete one-method
     */
    @Test
    public void deleteOne() {
        t.addStarterExperiments();
        String subject = "empty";
        List<Experiment> results = t.search(subject, 5);
        Experiment one = results.remove(0);
        t.deleteOne(one);
        assertEquals(3, t.findAll().size());
    }    
    
    /**
     * updating an experiment with an "empty" subject, the subject should not change
     */
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
    
    /**
     * checks whether one can remove duration info from database (they should not be able to). previous value
     * should remain
     */
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
    
    /**
     * tests update
     */
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
        String notesTest = testexp.getNotes();
        String scienceTest = testexp.getTheScience();
        String waittimeTest = testexp.getWaitTime();
        
        assertEquals("another test", topicTest);
        assertEquals("another set of materials", materialsTest);
        assertEquals("still time and patience", directionsTest);
        assertEquals(15, durationTest);
        assertEquals("none", notesTest);
        assertEquals("very little", scienceTest);
        assertEquals("none", waittimeTest);
    }
    
    /**
     * tests shortinfo content
     */
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
    }
    
    /**
     * tests to string content
     */
    @Test
    public void experimentToString() {
        String subject = "Physics";
        String topic = "test topic";
        String materials ="patience and time";
        String directions = "set test, test and study results";
        String waittime = "none";
        int duration = 10;
        String notes = "none";
        String science = "very little";
        
        Experiment one = new Experiment(subject, topic, duration, waittime, materials, directions, notes, science);
        String info = one.toString();
        assertEquals("\nSubject: Physics\nExperiment: test topic\nDuration of experiment: 10"
                + " minutes\nWaiting time: none\nMaterials: patience and time\nDirections: \n"
                + "set test, test and study results\nNotes on this experiment: none\nThe Science: very little\n", info);
    }
    
    /**
     * tests that there are no users in the database in the beginning
     */
    @Test
    public void noUsersInBeginning() {
        assertEquals(0, t.getUserN());
    }
    
    /**
     * adds default maintenance and checks user amount and their credentials
     */
    @Test
    public void addDefaultMaintenance() {
        t.addDefaultMaintenance();
        List<User> users = t.findAllUsers();
        assertEquals(1, users.size());
        assertEquals(true, users.get(0).getMaintenance());
        assertEquals("maintenance", users.get(0).getUsername());
        assertEquals("main_auth123", users.get(0).getPassword());
        assertEquals(0, users.get(0).getExperiments().size());
    }
    
    /**
     * tests the same for end-user
     */
    @Test
    public void addDefaultEnduser() {
        t.addDefaultMaintenance();
        t.addDefaultEnduser();
        List<User> users = t.findAllUsers();
        assertEquals(2, users.size());
        assertEquals(true, users.get(0).getMaintenance());
        assertEquals(false, users.get(1).getMaintenance());
    }
    
    /**
     * sets a new user
     */
    @Test
    public void setUser() {
        String username = "test";
        String password = "testPass";
        Boolean maintenance = false;
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setMaintenance(maintenance);
        assertEquals("test", newUser.getUsername());
        assertEquals("testPass", newUser.getPassword());
        assertEquals(false, newUser.getMaintenance());
    }
    
    /**
     * checks user tostring method content n maintenance
     */
    @Test
    public void userToStringMaintenance() {
        t.addDefaultMaintenance();
        List<User> users = t.findAllUsers();
        String userToString = users.get(0).toString();
        assertEquals("Username: maintenance\nPassword: main_auth123\nRole: maintenance\nExperiments in list: 0", userToString);
    }
    
    /**
     * checks user to string method content in end-user
     */
    @Test 
    public void userToStringEnduser() {
        t.addDefaultEnduser();
        List<User> users = t.findAllUsers();
        String userToString = users.get(0).toString();
        assertEquals("Username: end-user\nPassword: end_auth987\nRole: user\nExperiments in list: 0", userToString);
    }
     
    /**
     * deletes a specific user
     */
    @Test
    public void deleteUser() {
        assertEquals(0, t.getUserN());
        t.addDefaultEnduser();
        assertEquals(1, t.getUserN());
        List<User> users = t.findAllUsers();
        User user = users.get(0);
        t.deleteUser(user);
        assertEquals(0, t.getUserN());
    }
    
    /**
     * changes the password of a specific user
     */
    @Test
    public void changePassword() {
        t.addDefaultEnduser();
        User test = t.findAllUsers().get(0);
        String password = test.getPassword();
        assertEquals("end_auth987",password);
        String newPass = "ending";
        assertEquals("ending", newPass);
        String newerPass = "beginning";
        t.changePassword(test, newerPass);
        User test2 = t.findAllUsers().get(0);
        assertEquals("beginning", test2.getPassword());
    }
    
    /**
     * adds user through GUI (method, not actually through GUI) 
     */
    @Test
    public void addUserGUI() {
        String name = "test";
        String pass = "test1";
        Boolean admin = true;
        t.addUserThroughGUI(name, pass, admin);
        User user = t.findAllUsers().get(0);
        assertEquals("test", user.getUsername());
        assertEquals("test1", user.getPassword());
        assertEquals(true, user.getMaintenance());
    }
    
    /**
     * checks username
     */
    @Test
    public void checkUserName() {
        t.addDefaultEnduser();
        assertEquals(true, t.checkUsernameExists("end-user"));    
    }
    
    /**
     * checks user credentials
     */
    @Test
    public void checkUser() {
        String falseName = "false";
        String falsePass = "false1";
        
        assertEquals(2, t.checkUser("end-user", falsePass));
        t.addDefaultEnduser();
        t.addDefaultMaintenance();
                
        assertEquals(4, t.checkUser("end-user", "end_auth987"));
        
        t.addDefaultEnduser();
        assertEquals(1, t.checkUser("end-user", "end_auth987"));
        
        assertEquals(3, t.checkUser("maintenance", "end_auth987"));  
    }
    
    /**
     * does login return a user
     */
    @Test
    public void login() {
        t.addDefaultMaintenance();
        t.checkUser("maintenance", "main_auth123");
        assertEquals("maintenance", t.login().getUsername());
    }
    
    /**
     * does username exist
     */
    @Test
    public void findUsername() {
        t.addDefaultMaintenance();
        assertEquals(true, u.findUsername("maintenance"));
        assertEquals(false, u.findUsername("end-user"));
    }
    
    /**
     * can an experiment be added to user and deleted from them
     */
    @Test
    public void addExpToUserAndDelete() {
        t.addDefaultMaintenance();
        
        String subject = "Physics";
        String topic = "test topic";
        String materials ="patience and time";
        String directions = "set test, test and study results";
        String waittime = "none";
        int duration = 10;
        String notes = "none";
        String science = "very little";
        
        Experiment one = new Experiment(subject, topic, duration, waittime, materials, directions, notes, science);
        t.createExperimentAndSave(subject, topic, duration, waittime, materials, directions, notes, science);
        
        Experiment testexp = t.findAll().get(0);
        
        User user = t.findAllUsers().get(0);
        t.addExpToUser(user, testexp);
        List<Experiment> usersList = t.viewUsersExperimentsList(user);
        assertEquals(1, usersList.size());
        t.deleteFromUserlist(user, 0);
        List<Experiment> usersList2 = t.viewUsersExperimentsList(user);
        assertEquals(0, usersList2.size());
    }

}
