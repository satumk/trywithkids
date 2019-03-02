
package trywithkids.domain;

import com.mongodb.MongoClient;
import trywithkids.gui.GUI;
import static javafx.application.Application.launch;
import trywithkids.database.Database;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class main {
    
    public static void main(String[] args) {
        TryWithKids trywithkids = new TryWithKids();
        trywithkids.addStarterExperiments();
        
        /*
        Morphia morphia = new Morphia();
        morphia.mapPackage("trywithkids.domain");
        morphia.mapPackage("trywithkids.gui");
        Datastore datastore = morphia.createDatastore(new MongoClient(), "experiments");
        datastore.ensureIndexes();
        */
        
        Database database = new Database();
        
        GUI kayttis = new GUI();
        launch(GUI.class);
    }
    
}
