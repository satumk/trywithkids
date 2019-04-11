
package trywithkids.domain;

import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Field;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Index;
import xyz.morphia.annotations.Indexes;


/**
 * This class holds the instances of Experiments. The variables are ObjectID, String subject (physics, chemistry or biology),
 * String topic, Integer duration (5-60 minutes), String waitTime (if there is a waiting period between
 * the experiment and the results), String materials, String directions, String notes, String the Science
 * The methods are mostly comprised of the basic getters and setters.
 * @author satu
 */
@Entity("experiments")
@Indexes({
    @Index(value = "topic", fields = @Field("topic")),
    @Index(value = "subject", fields = @Field("subject")),
    @Index(value = "duration", fields = @Field("duration")),
    @Index(value = "materials", fields = @Field("materials"))
})

public class Experiment {
    @Id
    private ObjectId id;
    private String subject;
    private String topic;
    private Integer duration;
    private String waitTime;
    private String materials;
    private String directions;
    private String notes;
    private String theScience;
    
    /**
     * Constructor for class experiment
     * @param subject String
     * @param topic String
     * @param duration Integer
     * @param waitTime String
     * @param materials String
     * @param directions String
     * @param notes String
     * @param theScience String
     */
    public Experiment(String subject, String topic, Integer duration, String waitTime, String materials, String directions, String notes, String theScience) {
        this.subject = subject;
        this.topic = topic;
        this.duration = duration;
        this.waitTime = waitTime;
        this.materials = materials;
        this.directions = directions;
        this.notes = notes;
        this.theScience = theScience;
    }

    /**
     * constructor without any params for class Experiment
     */
    public Experiment() {

    }
    
    /**
     * get id for class instance experiment
     * @return the objectId of this experiment
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * get String subject of experiment
     * @return subject (String) of experiment
     */
    public String getSubject() {
        return subject;
    }

    /**
     * get String topic of experiment
     * @return topic (String) of experiment
     */
    public String getTopic() {
        return topic;
    }

    /**
     * get Integer duration of experiment
     * @return duration (integer) of experiment
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * get String materials of experiment
     * @return materials (String) of experiment
     */
    public String getMaterials() {
        return materials;
    }

    /**
     * get String directions of experiment
     * @return directions (String) of experiment
     */
    public String getDirections() {
        return directions;
    }

    /**
     * get String science of the experiment
     * @return the science explained (String) of experiment
     */
    public String getTheScience() {
        return theScience;
    }

    /**
     * set id. Param objectId
     * @param id objectId
     */
    public void setId(ObjectId id) {
        this.id = id;
    }
    
    /**
     * Set String subject
     * @param subject String
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Set String topic
     * @param topic String
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Set Integer duration
     * @param duration Integer
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Set String materials
     * @param materials String
     */
    public void setMaterials(String materials) {
        this.materials = materials;
    }

    /**
     * Set String directions
     * @param directions String
     */
    public void setDirections(String directions) {
        this.directions = directions;
    }

    /**
     * Set String science
     * @param theScience String
     */
    public void setTheScience(String theScience) {
        this.theScience = theScience;
    }
    
    /**
     * set String waitTime
     * @param waitTime String
     */
    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * get String waitTime
     * @return String waitTime
     */
    public String getWaitTime() {
        return waitTime;
    }
    
    /**
     * set String notes
     * @param notes String
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * get String notes
     * @return String notes
     */
    public String getNotes() {
        return this.notes;
    }
    
    /**
     * This method returns a shorter version of the information about an experiment including topic, duration, waittime and materials
     * @return String
     */
    public String shortInfo() {
        String info = "Experiment: " + this.topic + "\nDuration of experiment: "
                + this.duration + " minutes\nWaiting time: " + this.waitTime + "\nMaterials: " + this.materials;

        return info;
    }
    
    /**
     * This method contains all information about an experiment excluding the id and returns a String containing this info
     * @return String
     */
    @Override
    public String toString() {
        String info = "\nSubject: " + this.subject + "\nExperiment: " + this.topic + "\nDuration of experiment: " 
                + this.duration + " minutes\nWaiting time: " + this.waitTime + "\nMaterials: " + this.materials + "\nDirections: \n"
                + this.directions + "\nNotes on this experiment: " + this.notes + "\nThe Science: " + this.theScience + "\n";
        return info;
    }
    
    
}
