
package trywithkids.database;

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
     * @param morphia this is the morphia created when application is launched
     * @param datastore this is the datastore created when application is launched
     */
    public DatabaseUsers(Morphia morphia, Datastore datastore) {
        this.morphia = morphia;
        this.datastore = datastore;
    }    

    /**
     * saves the user in parameters to database
     * @param newUser Basically this is just a user-class instance passed on to the datastore
     */
    public void save(User newUser) {
        this.datastore.save(newUser);
    }

    /**
     * deletes the user in parameter from database
     * @param user Basically this is just a user-class instance passed on to the datastore
     */
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
     * @return a list of all users in the database
     */
    public List<User> findAll() {
        Query<User> query = this.datastore.createQuery(User.class);
        List<User> users = query.asList();
        return users;
    }

    /**
     * This method returns a user from database. It requires an instance of User (from GUI) as param and returns the matching instance
     * from database.
     * @param userfromGUI Basically this is just a user-class instance passed on to the datastore from the GUI
     * @return the corresponding user from the database
     */
    public User findUser(User userfromGUI) {      
        return this.datastore.get(User.class, userfromGUI.getId());
    }

    /**
     * method to check if the database contains a user with a specific username. Param username accepts String.
     * @param username username is String that identifies the user.
     * @return returns a boolean value if there is someone with that username in the database
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
     * @param userfromGUI User passed on from GUI, that is removed from database (old info)
     * @param userinDatabase user passed on from trywithkids with updated info saved to database
     */
    public void updateUser(User userfromGUI, User userinDatabase) {
        datastore.delete(userfromGUI);
        datastore.save(userinDatabase);
    }

    /**
     * This method supports credential-evaluation. It takes as param String username and returns a list of 
     * all users with that username (should be a list of 1).
     * @param usernameGUI String username, which it then checks if there are users with that username in database
     * @return a list of users with that username in the database. Should be a list of 1 
     */
    public List<User> checkLoginInfo(String usernameGUI) {
        Query<User> query = this.datastore.createQuery(User.class).field("username").equal(usernameGUI);
        List<User> users = query.asList();
        return users;
    }

}
