
package trywithkids.gui;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trywithkids.domain.Experiment;
import trywithkids.domain.TryWithKids;


public class GUIview {
    
    private TryWithKids tryWithKids;
    
    public GUIview(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    public Parent getNakyma() {
        List<Experiment> experiments = this.tryWithKids.findAll();
        
        ScrollPane sp = new ScrollPane();
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        
        ListView<Experiment> listView = new ListView();
        ObservableList<Experiment> observableExperiment = FXCollections.observableList(experiments);
        listView.setItems(observableExperiment);
        
        sp.setContent(new Label("EXPERIMENTS IN DATABASE"));
        sp.setContent(listView);
        
        return sp;
    }
}
