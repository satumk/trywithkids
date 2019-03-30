
package trywithkids.gui;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trywithkids.domain.Experiment;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.User;

/**
 *
 * @author satu
 */
public class GUIusers {
    private TryWithKids tryWithKids;
    
    public GUIusers(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    public Parent getView() {
        VBox vb = new VBox();
        vb.setPadding(new Insets(20, 20, 20, 20));
        
        ToggleButton addUsers = new ToggleButton("Add users");
        ToggleButton viewUsers = new ToggleButton("Browse (and delete) users");
        ToggleButton changePasswd = new ToggleButton("Change password");
        
        ToggleGroup toggleGroup1 = new ToggleGroup();
        addUsers.setToggleGroup(toggleGroup1);
        viewUsers.setToggleGroup(toggleGroup1);
        changePasswd.setToggleGroup(toggleGroup1);
        HBox actionSelect = new HBox(addUsers, viewUsers, changePasswd);
        actionSelect.setSpacing(10);
        
        ScrollPane sp = new ScrollPane();
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        
        vb.getChildren().addAll(actionSelect, sp);
        
        addUsers.setOnAction((event) -> {
            VBox add = new VBox();
            add.setLayoutX(5);
            add.setSpacing(10);
            add.setPadding(new Insets(20, 20, 20, 20));
            Label instruction = new Label("Please fill all boxes below");
            TextArea username = new TextArea("");
            username.setPromptText("Compulsory: please write your username");
            TextArea password = new TextArea("");
            password.setPromptText("Compulsory: please choose a password. Must be over 8 characters long");
            TextArea rewritePasswd = new TextArea("");
            rewritePasswd.setPromptText("Compulsory: please rewrite password");
            Label role = new Label("Compulsory: Please select a role");
            ToggleButton maintenance = new ToggleButton("Maintenance");
            ToggleButton user = new ToggleButton("User");
            ToggleGroup roles = new ToggleGroup();
            maintenance.setToggleGroup(roles);
            user.setToggleGroup(roles);
            HBox rolesgroup = new HBox();
            rolesgroup.getChildren().addAll(maintenance, user);
            rolesgroup.setSpacing(10);
            
            Button save = new Button("SAVE");
            add.getChildren().addAll(instruction, username, password, rewritePasswd, role, rolesgroup, save);
            
            sp.setContent(add);
        });

        
        viewUsers.setOnAction((event) -> {
            ScrollPane scrollUsers = new ScrollPane();
            scrollUsers.setFitToHeight(true);
            scrollUsers.setFitToWidth(true);
            
            List<User> users = this.tryWithKids.findAllUsers();
            ListView<User> listView = new ListView();
            ObservableList<User> observableExperiment = FXCollections.observableList(users);
            listView.setItems(observableExperiment);
        
            scrollUsers.setContent(new Label("USERS IN DATABASE"));
            scrollUsers.setContent(listView);
            
            sp.setContent(scrollUsers);
        });
        
        changePasswd.setOnAction((event) -> {
            VBox change = new VBox();
            change.setLayoutX(5);
            change.setSpacing(10);
            change.setPadding(new Insets(20, 20, 20, 20));
            
            TextArea oldPasswd = new TextArea("");
            oldPasswd.setPromptText("Compulsory: write old password here");
            TextArea newPasswd = new TextArea("");
            newPasswd.setPromptText("Compulsory: write new password here");
            TextArea newAgain = new TextArea("");
            newAgain.setPromptText("Compulsory: please rewrite new password");
            Button save = new Button("SAVE");
            
            change.getChildren().addAll(oldPasswd, newPasswd, newAgain, save);
            
            sp.setContent(change);
            
        });
        
        return vb;
    }    
}
