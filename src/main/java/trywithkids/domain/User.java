
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
 * false = user without privileges to update, add or delete experiments or users) and List<Experiments> where
 * individual experiments are added once the user can create personal lists.
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
    
    public User(String username, String password, Boolean maintenance) {
        this.username = username;
        this.password = password;
        this.maintenance = maintenance;
        this.experiments = new ArrayList<>();
    }
    
    public User() {
        
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getMaintenance() {
        return maintenance;
    }

    public List<Experiment> getExperiments() {
        return experiments;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaintenance(Boolean maintenance) {
        this.maintenance = maintenance;
    }

    public void addExperiment(Experiment experiment) {
        this.experiments.add(experiment);
    }
    
    @Override
    public String toString() {
        String role = "";
        if (this.maintenance == true) {
            role = "maintenance";
        } else {
            role = "user";
        }
        
        String info = "Username: " + this.username + "\nRole: " + role 
                + "\nExperiments in list: " + this.experiments.size();
        
        return info;
    }
    
}

