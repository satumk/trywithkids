
package trywithkids.gui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.User;


public class GUIadd {
    
    private TryWithKids tryWithKids;
    
    public GUIadd(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    public Parent getView() {
        ScrollPane sp = new ScrollPane();
        
        //TextAreas and prompt texts for GUIadd
        VBox setting = new VBox();
        setting.setLayoutX(5);
        setting.setSpacing(10);
        setting.setPadding(new Insets(20, 20, 20, 20));
        TextArea topic = new TextArea("");
        topic.setPromptText("Compulsory: what do you want to name the experiment");
        topic.setWrapText(true);
        TextArea waitTime = new TextArea("");
        waitTime.setPromptText("Not compulsory: Is there a waiting period between the experiment and the results?");
        waitTime.setWrapText(true);
        TextArea materials = new TextArea("");
        materials.setWrapText(true);
        materials.setPromptText("Compulsory: what materials are needed? Separate with // for easier viewing");
        TextArea directions = new TextArea("");
        directions.setPromptText("Compulsory: How is the experiment conducted?");
        directions.setWrapText(true);
        TextArea notes = new TextArea("");
        notes.setPromptText("Not compulsory: Is there something more that can help the experimentor?");
        notes.setWrapText(true);
        TextArea theScience = new TextArea("");
        theScience.setPromptText("Not compulsory, but recommended: Why does the experiment work / what is the science behind it?");
        theScience.setWrapText(true);
        
        //feedback buttons and labels for successful entry
        Button save = new Button("Save to database");
        Label saved = new Label("Saved to database. To see your experiments, visit 'Browse all'");
                
        // Create a togglegroup of buttons for subject
        ToggleButton biology = new ToggleButton("Biology");
        ToggleButton physics = new ToggleButton("Physics");
        ToggleButton chemistry = new ToggleButton("Chemistry");
        ToggleGroup toggleGroup1 = new ToggleGroup();
        physics.setToggleGroup(toggleGroup1);
        chemistry.setToggleGroup(toggleGroup1);
        biology.setToggleGroup(toggleGroup1);
        Label compulsory1 = new Label("Compulsory: please select one");
        HBox subject = new HBox(physics, chemistry, biology, compulsory1);
        subject.setSpacing(10);
        
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
        Label compulsory2 = new Label("Compulsory: please select one");
        HBox duration = new HBox(five, ten, fiveteen, twenty, thirty, fourty, fifty, sixty, compulsory2);
        duration.setSpacing(10);
        
        // Labels to GUIadd
        setting.getChildren().add(new Label("TO ADD NEW EXPERIMENTS:"));
        setting.getChildren().add(new Label("Please fill out the information below"));
        setting.getChildren().add(new Label("---------------------------"));
        setting.getChildren().add(new Label("Select a subject"));
        setting.getChildren().add(subject);
        setting.getChildren().add(new Label("Topic"));
        setting.getChildren().add(topic);
        setting.getChildren().add(new Label("Materials"));
        setting.getChildren().add(materials);
        setting.getChildren().add(new Label("Select a duration"));
        setting.getChildren().add(duration);
        setting.getChildren().add(new Label("Waiting period"));
        setting.getChildren().add(waitTime);
        setting.getChildren().add(new Label("Directions to conduct the experiment"));
        setting.getChildren().add(directions);
        setting.getChildren().add(new Label("Explain the science behind the experiment"));
        setting.getChildren().add(theScience);
        setting.getChildren().add(new Label("Additional notes"));
        setting.getChildren().add(notes);
        setting.getChildren().add(new Label("When you press 'save', if everything is ok, you will see a 'saved to database' below save-button. If not, please make sure you have filled out everything"));
        setting.getChildren().add(save);
 
        sp.setContent(setting);
        
        //check content and add to database
        save.setOnAction((event) -> {
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
            } else {
                durationToggle = 100;
            }
            
            // making sure all compulsory fields have values before adding them to database (in else)
            if (topic.getText().isEmpty() || durationToggle == 0 || subjectToggle.contains("empty") || materials.getText().isEmpty() || directions.getText().isEmpty()) {
                setting.getChildren().add(new Label("Please fill out all compulsory fields"));
            } else {
                this.tryWithKids.createExperimentAndSave(subjectToggle, topic.getText(), durationToggle, waitTime.getText(), materials.getText(), directions.getText(), notes.getText(), theScience.getText());
                setting.getChildren().add(saved);
            }

        });

        return sp;
    }
}
