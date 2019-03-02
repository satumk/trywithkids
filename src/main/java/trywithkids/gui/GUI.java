
package trywithkids.gui;

import trywithkids.gui.GUIadd;
import trywithkids.gui.GUIview;
import trywithkids.gui.GUIupdate;
import trywithkids.gui.GUIsearch;
import trywithkids.gui.GUIlist;
import trywithkids.gui.GUIdelete;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import trywithkids.database.Database;
import trywithkids.domain.TryWithKids;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class GUI extends Application {
    
    private TryWithKids trywithkids;
    
    public GUI(TryWithKids trywithkids) {
        this.trywithkids = trywithkids;
    }
    
    /*
    @Override
    public void init() throws Exception {
        this.trywithkids = new TryWithKids();
    }
*/
    @Override
    public void start(Stage window) /*throws Exception */{
        //this.trywithkids = new TryWithKids();
        window.setTitle("Try with kids");
        
        //create subscenes
        GUIadd add = new GUIadd(trywithkids);
        GUIlist list = new GUIlist(trywithkids);
        GUIsearch search = new GUIsearch(trywithkids);
        GUIupdate update = new GUIupdate(trywithkids);
        GUIdelete delete = new GUIdelete(trywithkids);
        GUIview view = new GUIview(trywithkids);
        
        //create the menu and its buttons
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);
        
        Button addButton = new Button("Add new");
        Button updateButton = new Button("Update");
        Button listButton = new Button("List current");
        Button searchButton = new Button("Search");
        Button deleteButton = new Button("Delete");
        Button viewButton = new Button("View");
        
        menu.getChildren().addAll(addButton, updateButton, listButton, 
                searchButton, deleteButton);
        
        //create main scene
        BorderPane setting = new BorderPane();
        setting.setTop(menu);

        addButton.setOnAction((event)-> setting.setCenter(add.getNakyma()));
        updateButton.setOnAction((event) -> setting.setCenter(update.getNakyma()));
        listButton.setOnAction((event) -> setting.setCenter(list.getNakyma()));
        searchButton.setOnAction((event)-> setting.setCenter(search.getNakyma()));
        deleteButton.setOnAction((event) -> setting.setCenter(delete.getNakyma()));
        viewButton.setOnAction((event) -> setting.setCenter(view.getNakyma()));
        
        setting.setCenter(add.getNakyma());
        
        Scene selectActionUpkeep = new Scene(setting, 700, 400);
               
        window.setScene(selectActionUpkeep);

        window.show();
    }

}
