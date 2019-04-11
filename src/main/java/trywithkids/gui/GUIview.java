
package trywithkids.gui;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import trywithkids.domain.Experiment;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.User;

/**
 * GUI to browse experiments, add them to own list and view own list
 * @author satu
 */
public class GUIview {
    
    private TryWithKids tryWithKids;
    private User user;
    
    /**
     *
     * @param tryWithKids sets the trywithkids application logic to the view
     */
    public GUIview(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    /**
     * sets user onto this view
     * @param user user-instance currently using the program
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * returns view 
     * @return view of browse for the user
     */
    public Parent getView() {
        List<Experiment> experiments = this.tryWithKids.findAll();
        
        //master level element
        VBox master = new VBox();
        master.setPadding(new Insets(10, 10, 10, 10));
        master.setSpacing(10);
        
        //menu
        HBox menu = new HBox();
        menu.setSpacing(10);
        menu.setPadding(new Insets(10, 10, 10, 10));
        Button viewAllButton = new Button("Browse all");
        Button viewOwnButton = new Button("Browse own list");
        menu.getChildren().addAll(viewAllButton, viewOwnButton);
        
        VBox info = new VBox();
        info.setPadding(new Insets(10, 10, 10, 10));
        Label expInData = new Label("EXPERIMENTS IN DATABASE // In your own list");
        Label actionSelect = new Label("By clicking on an experiment below, you can: ");
        Label action2 = new Label("In BROWSE ALL: add it to your own list, print it or evaluate it.");
        Label action3 = new Label("In BROWSE OWN LIST: delete it from your list");
        info.getChildren().addAll(expInData, actionSelect, action2, action3);

        ScrollPane sp = new ScrollPane();
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        master.getChildren().addAll(menu, info, sp);
        
        ListView<Experiment> listViewAll = new ListView();
        ObservableList<Experiment> observableExperiment = FXCollections.observableList(experiments);
        listViewAll.setItems(observableExperiment);
        sp.setContent(listViewAll);
        
        listViewAll.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Experiment>() {
            int index = -1;

            @Override
            public void changed(ObservableValue<? extends Experiment> observable, Experiment oldValue, Experiment newValue) {
                index = listViewAll.getSelectionModel().getSelectedIndex();

                Experiment expToList = experiments.get(index);
                String topic = expToList.getTopic();

                // creating a popup window to make sure user wants to delete an experiment
                Button addToListButton = new Button("Add to my list");
                Button printButton = new Button("Print experiment");
                Button evalButton = new Button("Give feedback on experiment");
                Button cancelButton = new Button("Cancel");
                Label topicLabel = new Label(topic);
                VBox newStage = new VBox();
                newStage.setSpacing(10);
                newStage.setPadding(new Insets(20, 20, 20, 20));
                newStage.getChildren().addAll(topicLabel, addToListButton, printButton); 
                newStage.getChildren().add(new Label("This will be available in iteration 4"));
                newStage.getChildren().addAll(evalButton, cancelButton);

                Scene secondScene = new Scene(newStage, 450, 250);
                Stage newWindow = new Stage();
                newWindow.setTitle("Add to your list, give feedback or print");
                newWindow.setScene(secondScene);

                addToListButton.setOnAction((event) -> { 
                    addExpToUser(expToList);
                    newWindow.hide();
                });

                printButton.setOnAction((event) -> {
                    try {
                        print(expToList);
                    } catch (IOException ex) {
                        Logger.getLogger(GUIview.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DocumentException ex) {
                        Logger.getLogger(GUIview.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    newWindow.hide();
                });

                evalButton.setOnAction((event) -> {
                    System.out.println("this will become available in the 4th iteration");
                    //newWindow.hide();
                });

                cancelButton.setOnAction((event) -> {
                    newWindow.hide();
                });

                newWindow.show();
            }  

        });
        
        viewAllButton.setOnAction((event) -> {
            sp.setContent(listViewAll);
        });
        
        // creating the view of browsing user's own experiments + adding to view
        viewOwnButton.setOnAction((event) -> {
            List<Experiment> usersList = this.tryWithKids.viewUsersExperimentsList(this.user);

            ListView<Experiment> listViewOwn = new ListView();
            
            ObservableList<Experiment> ownExperiment = FXCollections.observableList(usersList);
            listViewOwn.setItems(ownExperiment);

            listViewOwn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Experiment>() {
                int index = -1;

                @Override
                public void changed(ObservableValue<? extends Experiment> observable, Experiment oldValue, Experiment newValue) {
                    index = listViewOwn.getSelectionModel().getSelectedIndex();

                    Experiment expDelete = usersList.get(index);
                    String label = expDelete.getTopic();

                    // creating a popup window to make sure user wants to delete an experiment
                    Button delete = new Button("Delete from my list");
                    Button cancel = new Button("Cancel");
                    Label topicLabel = new Label(label);
                    VBox newStage = new VBox();
                    newStage.setSpacing(10);
                    newStage.setPadding(new Insets(20, 20, 20, 20));
                    newStage.getChildren().addAll(topicLabel, delete, cancel);

                    Scene secondScene = new Scene(newStage, 450, 250);
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Delete from my list");
                    newWindow.setScene(secondScene);

                    delete.setOnAction((event) -> { 
                        deleteFromList(index);
                        usersList.remove(index);
                        //showing changes in UI
                        listViewOwn.refresh();
                        newWindow.hide();
                    });

                    cancel.setOnAction((event) -> {
                        newWindow.hide();
                    });

                    newWindow.show();
                }  
            });
            
            sp.setContent(listViewOwn);
        });
        
        return master;

    }
    
    /**
     * takes experiment as param and passes it, as well as info about user, onto trywithkids 
     * so experiment can be added to users list.
     * @param exp Experiment-class instance to be added to the current user
     */
    public void addExpToUser(Experiment exp) {
        this.tryWithKids.addExpToUser(user, exp);
        this.user.addExperiment(exp);
    }
    
    /**
     * passes experiment onto trywithkids to be printed to PDF
     * @param exp experiment-class instance to be printed
     * @throws IOException should something go wrong
     * @throws DocumentException should something go wrong
     */
    public void print(Experiment exp) throws IOException, DocumentException {
        this.tryWithKids.print(exp);
    }
    
    /**
     * deletes one experiment from users own list. Param is the index of the experiment on the users list.
     * @param index indec (int) that is used to target the delete-operation to the correct experiment on users list
     */
    public void deleteFromList(int index) {
        this.tryWithKids.deleteFromUserlist(this.user, index);
    }
}
