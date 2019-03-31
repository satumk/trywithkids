
package trywithkids.gui;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import trywithkids.domain.Experiment;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.User;

public class GUIupdate {
    private TryWithKids tryWithKids;
    
    public GUIupdate(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    public Parent getView() {
        List<Experiment> experiments = this.tryWithKids.findAll();
        
        // create a borderpane as main element
        BorderPane master = new BorderPane();
        VBox labels = new VBox();
        labels.getChildren().add(new Label("EXPERIMENTS IN DATABASE"));
        labels.getChildren().add(new Label("Click on the experiment you want to update"));
        labels.setPadding(new Insets(20, 20, 20, 20));
        
        // create a scrollpane to show experiments
        ScrollPane sp = new ScrollPane();
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        
        // create a listView to show individual experiments
        ListView<Experiment> listView = new ListView();
        ObservableList<Experiment> observableExperiment = FXCollections.observableList(experiments);
        listView.setItems(observableExperiment);
        
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Experiment>() {
            int index = -1;
                
            @Override
            public void changed(ObservableValue<? extends Experiment> observable, Experiment oldValue, Experiment newValue) {
                index = listView.getSelectionModel().getSelectedIndex();
                
                Experiment updateExp = experiments.get(index);
                String topic = updateExp.getTopic();
                
                // creating an update window + inserting the info from the experiment
                ScrollPane updateSP = new ScrollPane();
                sp.setFitToHeight(true);
                sp.setFitToWidth(true);
                
                Label updating = new Label("Please review and make any necessary changes below");
                Button update = new Button("Update info");
                Button no = new Button("Cancel");
                TextArea topicTextarea = new TextArea("");
                topicTextarea.setText(updateExp.getTopic());
                TextArea waitTime = new TextArea("");
                waitTime.setText(updateExp.getWaitTime());
                TextArea materials = new TextArea("");
                materials.setText(updateExp.getMaterials());
                TextArea directions = new TextArea("");
                directions.setText(updateExp.getDirections());
                TextArea notes = new TextArea("");
                notes.setText(updateExp.getNotes());
                TextArea theScience = new TextArea("");
                theScience.setText(updateExp.getTheScience());
                
                // creating togglegroup for subject
                ToggleButton biology = new ToggleButton("Biology");
                ToggleButton physics = new ToggleButton("Physics");
                ToggleButton chemistry = new ToggleButton("Chemistry");
                ToggleGroup toggleGroup1 = new ToggleGroup();
                physics.setToggleGroup(toggleGroup1);
                chemistry.setToggleGroup(toggleGroup1);
                biology.setToggleGroup(toggleGroup1);
                HBox subject = new HBox(physics, chemistry, biology);
                subject.setSpacing(10);
                // preselect subject based on the experiment info
                if (updateExp.getSubject().contains("Phy")) {
                    physics.selectedProperty().setValue(Boolean.TRUE);
                } else if (updateExp.getSubject().contains("Che")) {
                    chemistry.selectedProperty().setValue(Boolean.TRUE);
                } else {
                    biology.selectedProperty().setValue(Boolean.TRUE);
                }
                toggleGroup1.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
                    if (newVal == null) {
                        oldVal.setSelected(true);
                    }
                });
                
                // create a togglegroup for duration
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
                // preselect duration based on experiment info
                if (updateExp.getDuration().equals(5)) {
                    five.selectedProperty().setValue(Boolean.TRUE);
                } else if (updateExp.getDuration().equals(10)) {
                    ten.selectedProperty().setValue(Boolean.TRUE);
                } else if (updateExp.getDuration().equals(15)) {
                    fiveteen.selectedProperty().setValue(Boolean.TRUE);
                } else if (updateExp.getDuration().equals(20)) {
                    twenty.selectedProperty().setValue(Boolean.TRUE);
                } else if (updateExp.getDuration().equals(40)) {
                    thirty.selectedProperty().setValue(Boolean.TRUE);
                } else if (updateExp.getDuration().equals(40)) {
                    fourty.selectedProperty().setValue(Boolean.TRUE);
                } else if (updateExp.getDuration().equals(50)) {
                    fifty.selectedProperty().setValue(Boolean.TRUE);
                } else if (updateExp.getDuration().equals(60)) {
                    sixty.selectedProperty().setValue(Boolean.TRUE);
                }
                toggleGroup2.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
                    if (newVal == null) {
                        oldVal.setSelected(true);
                    }
                });
                
