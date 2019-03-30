
package trywithkids.gui;

import com.mongodb.MongoClient;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import trywithkids.database.Database;
import trywithkids.database.DatabaseUsers;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.User;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class GUI extends Application {
    
    private TryWithKids trywithkids;
    
    @Override
    public void init() throws Exception {
        Morphia morphia = new Morphia();
        morphia.mapPackage("trywithkids.domain");
        morphia.mapPackage("trywithkids.gui");
        Datastore datastore = morphia.createDatastore(new MongoClient(), "experiments");
        datastore.ensureIndexes();
        Database database = new Database(morphia, datastore);
        DatabaseUsers userDatabase = new DatabaseUsers(morphia, datastore);
        this.trywithkids = new TryWithKids(database, userDatabase);
        
        //userDatabase.clearDatabaseOfUsers();
        if (trywithkids.getUserN() == 0) {
            this.trywithkids.addDefaultMaintenance();
        }
        
        if (trywithkids.databaseSize() == 0) {
            trywithkids.addStarterExperiments(); 
        }
    }
    
    @Override
    public void start(Stage window) /*throws Exception */{
        window.setTitle("Try with kids");
        
        //create subscenes
        GUIadd add = new GUIadd(trywithkids);
        GUIsearch search = new GUIsearch(trywithkids);
        GUIupdate update = new GUIupdate(trywithkids);
        GUIdelete delete = new GUIdelete(trywithkids);
        GUIview view = new GUIview(trywithkids);
        GUIusers users = new GUIusers(trywithkids);
        
        //create the menu and its buttons
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);
        
        Button addButton = new Button("Add new");
        Button updateButton = new Button("Update");
        Button searchButton = new Button("Search");
        Button deleteButton = new Button("Delete");
        Button viewButton = new Button("Browse all");
        Button usersButton = new Button("Users");
        
        menu.getChildren().addAll(usersButton, viewButton, addButton, updateButton, 
                searchButton, deleteButton);
        
        //create main scene
        BorderPane setting = new BorderPane();
        setting.setTop(menu);
        
        usersButton.setOnAction((event) -> setting.setCenter(users.getView()));
        viewButton.setOnAction((event) -> setting.setCenter(view.getView()));
        addButton.setOnAction((event)-> setting.setCenter(add.getView()));
        updateButton.setOnAction((event) -> setting.setCenter(update.getView()));
        searchButton.setOnAction((event)-> setting.setCenter(search.getView()));
        deleteButton.setOnAction((event) -> setting.setCenter(delete.getView()));
        
        setting.setCenter(view.getView());
        
        Scene selectAction = new Scene(setting, 1000, 600);
               
        window.setScene(selectAction);

        window.show();
    }
    
    public static void main(String[] args) {
        launch(GUI.class);  
    }

}
