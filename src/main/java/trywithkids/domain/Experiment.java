
package trywithkids.domain;

import java.util.List;

class Experiment {
    private Integer id;
    private String subject;
    private String topic;
    private Integer duration;
    private List<String> materials;
    private String directions;
    private String theScience;
    
    public Experiment(String subject, String topic, Integer duration, List<String> materials, String directions, String theScience) {
        this.subject = subject;
        this.topic = topic;
        this.duration = duration;
        this.materials = materials;
        this.directions = directions;
        this.theScience = theScience;
    }
    
    public Integer getId() {
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

    public void setId(Integer id) {
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
    
    @Override
    public String toString() {
        String info = this.subject + "\n" + this.id + " - " + this.topic + "\n" 
                + this.duration + "\nMaterials:\n";
        
        for (String s : this.materials) {
            info += "    " + s.toString() + "\n";
        }
        
        return info;
    }
    
    
}