                VBox newStage = new VBox();
                newStage.setSpacing(10);
                newStage.setPadding(new Insets(20, 20, 20, 20));
                newStage.getChildren().add(updating);
                newStage.getChildren().add(new Label("Subject: "));
                newStage.getChildren().add(subject);
                newStage.getChildren().add(new Label("Topic:"));
                newStage.getChildren().add(topicTextarea);
                newStage.getChildren().add(new Label("Materials:"));
                newStage.getChildren().add(materials);
                newStage.getChildren().add(new Label("Duration: "));
                newStage.getChildren().add(duration);
                newStage.getChildren().add(new Label("Waiting period:"));
                newStage.getChildren().add(waitTime);  
                newStage.getChildren().add(new Label("Directions:"));
                newStage.getChildren().add(directions);
                newStage.getChildren().add(new Label("Notes:"));
                newStage.getChildren().add(notes);
                newStage.getChildren().add(new Label("The Science:"));
                newStage.getChildren().add(theScience);
                newStage.getChildren().add(no);
                newStage.getChildren().add(update);
                
                updateSP.setContent(newStage);
                
                Scene secondScene = new Scene(updateSP, 700, 400);
                Stage newWindow = new Stage();
                newWindow.setTitle("Updating experiment " + topic);
                newWindow.setScene(secondScene);
                newWindow.setX(master.getTranslateX() + 300);
                newWindow.setY(master.getTranslateY() + 150);
                
                update.setOnAction((event) -> { 
                    // getting the toggled subject 
                    ToggleButton selected = (ToggleButton) toggleGroup1.getSelectedToggle();
                    String subjectToggle = "empty";
                    if (selected.equals(biology)) {
                        subjectToggle = "Biology";
                    } else if (selected.equals(physics)) {
                        subjectToggle = "Physics";
                    } else if (selected.equals(chemistry)) {
                        subjectToggle = "Chemistry";
                    } 

                    // getting the toggled value of duration 
                    ToggleButton selected2 = (ToggleButton) toggleGroup2.getSelectedToggle();
                    int durationToggle = 0;
                    if (selected2.equals(five)) {
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
                    
                    //update database
                    update(updateExp, subjectToggle, topicTextarea.getText(), durationToggle, waitTime.getText(), materials.getText(), directions.getText(), notes.getText(), theScience.getText());
                    
                    //update the list in UI
                    experiments.get(index).setSubject(subjectToggle);
                    experiments.get(index).setTopic(topicTextarea.getText());
                    experiments.get(index).setDuration(durationToggle);
                    experiments.get(index).setWaitTime(waitTime.getText());
                    experiments.get(index).setMaterials(materials.getText());
                    experiments.get(index).setDirections(directions.getText());
                    experiments.get(index).setNotes(notes.getText());
                    experiments.get(index).setTheScience(theScience.getText());
                    
                    //showing changes in UI
                    listView.refresh();
                    
                    //closing the popup
                    newWindow.hide();
                });

                no.setOnAction((event) -> {
                    newWindow.hide();
                });
                
                newWindow.show();

            }  

        });
        
        sp.setContent(listView);
        
        master.setTop(labels);
        master.setCenter(sp);
        
        return master;
    }

    public void update(Experiment updateExp, String subject, String topic, int duration, String waittime, String materials, String directions, String notes, String thescience) {
        this.tryWithKids.update(updateExp, subject, topic, duration, waittime, materials, directions, notes, thescience);
    }
}
