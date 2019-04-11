
package trywithkids.domain;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import trywithkids.database.Database;
import trywithkids.database.DatabaseUsers;

/**
 * @author satu
 * This class contains the main logic of the application. It also connects to the class Database, 
 * that discusses with the MongoDB database.
 */
public class TryWithKids {
    private Database database;
    private DatabaseUsers userDatabase;
    private User user;
    
    /**
     * The constructor takes as parameter instance of class database (for experiments) 
     * and databaseUsers (for users) that is used when using the MongoDB database.
     * @param database instance of class database (for experiments) 
     * @param userdatabase instance of class database (for users) 
     */
    public TryWithKids(Database database, DatabaseUsers userdatabase) {
        this.database = database;
        this.userDatabase = userdatabase;
    }
    
    /**
     * Returns this.user so other parts of program know which user is using the software
     * @return this specific user-instance to pass onward to GUI to determine which views are allowed
     */
    public User login() {
        return this.user;
    }
    
    /**
     * Checks if the username is already in use. Param is String username. returns boolean.
     * 
     * @param usernameGUI String of the wanted username
     * @return boolean info whether that specific username is already in use. Only one may exist in database
     */
    public Boolean checkUsernameExists(String usernameGUI) {
        Boolean exists = this.userDatabase.findUsername(usernameGUI);
        return exists;
    }
    
    /**
     * Method checks the user credentials with one query. Params are String username and String password from GUI. 
     * If return-value is 1: there are more than 1 user with that database (should not happen)
     * If return-value is 2: there are no users with that username in the database
     * if return-value is 3: there is a matching user (just the one), but the supplied password does not match
     * if return-value is 4: there is a matching user and the password matches
     * if return-value is 5: unknown error has occurred.
     * @param usernameGUI String of username
     * @param passwdGUI String of password
     * @return integer depending on how login went
     */
    public int checkUser(String usernameGUI, String passwdGUI) {
        List<User> users = new ArrayList<>();
        users = this.userDatabase.checkLoginInfo(usernameGUI);
        if (users.isEmpty()) {
            return 2;
        } else if (users.size() > 1) {
            return 1;
        } else if (!users.get(0).getPassword().equals(passwdGUI)) {
            return 3;
        } else if (users.size()==1 && users.get(0).getPassword().equals(passwdGUI)) {
            this.user = users.get(0);
            return 4;
        } else {
            return 5;
        }
    }
    
    /**
     * saves new user to database if password is over 8 char long and username is
     * not already in use in database.
     * @param username String name selected to represent the user
     * @param passwd String password selected to guard entry into the program
     * @param maintenance Boolean whether this user has maintenance-privileges
     */
    public void addUserThroughGUI(String username, String passwd, Boolean maintenance) {
        User newUser = new User(username, passwd, maintenance);
        this.userDatabase.save(newUser);
    }
    
    /**
     * Adds default Admin/maintenance user to database + login-info
     */
    public void addDefaultMaintenance() {
        User newUser = new User("maintenance", "main_auth123", true);
        this.userDatabase.save(newUser);
    }
    
    /**
     * Adds default end-user to database + login-info
     */
    public void addDefaultEnduser() {
        User newUser = new User("end-user", "end_auth987", false);
        this.userDatabase.save(newUser);
    }
    
    /**
     * deletes one user from database
     * @param user instance of user-class to be deleted from database
     */
    public void deleteUser(User user) {
        this.userDatabase.delete(user);    
    }
    
    /**
     * adds an experiment to userlist. Gets user-param and experiment from GUI, saves to database
     * @param userfromGUI User-class instance (from GUI) to which the experiment is added
     * @param experiment the experiment-instance that is added to the user
     */
    public void addExpToUser(User userfromGUI, Experiment experiment) {
        User userinDatabase = this.userDatabase.findUser(userfromGUI);
        userinDatabase.addExperiment(experiment);
        this.userDatabase.updateUser(userfromGUI, userinDatabase);
    }
    
    /**
     * deletes a single experiment from userlist. Params are User-class instance (whose list) and experiment 
     * to be deleted.
     * @param userfromGUI  User-instance from which an experiment is deleted from
     * @param index index of the experiment that is deleted from users list
     */
    public void deleteFromUserlist(User userfromGUI, int index) {
        User userinDatabase = this.userDatabase.findUser(userfromGUI);
        userinDatabase.getExperiments().remove(index);
        this.userDatabase.updateUser(userfromGUI, userinDatabase);
    }
    
    /**
     * returns the experiments of an individual user
     * @param user instance of user-class, whose experiments one wishes to explore
     * @return returns a list of those experiments
     */
    public List<Experiment> viewUsersExperimentsList(User user) {
        User userinDatabase = this.userDatabase.findUser(user);
        List<Experiment> userExpList = userinDatabase.getExperiments();
        return userExpList;
    }
    
    /**
     * changes the password of a user. 
     * @param userfromGUI User-class instance passed from GUI
     * @param newPassWd new String to be added into the database as password
     */
    public void changePassword(User userfromGUI, String newPassWd) {
        User userinDatabase = this.userDatabase.findUser(userfromGUI);
        userinDatabase.setPassword(newPassWd);
        this.userDatabase.updateUser(userfromGUI, userinDatabase);
    }
   
