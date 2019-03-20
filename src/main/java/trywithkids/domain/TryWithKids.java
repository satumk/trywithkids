
package trywithkids.domain;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import trywithkids.database.Database;

/**
 * @author satu
 * This class contains the main logic of the application. It also connects to the class Database, 
 * that discusses with the MongoDB database.
 */
public class TryWithKids {
    private Database database;
    
    /**
     * The constructor takes as parameter instance of class database that is used when using the MongoDB database.
     * @param database 
     */
    public TryWithKids(Database database) {
        this.database = database;
    }

    /**
     * This method saves one instance of class Experiment given as parameter to database by
     * passing in on to class Database.
     * @param experiment 
     */
    public void saveToDatabase(Experiment experiment) {
        this.database.save(experiment);
    }
    
    /**
     * This experiment takes as parameters the subject, topic, duration (int), 
     * waitTime, materials, directions, notes and the science that comprise an instance of 
     * Experiment class. All other variables are String. It then creates an instance
     * of Experiment from this info and passes it on to Database-class.
     * 
     * @param subject
     * @param topic
     * @param duration
     * @param waitTime
     * @param materials
     * @param directions
     * @param notes
     * @param theScience 
     */
    public void createExperimentAndSave(String subject, String topic, int duration, String waitTime, String materials, String directions, String notes, String theScience) {
        Experiment novel = new Experiment(subject, topic, duration, waitTime, materials, directions, notes, theScience);
        saveToDatabase(novel);
    }
    
    /**
     * Method sends a request to class Database to find all experiments in the database and 
     * returns the list it gets back from Database.
     * @return List<Experiment>
     */
    public List<Experiment> findAll() {
        List<Experiment> fromDatabase = this.database.findAll();
        return fromDatabase;
    }
    
    /**
     * Method takes as parameters from GUI all possible search criteria.
     * It then checks, which is valid or if both are, then
     * sends a query-request to database-class instance accordingly.
     * As it receives the search result, it then returns the list to GUI
     * @param subject
     * @param duration
     * @return List<Experiment>
     */
    public List<Experiment> search(String subject, int duration) {
        List<Experiment> fromDatabase = new ArrayList<>();
        
        if (!subject.contains("empty") && duration !=0) {
            fromDatabase = this.database.findByBoth(subject, duration);
        } else if (!subject.contains("empty")) {
            fromDatabase = this.database.findBySubject(subject);
        } else if (subject.contains("empty") && duration !=0) {
            fromDatabase = this.database.findByDuration(duration);
        }

        return fromDatabase;
    }
    
    /**
     * This utilises the method findAll() to check the amount of experiments in Database by checking
     * the size of the returned list and returning that.
     * @return int
     */
    public int databaseSize() {
        return findAll().size();
    }
    
    /**
     * Method requests database to clear the mongoDB. It does not take parameters or
     * return anything.
     */
    public void clearDatabase() {
        this.database.deleteAllDatabase();
    }
    
    /**
     * Method asks the database-class to delete an experiment that it takes as
     * parameter. It passes this instance on to the database-class to be deleted.
     * @param experiment 
     */
    public void deleteOne(Experiment experiment) {
        this.database.deleteOne(experiment);
    }
    
    /**
     * Method parameters received from the GUI include the old version of the experiment as well as each
     * variable needed to create a new experiment. It checks that the subject- and duration
     * variables have a value and then creates the updated version of the experiment by using the set-methods
     * in class Experiment. It then passes the old version and the updated version to the
     * Database-class to be updated.
     * @param updateExp
     * @param subject
     * @param topic
     * @param duration
     * @param waittime
     * @param materials
     * @param directions
     * @param notes
     * @param thescience 
     */
    public void update(Experiment updateExp, String subject, String topic, int duration, String waittime, String materials, String directions, String notes, String thescience) {
        Experiment updating = updateExp;
        
        if (!subject.contains("empty")) {
            updating.setSubject(subject);
        } else {
            updating.setSubject(updateExp.getSubject());
        }
        if (duration != 0) {
            updating.setDuration(duration);
        } else {
            updating.setDuration(updateExp.getDuration());
        }
        updating.setTopic(topic);
        updating.setWaitTime(waittime);
        updating.setMaterials(materials);
        updating.setDirections(directions);
        updating.setNotes(notes);
        updating.setTheScience(thescience);

        this.database.update(updateExp, updating);
    }
    
