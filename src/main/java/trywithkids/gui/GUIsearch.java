
package trywithkids.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trywithkids.domain.Experiment;
import trywithkids.domain.TryWithKids;


public class GUIsearch {
    private TryWithKids tryWithKids;
    
    public GUIsearch(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    public Parent getView() {
        BorderPane setting = new BorderPane();
        
        VBox head = new VBox();
        head.setSpacing(10);
        head.setPadding(new Insets(10, 10, 10, 10));

        Label headline = new Label("SEARCH THE DATABASE");
        Label guide = new Label("You can search by subject, by maximum wanted duration or with both");
        
        // Create a togglegroup of buttons for subject
        Label bySubject = new Label("Select the subject");
        ToggleButton biology = new ToggleButton("Biology");
        ToggleButton physics = new ToggleButton("Physics");
        ToggleButton chemistry = new ToggleButton("Chemistry");
        ToggleGroup toggleGroup1 = new ToggleGroup();
        physics.setToggleGroup(toggleGroup1);
        chemistry.setToggleGroup(toggleGroup1);
        biology.setToggleGroup(toggleGroup1);
        HBox subject = new HBox(physics, chemistry, biology);
        subject.setSpacing(10);
        
        // create a togglegroup of buttons for duration
        Label byDuration = new Label("Select the maximum duration you want");
        ToggleButton five = new ToggleButton("5min");
        ToggleButton ten = new ToggleButton("10min");
        ToggleButton fiveteen = new ToggleButton("15min");
        ToggleButton twenty = new ToggleButton("20min");
        ToggleButton thirty = new ToggleButton("30min");
        ToggleButton fourty = new ToggleButton("40min");
        ToggleButton fifty = new ToggleButton("50min");
        ToggleButton sixty = new ToggleButton("60min");
        ToggleGroup toggleGroup2 = new ToggleGroup();
        five.setToggleGroup(toggleGroup2);
        ten.setToggleGroup(toggleGroup2);
        fiveteen.setToggleGroup(toggleGroup2);
        twenty.setToggleGroup(toggleGroup2);
        thirty.setToggleGroup(toggleGroup2);
        fourty.setToggleGroup(toggleGroup2);
        fifty.setToggleGroup(toggleGroup2);
        sixty.setToggleGroup(toggleGroup2);
        HBox duration = new HBox(five, ten, fiveteen, twenty, thirty, fourty, fifty, sixty);
        duration.setSpacing(10);
        
        Button search = new Button("SEARCH");
        head.getChildren().addAll(headline, guide, bySubject, subject, byDuration, duration, search);
        
        setting.setTop(head);
        
        search.setOnAction((event) -> {
            
            // getting the toggled subject 
            ToggleButton selected = (ToggleButton) toggleGroup1.getSelectedToggle();
            String subjectToggle = "empty";
            if (selected == null) {
                subjectToggle = "empty";
            } else if (selected.equals(biology)) {
                subjectToggle = "Biology";
            } else if (selected.equals(physics)) {
                subjectToggle = "Physics";
            } else if (selected.equals(chemistry)) {
                subjectToggle = "Chemistry";
            }
            
            // getting the toggled value of duration 
            ToggleButton selected2 = (ToggleButton) toggleGroup2.getSelectedToggle();
            int durationToggle = 0;
            if (selected2 == null) {
                durationToggle = 0;
            } else if (selected2.equals(five)) {
                durationToggle = 5;
            } else if (selected2.equals(ten)) {
                durationToggle = 10;
            } else if (selected2.equals(fiveteen)) {
                durationToggle = 15;
            } else if (selected2.equals(twenty)) {
                durationToggle = 20;
            } else if (selected2.equals(thirty)) {
                durationToggle = 30;
            } else if (selected2.equals(fourty)) {
                durationToggle = 40;
            } else if (selected2.equals(fifty)) {
                durationToggle = 50;
            } else if (selected2.equals(sixty)) {
                durationToggle = 60;
            } 
            
            List<Experiment> resultsList = new ArrayList<>();
            
            if (!subjectToggle.contains("empty") || durationToggle != 0) {
                resultsList = this.tryWithKids.search(subjectToggle, durationToggle);
            } else {
                System.out.println("durationToggle = " + durationToggle);
                System.out.println(selected2);
                System.out.println("subjectToggle = " + subjectToggle);
                System.out.println(selected);
            }
            
            ScrollPane results = new ScrollPane();
            results.setFitToHeight(true);
            results.setFitToWidth(true);
            results.setMaxHeight(300);
            
            ListView<Experiment> listView = new ListView();
            ObservableList<Experiment> observableExperiment = FXCollections.observableList(resultsList);
            listView.setItems(observableExperiment);

            results.setContent(new Label("Experiments in database matching search criteria"));
            results.setContent(listView);
            setting.setBottom(results);
        });
        
        
        return setting;
    }
}
