
package trywithkids.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the logic of the class User. Its variables are:
 * String username, String password, Boolean upkeep (true = upkeep with full privileges, 
 * false = user without privileges to update, add or delete) and List<Experiments> where
 * the Id of individual experiments are added once the user can create personal lists.
 * @author satu
 */
public class User {
    private String username;
    private String password;
    private Boolean upkeep;
    private List<Experiment> experiments;
    
    public User(String username, String password, Boolean upkeep) {
        this.username = username;
        this.password = password;
        this.upkeep = upkeep;
        this.experiments = new ArrayList<>();
    }
}

