
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import trywithkids.domain.Experiment;
import trywithkids.domain.TryWithKids;

public class GUIdelete {
    private TryWithKids tryWithKids;
    
    public GUIdelete(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    public Parent getView() {
        List<Experiment> experiments = this.tryWithKids.findAll();
        
        // create a borderpane as main element
        BorderPane master = new BorderPane();
        VBox labels = new VBox();
        labels.getChildren().add(new Label("EXPERIMENTS IN DATABASE"));
        labels.getChildren().add(new Label("Click on the experiment you want to delete"));
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
                
                Experiment deleteExp = experiments.get(index);
                String topic = deleteExp.getTopic();
                
                // creating a popup window to make sure user wants to delete an experiment
                Button yes = new Button("Yes, I am sure");
                Button no = new Button("Cancel");
                Label label = new Label("Are you sure you want to delete experiment");
                Label topicLabel = new Label(topic);
                Label makingSure = new Label("You cannot undo this action");
                VBox newStage = new VBox();
                newStage.setSpacing(10);
                newStage.setPadding(new Insets(20, 20, 20, 20));
                newStage.getChildren().add(makingSure);
                newStage.getChildren().add(label);
                newStage.getChildren().add(topicLabel);
                newStage.getChildren().add(no);
                newStage.getChildren().add(yes);
                
                Scene secondScene = new Scene(newStage, 450, 250);
                Stage newWindow = new Stage();
                newWindow.setTitle("Confirmation");
                newWindow.setScene(secondScene);
                newWindow.setX(master.getTranslateX() + 250);
                newWindow.setY(master.getTranslateY() + 200);
                
                yes.setOnAction((event) -> { 
                    // deleting from dayabase
                    deleteOne(deleteExp);
                    //deleting from UI
                    experiments.remove(index);
                    //showing changes in UI
                    listView.refresh();
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
    
    // deleting the document from trywithkids
    public void deleteOne(Experiment exp) {
        this.tryWithKids.deleteOne(exp);
    }
}
