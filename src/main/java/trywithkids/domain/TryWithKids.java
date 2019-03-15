
package trywithkids.domain;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import trywithkids.database.Database;


public class TryWithKids {
    private Database database;
    private int numberOfExperiments;
    
    public TryWithKids(Database database) {
        this.database = database;
        this.numberOfExperiments = 0;
    }
    
    public int getNumber() {
        return this.numberOfExperiments;
    }
    
    public void saveToDatabase(Experiment experiment) {
        this.database.save(experiment);
        this.numberOfExperiments ++;
    }
    
    public void createExperimentAndSave(String subject, String topic, int duration, String waitTime, String materials, String directions, String notes, String theScience) {
        Experiment novel = new Experiment(subject, topic, duration, waitTime, materials, directions, notes, theScience);
        saveToDatabase(novel);
    }
    
    public List<Experiment> findAll() {
        List<Experiment> fromDatabase = this.database.findAll();
        return fromDatabase;
    }
    
    public int databaseSize() {
        return findAll().size();
    }
    
    public void clearDatabase() {
        this.database.deleteAllDatabase();
        this.numberOfExperiments = 0;
    }
    
    public void deleteOne(Experiment experiment) {
        this.database.deleteOne(experiment);
    }
    
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
        four.setDirections("1. Mix corn starch, water and foor colouring. \n2. Play with the result.\nOptionally add more water or corn starch until you are happy with the texture and behaviour.");
        four.setTheScience("Magic mud behaves like solid material if force is applied, but otherwise acts as a liquid.");
        four.setNotes("Difficult to clean as it acts as solid when force is applied");
        saveToDatabase(four);
    }

}
