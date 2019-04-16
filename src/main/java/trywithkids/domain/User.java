
package trywithkids.domain;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Field;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Index;
import xyz.morphia.annotations.Indexes;
import xyz.morphia.annotations.Reference;

/**
 * This class contains the logic of the class User. Its variables are:
 * String username, String password, Boolean maintenance (true = maintenance with full privileges, 
 * false = user without privileges to update, add or delete experiments or users) 
 * and list of experiments where
 * individual experiments are added now that users can create personal lists.
 * @author satu
 */
@Entity("users")
@Indexes({
    @Index(value = "username", fields = @Field("username"))
})

public class User {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private Boolean maintenance;
    @Reference
    private List<Experiment> experiments;
    
    /**
     * Constructor for class user. parameters String username, String password, 
     * Boolean maintenance. Also
     * creates an empty arrayList.
     * @param username String selected to represent the user. 
     * @param password String password to gain entry to user info
     * @param maintenance boolean info whether the user is an administrator (maintenance) or not
     */
    public User(String username, String password, Boolean maintenance) {
        this.username = username;
        this.password = password;
        this.maintenance = maintenance;
        this.experiments = new ArrayList<>();
    }

    /**
     * Returns ObjectId
     * @return objectId tht identifies the user
     */
    public ObjectId getId() {
        return id;
    }
    
    /**
     * No params constructor for class User
     */
    public User() {
        
    }

    /**
     * Returns String username
     * @return String username 
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns string password
     * @return string password
     */
    public String getPassword() {
        return password;
    }

    /**
     * returns boolean admin-status
     * @return boolean value whether the user has maintenance-privileges
     */
    public Boolean getMaintenance() {
        return maintenance;
    }

    /**
     * returns list of experiments the user has added to their list
     * @return list of experiments connected with a specific user
     */
    public List<Experiment> getExperiments() {
        return experiments;
    }

    /**
     * sets String username
     * @param username Sets a specific String as a username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * sets string password
     * @param password Sets a specific String as a password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * sets Boolean maintenance status
     * @param maintenance sets the maintenance-status of a specific user
     */
    public void setMaintenance(Boolean maintenance) {
        this.maintenance = maintenance;
    }

    /**
     * adds experiment-parameter to own list
     * @param experiment accepts instance of experiment-class to add to a specific user
     */
    public void addExperiment(Experiment experiment) {
        this.experiments.add(experiment);
    }
    
    /**
     * 
     * @return String representation of the username, password, and size of 
     * their personal list of experiments
     */
    @Override
    public String toString() {
        String role = "";
        if (this.maintenance == true) {
            role = "maintenance";
        } else {
            role = "user";
        }
        
        String info = "Username: " + this.username + "\nPassword: " 
                + this.password + "\nRole: " + role 
                + "\nExperiments in list: " + this.experiments.size();
        
        return info;
    }
    
}

