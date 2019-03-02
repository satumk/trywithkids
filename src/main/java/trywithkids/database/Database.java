
package trywithkids.database;

import com.mongodb.MongoClient;
import java.util.List;
import xyz.morphia.Morphia;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.Experiment;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

public class Database {
    private Morphia morphia;
    private Datastore datastore;
    
    public Database(Morphia morphia, Datastore datastore) {
        this.morphia = morphia;
        this.datastore = datastore;
    }
    
    public void save(Experiment experiment) {
        datastore.save(experiment);
    }

    public List<Experiment> findAll() {
        Query<Experiment> query = datastore.createQuery(Experiment.class);
        List<Experiment> experiments = query.asList();
         
        return experiments;
    }
    
    
}