    /**
     * This method contains some experiments to get the user started.
     * It is also utilised with testing the system.
     */
    public void addStarterExperiments() {
        Experiment one = new Experiment();
        one.setSubject("Biology");
        one.setTopic("Capillaries with celery");
        /* List<String> materials = new ArrayList<>();
        String celery = new String("Two or more stems of celery, at least one for each colour you wish to use");
        String glasses = new String("As many glasses as you have colours you wish to use");
        String water = new String("water enough to fill each glass 2/3 full");
        String colours = new String("food colourings (blue works especially well)");
        materials.add(celery);
        materials.add(glasses);
        materials.add(water);
        materials.add(colours); */
        one.setMaterials("Two or more stems of celery, at least one for each colour you wish to use // As many glasses as you have colours you wish to use // water enough to fill each glass 2/3 full // food colourings (blue works especially well)");
        one.setDuration(5);
        one.setWaitTime("At least a few hours, a day or more for clearest view of capillary veins");
        one.setDirections("1. Gather all materials. \n2. Add water to each glass you have. \n3. Add food colouring to each glass. "
                + "\n4. Add celery sticks and leave to wait. \n5. Once waiting time has passed, cut the celery. You can cut it along the stalk to see "
                + "how the colour rises along the stem. You can also cut across the stem to see a cross-section of the stem.");
        one.setTheScience("Plants use the capillary veins to bring water from the roots to the leaves. This"
                + " works in flowers and in trees the same way. As water is vaporised in the leaves, more water"
                + "gets pulled up to replace the vaporised water. The colour travels with the water, but"
                + "gets left behind as it does not evaporate, so leaves turn a different colour.");
        one.setNotes("Blue colour is easiest to see and works best for this experiment");
        this.saveToDatabase(one);
        
        Experiment two = new Experiment();
        two.setSubject("Physics");
        two.setTopic("Make a paper clip hover");
        /* List<String> materialsTwo = new ArrayList<>();
        materialsTwo.add(new String("a paper clip"));
        materialsTwo.add(new String("two magnets (circular ones worked really well)"));
        materialsTwo.add(new String("a ruler"));
        materialsTwo.add(new String("some string"));
        materialsTwo.add(new String("a way to hold the ruler up, f.ex towers made from Dublos"));*/
        two.setMaterials("paper clip // two magnets (circular ones worked really well) // ruler // string // a way to hold the ruler up, f.ex towers made from Dublos");
        two.setDuration(20);
        two.setWaitTime("no waiting time needed");
        two.setDirections("1. Construct your chosen way to hold the ruler up in the air. \n2. Thread the paper clip with the string"
                + "so you can pick it up by holding onto just the string. \n3. Place the magnets on either side of the ruler so they hold"
                + "each other in place. \n4. Place the ruler on top of your support structure."
                + "\n5. Let the clip hang by the string.\n6.Take the clip so close to the magnets that they touch."
                + "\n7. Pull the clip slowly downwards by the string until the magnetic pull suddenly disappears and the clip falls."
                + "\n8. Repeat as many times as you want");
        two.setTheScience("The magnet pulls the metal that is in the metal clip. If the clip is close enough that it is in "
                + "the magnetic field of the magnet, the pull of the magnet is stronger than the pull of gravity. "
                + "Once the clip is far enough away that it no longer is in the magnetic field, the weak force of gravity "
                + "takes hold again and the clip falls.");
        two.setNotes("You can involve the child in each step f.ex. Choose how to build the support structure together. "
                + "Either first show the experiment with the clip and then let the child experiment or give verbal instructions.");
        saveToDatabase(two);
        
        Experiment three = new Experiment();
        three.setSubject("Biology");
        three.setTopic("Decomposition box");
        /* List<String> matThree = new ArrayList<>();
        matThree.add(new String("a plastic box with lid"));
        matThree.add(new String("decomposing material: paper, tissues, plant material"));
        matThree.add(new String("non-decomposing material: rocks, tinfoil, screws, plastic, etc."));*/
        three.setMaterials("plastic box with lid // decomposing material: paper, tissues, plant material // non-decomposing material: rocks, tinfoil, screws, plastic, etc.");
        three.setDuration(15);
        three.setWaitTime("Several weeks - some months. Basically some changes happen in weeks, more changes happen the more time the materials have to decompose.");
        three.setDirections("1. Cut big pieces into smaller ones. \n2. Place both composing and decomposing materials into the plastic box. "
                + "\n3. Ask the child to guess (make a hypothesis) as to what will decompose and what will stay the same.\n4. Close the lid and place the box somewhere, where temperature is above freezing (above 0 degrees Celsius), but preferably somewhere even warmer "
                + "than this. Then wait. Come back every few weeks and observe changes. The things that decompose become unrecognisable while things like metal and plastic stay the same.");
        three.setTheScience("Decomposition is a process where material is broken up by bacteria. This is a long process by which plant material eventually turns into soil. Metals, plastic, rocks etc. do not decay in any timeframe that makes sense to us humans. "
                + "This is a good experiment explaining why rubbish should always be disposed of properly and not left out in nature.");
        three.setNotes("It is perfectly fine to forget about this experiment for months. Decay happens on its own.");
        saveToDatabase(three);
        
        Experiment four = new Experiment();
        four.setSubject("Chemistry");
        four.setTopic("Magic mud, between liquid and solid");
        /*List<String> matFour = new ArrayList<>();
        matFour.add(new String("5 tablespoons of corn starch"));
        matFour.add(new String("3 tablespoons of water"));
        matFour.add(new String("(optional) food colouring"));*/
        four.setMaterials("5 tablespoons of corn starch // 3 tablespoons of water // (optional) food colouring");
        four.setDuration(10);
        four.setWaitTime("No waiting time needed");
        four.setDirections("1. Mix corn starch, water and food colouring. \n2. Play with the result.\nOptionally add more water or corn starch until you are happy with the texture and behaviour.");
        four.setTheScience("Magic mud behaves like solid material if force is applied, but otherwise acts as a liquid.");
        four.setNotes("Difficult to clean as it acts as solid when force is applied");
        saveToDatabase(four);
    }

}
