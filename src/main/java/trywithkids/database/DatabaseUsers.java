
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
    
    public void clearDatabaseOfUsers() {
        Query<User> queryDelete = datastore.createQuery(User.class);
        datastore.delete(queryDelete);
    }
    public List<User> findAll() {
        Query<User> query = this.datastore.createQuery(User.class);
        List<User> users = query.asList();
        return users;
    }

    public void updateUserList(User user) {
        System.out.println("updateuserslist not supported yet");
    }

    public User findUser(User userfromGUI) {
        System.out.println("finduser not supported yet");
        User user = new User("test", "testexception", false);
        return user;
    }

    public Boolean findUsername(String username) {
        System.out.println("Findusername: Not supported yet.");
        return true;
    }

}
