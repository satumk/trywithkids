
package trywithkids.domain;

import java.util.List;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Field;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Index;
import xyz.morphia.annotations.Indexes;
import static xyz.morphia.utils.IndexType.ASC;

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
    private List<String> materials;
    private String directions;
    private String notes;
    private String theScience;
    
    public Experiment(String subject, String topic, Integer duration, String waitTime, List<String> materials, String directions, String notes, String theScience) {
        this.subject = subject;
        this.topic = topic;
        this.duration = duration;
        this.waitTime = waitTime;
        this.materials = materials;
        this.directions = directions;
        this.notes = notes;
        this.theScience = theScience;
    }

    public Experiment() {

    }
    
    public ObjectId getId() {
        return id;
    }
    public String getSubject() {
        return subject;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getDuration() {
        return duration;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public String getDirections() {
        return directions;
    }

    public String getTheScience() {
        return theScience;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public void setTheScience(String theScience) {
        this.theScience = theScience;
    }
    
    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    public String getWaitTime() {
        return waitTime;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getNotes() {
        return this.notes;
    }
    
    public String shortInfo() {
        String info = "Experimet: " + this.id + " - " + this.topic + "\nDuration of experiment: "
                + this.duration + "minutes\nWaiting time: " + this.waitTime + "\nMaterials: ";
        
        for (String s : this.materials) {
            info += "    " + s.toString() + "\n";
        }
        
        return info;
    }
    
    @Override
    public String toString() {
        String info = "Subject: " + this.subject + "\nExperimet: " + this.id + " - " + this.topic + "\nDuration of experiment: " 
                + this.duration + "minutes\nWaiting time: " + this.waitTime + "\nMaterials: ";
        
        for (String s : this.materials) {
            info += "    " + s.toString() + "\n";
        }
        
        info += "Directions: " + this.directions + "\nNotes on this experiment: + " + this.notes + "\nThe Science: " + this.theScience;
        
        return info;
    }
    
    
}
