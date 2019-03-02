
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
    
    public Database() {
        this.morphia = new Morphia();
        morphia.mapPackage("trywithkids.domain");
        morphia.mapPackage("trywithkids.gui");
        this.datastore = morphia.createDatastore(new MongoClient(), "experiments");
        datastore.ensureIndexes();
    }
    
    public void save(Experiment experiment) {
        datastore.save(experiment);
    }

    public void findAll() {
        Query<Experiment> query = datastore.createQuery(Experiment.class);
        List<Experiment> experiments = query.asList();
    }
    
    
}


