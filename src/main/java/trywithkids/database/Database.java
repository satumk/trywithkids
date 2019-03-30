
package trywithkids.database;

import java.util.List;
import org.bson.types.ObjectId;
import xyz.morphia.Morphia;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.Experiment;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

/**
 * @author satu
 * This class is responsible for all conversation with the database relating with
 * experiments.
 */

public class Database {
    private Morphia morphia;
    private Datastore datastore;
    
    /**
     * The constructor takes as parameter Morphia and the datastore created when the application is launched.
     * @param morphia
     * @param datastore 
     */
    public Database(Morphia morphia, Datastore datastore) {
        this.morphia = morphia;
        this.datastore = datastore;
    }
    
    /**
     * This method takes an Experiment-class instance as parameter and saves it to the database
     * @param experiment 
     */
    public void save(Experiment experiment) {
        datastore.save(experiment);
    }

    /**
     * This method does not have parameters. It returns a list of Experiment-class instances 
     * containing all the experiments in the database.
     * @return List<Experiment>
     */
    public List<Experiment> findAll() {
        Query<Experiment> query = datastore.createQuery(Experiment.class);
        List<Experiment> experiments = query.asList();     
        return experiments;
    }
    
    /**
     * This method clears the database of all instances of class Experiment. It does not take parameters
     */
    public void deleteAllDatabase() {
        Query<Experiment> queryDelete = datastore.createQuery(Experiment.class);
        datastore.delete(queryDelete);
    }
    
    /**
     * This method deletes a specific instance of class Experiments given as parameter from the database.
     * @param experiment 
     */
    public void deleteOne(Experiment experiment) {
        datastore.delete(experiment);
    }

    /**
     * This method updates the information of a specific instance of Experiment class in the database.
     * It takes as parameter the current version in the database, which is deleted, 
     * and the updated information and saves it as a new experiment. The information is updated also in class TryWithKids.
     * @param updateExp
     * @param updating 
     */
    public void update(Experiment updateExp, Experiment updating) {
        datastore.delete(updateExp);
        datastore.save(updating);    
    }

    /**
     * method takes two search criteria: subject (String) and duration(int) and
     * searches the database for experiments matching these criteria. With subject,
     * it searches for matches and with int, it searches for all those, whose int is smaller or 
     * equal to the int in parameter. returns a list of the results
     * @param subject
     * @param duration
     * @return List<Experiment>
     */
    public List<Experiment> findByBoth(String subject, int duration) {
        List<Experiment> experiments = datastore.createQuery(Experiment.class).field("subject").contains(subject).field("duration").lessThanOrEq(duration).asList();
        return experiments;
    }

    /**
     * Method searches the database for experiments with the subject in parameter. returns results as list
     * @param subject
     * @return List<Experiment>
     */
    public List<Experiment> findBySubject(String subject) {
        List<Experiment> experiments = datastore.createQuery(Experiment.class).field("subject").contains(subject).asList();
        return experiments;
    }

    /**
     * Method searches the database for experiments, where duration is less or equal
     * to the parameter given as int. Returns list of results.
     * @param duration
     * @return List<Experiment>
     */
    public List<Experiment> findByDuration(int duration) {
        List<Experiment> experiments = datastore.createQuery(Experiment.class).field("duration").lessThanOrEq(duration).asList();
        return experiments;
    }
}


