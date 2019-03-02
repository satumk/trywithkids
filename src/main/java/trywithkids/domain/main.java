
package trywithkids.domain;

import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import trywithkids.gui.GUI;
import static javafx.application.Application.launch;
import trywithkids.database.Database;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class main {
    
    public static void main(String[] args) {
        Morphia morphia = new Morphia();
        morphia.mapPackage("trywithkids.domain");
        morphia.mapPackage("trywithkids.gui");
        Datastore datastore = morphia.createDatastore(new MongoClient(), "experiments");
        datastore.ensureIndexes();
        Database database = new Database(morphia, datastore);
        
        TryWithKids trywithkids = new TryWithKids(database);
        
        trywithkids.clearDatabase();
        
        trywithkids.addStarterExperiments();
        
        List<Experiment> experiments = trywithkids.getExperiments();
        
        for (Experiment exp : experiments) {
            trywithkids.saveToDatabase(exp);
        }
        
        List<Experiment> fromDatabase = database.findAll();
        
        for (Experiment exp : fromDatabase) {
            System.out.println(exp.shortInfo());
        }
        
        GUI kayttis = new GUI(trywithkids);
        //launch(GUI.class);
        
        
        
        
        
    }
    
}
