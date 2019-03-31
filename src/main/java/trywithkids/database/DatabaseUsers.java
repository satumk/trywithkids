
package trywithkids.database;

import java.util.ArrayList;
import java.util.List;
import trywithkids.domain.User;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;
import xyz.morphia.query.Query;

/**
 * This database-class interacts with trywithkids and the datastore and mongodb instigated
 * at launch. This database focuses on dealing with user-objects and in a limited fashion 
 * with their experiments.
 * @author satu
 */
public class DatabaseUsers {
    private Morphia morphia;
    private Datastore datastore;
    
    /**
     * The constructor takes as parameter Morphia and the datastore created when the application is launched.
     * @param morphia
     * @param datastore 
     */
    public DatabaseUsers(Morphia morphia, Datastore datastore) {
        this.morphia = morphia;
        this.datastore = datastore;
    }    

    public void save(User newUser) {
        this.datastore.save(newUser);
    }

    public void delete(User user) { 
        this.datastore.delete(user);
    }
    
    /**
     * method clears database of users
     */
    public void clearDatabaseOfUsers() {
        Query<User> queryDelete = datastore.createQuery(User.class);
        datastore.delete(queryDelete);
    }
    
    /**
     * method lists all users in database
     * @return 
     */
    public List<User> findAll() {
        Query<User> query = this.datastore.createQuery(User.class);
        List<User> users = query.asList();
        return users;
    }

    /**
     * This method returns a user from database. It requires an instance of User (from GUI) as param and returns the matching instance
     * from database.
     * @param userfromGUI
     * @return 
     */
    public User findUser(User userfromGUI) {      
        return this.datastore.get(User.class, userfromGUI.getId());
    }

    /**
     * method to check if the database contains a user with a specific username. Param username accepts String.
     * @param username
     * @return 
     */
    public Boolean findUsername(String username) {
        Query<User> query = this.datastore.createQuery(User.class).field("username").equal(username);
        List<User> users = query.asList();
        Boolean exists = false;
        if (users.size() >= 1) {
            exists = true;
        }
        return exists;
    }
    
    /**
     * updates userinfo. Accepts the old User-class instance and new User-class instance as params
     * @param userfromGUI
     * @param userinDatabase 
     */
    public void updateUser(User userfromGUI, User userinDatabase) {
        datastore.delete(userfromGUI);
        datastore.save(userinDatabase);
    }

    /**
     * This method supports credential-evaluation. It takes as param String username and returns a list of 
     * all users with that username (should be a list of 1).
     * @param usernameGUI
     * @return 
     */
    public List<User> checkLoginInfo(String usernameGUI) {
        Query<User> query = this.datastore.createQuery(User.class).field("username").equal(usernameGUI);
        List<User> users = query.asList();
        return users;
    }

}