    /**
     * returns the number of users in database
     * @return integer of users (amount)
     */
    public int getUserN() {
        List<User> fromDatabase = this.userDatabase.findAll();
        return fromDatabase.size();
    }
    
    /**
     * Returns a list of all users in database
     * @return list of all users in database
     */
    public List<User> findAllUsers() {
        List<User> fromDatabase = this.userDatabase.findAll();
        return fromDatabase;
    }
    
    /**
     * Prints the experiment as a PDF-file. Takes one experiment as parameter.
     * @param exp experiment-class instance that is printed
     * @throws IOException should something go wrong
     * @throws DocumentException should something go wrong
     */
    public void print(Experiment exp) throws IOException, DocumentException {
        
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(exp.getTopic() + ".pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Font font2 = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
        Font font3 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Experiment directions\n", font2);
        Chunk topicHead = new Chunk("\nTopic: ", font3);
        Chunk chunkTopic = new Chunk(exp.getTopic(), font3);
        Chunk change = new Chunk("\n", font);
        Chunk subject = new Chunk(exp.getSubject(), font);
        Chunk durationHead = new Chunk("\nDuration: ", font3);
        String durationString = String.valueOf(exp.getDuration());
        Chunk durationChunk = new Chunk(durationString, font);
        Chunk waitTime = new Chunk("\nWaiting time: " + exp.getWaitTime(), font);
        Chunk materialsHead = new Chunk("\nMaterials:\n", font3);
        Chunk materials = new Chunk(exp.getMaterials(), font);
        Chunk directionsHead = new Chunk("\nDirections:\n", font3);
        Chunk directions = new Chunk(exp.getDirections(), font);
        Chunk scienceHead = new Chunk("\nThe Science behind the experiment: ", font3);
        Chunk science = new Chunk(exp.getTheScience(), font);
        Chunk notesHead = new Chunk("\nNotes:", font3);
        Chunk notes = new Chunk(exp.getNotes(), font);
        
        Paragraph info = new Paragraph();
        info.add(chunk);
        info.add(topicHead);
        info.add(chunkTopic);
        info.add(change);
        info.add(subject);
        
        Paragraph durationPara = new Paragraph();
        durationPara.add(durationHead);
        durationPara.add(durationChunk);
        durationPara.add(waitTime);
        
        Paragraph materialsPara = new Paragraph();
        materialsPara.add(materialsHead);
        materialsPara.add(materials);
        
        Paragraph direcPara = new Paragraph();
        direcPara.add(directionsHead);
        direcPara.add(directions);
        direcPara.add(notesHead);
        direcPara.add(notes);
        
        Paragraph sciencePara = new Paragraph();
        sciencePara.add(scienceHead);
        sciencePara.add(science);
        
        document.add(info);
        document.add(durationPara);
        document.add(materialsPara);
        document.add(direcPara);
        document.add(sciencePara);

        document.close();
    }
    
    /**
     * This method saves one instance of class Experiment given as parameter to database by
     * passing in on to class Database.
     * @param experiment instance of experiment-class saved to database
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
     * @param subject String
     * @param topic String
     * @param duration Integer
     * @param waitTime String
     * @param materials String
     * @param directions String
     * @param notes String
     * @param theScience String
     */
    public void createExperimentAndSave(String subject, String topic, int duration, String waitTime, String materials, String directions, String notes, String theScience) {
        Experiment novel = new Experiment(subject, topic, duration, waitTime, materials, directions, notes, theScience);
        saveToDatabase(novel);
    }
    
    /**
     * Method sends a request to class Database to find all experiments in the database and 
     * returns the list it gets back from Database.
     * @return a list of all experiments in database
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
     * @param subject sought after subject (either physics, chemistry or biology)
     * @param duration maximum desired duration of experiment
     * @return a list of all experiments that match these criteria
     */
    public List<Experiment> search(String subject, int duration) {
        List<Experiment> fromDatabase = new ArrayList<>();
        
        if (!subject.contains("empty") && duration !=0) {
            fromDatabase = this.database.findByBoth(subject, duration);
        } else if (!subject.contains("empty")) {
            fromDatabase = this.database.findBySubject(subject);
        } else if (duration !=0) {
            fromDatabase = this.database.findByDuration(duration);
        }

        return fromDatabase;
    }
    
    /**
     * This utilises the method findAll() to check the amount of experiments in Database by checking
     * the size of the returned list and returning that.
     * @return returns the amount of experiments in the database
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
     * @param experiment instance of experiment-class to be deleted from database
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
     * @param updateExp Experiment-instance to be updated
     * @param subject String
     * @param topic String
     * @param duration Integer
     * @param waittime String
     * @param materials String
     * @param directions String
     * @param notes String
     * @param thescience String
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
        four.setMaterials("5 tablespoons of corn starch // 3 tablespoons of water // (optional) food colouring");
        four.setDuration(10);
        four.setWaitTime("No waiting time needed");
        four.setDirections("1. Mix corn starch, water and food colouring. \n2. Play with the result.\nOptionally add more water or corn starch until you are happy with the texture and behaviour.");
        four.setTheScience("Magic mud behaves like solid material if force is applied, but otherwise acts as a liquid.");
        four.setNotes("Difficult to clean as it acts as solid when force is applied");
        saveToDatabase(four);
    }

    

}
