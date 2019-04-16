
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.User;

/**
 * GUI for admin-users (maintenance) to see user information
 * @author satu
 */
public class GUIusers {
    private TryWithKids tryWithKids;
    private User user;
    
    /**
     *
     * @param tryWithKids sets the trywithkids application logic to the view
     */
    public GUIusers(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    /**
     * sets user information for GUI
     * @param user sets user info for app, so maintenance-class user can see 
     * other users and their own info
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * returns view
     * @return view of users
     */
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
            Label usernameText = new Label("Username:");
            TextArea username = new TextArea("");
            username.setPromptText("Compulsory: please write your username");
            Label passwordText = new Label("Password:");
            PasswordField password = new PasswordField();
            password.setPromptText("Compulsory: please choose a password. Must "
                    + "be over 8 characters long");
            Label passwordTextAgain = new Label("Repeat password:");
            PasswordField rewritePasswd = new PasswordField();
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
            add.getChildren().addAll(instruction, usernameText, username, passwordText,
                    password, passwordTextAgain, rewritePasswd, role, 
                    rolesgroup, save);
            
            save.setOnAction((event2) -> {
                Boolean allTextsFilled = false;
                Boolean usernameFree = false;
                Boolean passwordsMatch = false;
                Boolean roleFilled = false;
                Boolean passwdLongEnough = false;
                
                if (username.getText().isEmpty() || password.getText().isEmpty() 
                        || rewritePasswd.getText().isEmpty()) {
                    Label errorMissingInfo = new Label("Please fill all compulsory information");
                    add.getChildren().add(errorMissingInfo);
                } else {
                    allTextsFilled = true;
                }
                
                String usernameString = username.getText();
                if (this.tryWithKids.checkUsernameExists(usernameString) == true) {
                    username.clear();
                    username.setPromptText("Username already in use. Please select another");
                } else {
                    usernameFree = true;
                }
                
                String passwordString = password.getText();
                if (!passwordString.equals(rewritePasswd.getText())) {
                    password.clear();
                    password.setPromptText("Passwords did not match. Please try again");
                    rewritePasswd.clear();
                    rewritePasswd.setPromptText("Passwords did not match. Please try again");
                } else {
                    passwordsMatch = true;
                }
                
                if (passwordString.length() < 9) {
                    password.clear();
                    password.setPromptText("Password is not long enough. "
                            + "Must be at least 9 characters long");
                    rewritePasswd.clear();
                    rewritePasswd.setPromptText("Password is not long enough. "
                            + "Must be at least 9 characters long");
                } else {
                    passwdLongEnough = true;
                }
                
                ToggleButton selected = (ToggleButton) roles.getSelectedToggle();
                Boolean admin = false;
                if (selected == null) {
                    Label errorRoleMissing = new Label("Error: role is missing. Please select a role");
                    add.getChildren().add(errorRoleMissing);
                } else if (selected.equals(maintenance)) {
                    admin = true;
                    roleFilled = true;
                } else {
                    roleFilled = true;
                }
                
                if (allTextsFilled == true && usernameFree == true 
                        && passwordsMatch == true && roleFilled == true 
                        && passwdLongEnough == true) {
                    this.tryWithKids.addUserThroughGUI(usernameString, passwordString, admin);
                    Label success = new Label("User saved");
                    add.getChildren().add(success);
                } else {
                    Label error = new Label("Error. Could not save user");
                    add.getChildren().add(error);
                }

            });
            
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
            
            listView.getSelectionModel().selectedItemProperty()
                    .addListener(new ChangeListener<User>() {
                
                @Override
                public void changed(ObservableValue<? extends User> observable, 
                        User oldValue, User newValue) {
                    int index = listView.getSelectionModel().getSelectedIndex();

                    User deleteUser = users.get(index);
                    String deleteUserName = deleteUser.getUsername();

                    // creating a popup window to make sure user wants to delete a user
                    Button yes = new Button("Yes, I am sure");
                    Button no = new Button("Cancel");
                    Label label = new Label("Are you sure you want to delete user");
                    Label userLabel = new Label("Username to be deleted: " + deleteUserName);
                    Label makingSure = new Label("You cannot undo this action");
                    Label lastAdmin = new Label("If deleted user is the last maintenance-"
                            + "type-user in database, \na new default-maintenance user will"
                            + " be added to the database");
                    VBox newStage = new VBox();
                    newStage.setSpacing(10);
                    newStage.setPadding(new Insets(20, 20, 20, 20));
                    newStage.getChildren().add(makingSure);
                    newStage.getChildren().add(label);
                    newStage.getChildren().add(userLabel);
                    newStage.getChildren().add(lastAdmin);
                    newStage.getChildren().add(no);
                    newStage.getChildren().add(yes);

                    Scene secondScene = new Scene(newStage, 450, 250);
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Deleting user");
                    newWindow.setScene(secondScene);

                    yes.setOnAction((event) -> { 
                        // deleting from database
                        deleteOne(deleteUser);
                        //deleting from UI
                        users.remove(index);
                        //showing changes in UI
                        listView.refresh();
                        //admin-check
                        if (deleteUser.getMaintenance() == true) {
                            adminCheck();
                        } 
                        
                        newWindow.hide();
                    });

                    no.setOnAction((event) -> {
                        newWindow.hide();
                    });

                    newWindow.show();

                }  

            });
            scrollUsers.setContent(new Label("USERS IN DATABASE"));
            scrollUsers.setContent(new Label("To delete user: please click on a listing below"));
            scrollUsers.setContent(listView);
            
            sp.setContent(scrollUsers);
        });
        
        changePasswd.setOnAction((event) -> {
            VBox change = new VBox();
            change.setLayoutX(5);
            change.setSpacing(10);
            change.setPadding(new Insets(20, 20, 20, 20));
            
            PasswordField oldPasswd = new PasswordField();
            oldPasswd.setPromptText("Compulsory: write old password here");
            PasswordField newPasswd = new PasswordField();
            newPasswd.setPromptText("Compulsory: write new password here. "
                    + "Must be over 8 charachers long.");
            PasswordField newAgain = new PasswordField();
            newAgain.setPromptText("Compulsory: please rewrite new password");
            Button save = new Button("SAVE");
            
            change.getChildren().addAll(oldPasswd, newPasswd, newAgain, save);
            
            save.setOnAction((event2)-> {
                String oldPW = oldPasswd.getText();
                String newPW = newPasswd.getText();
                String newAg = newAgain.getText();
                String userPassword = this.user.getPassword();
                Boolean newPasswordsMatch = false;
                Boolean oldpasswdMatchesOneOnFile = false;
                Boolean passwordOver8Char = false;
                
                if (newPW.length()<9) {
                    newPasswd.clear();
                    newPasswd.setPromptText("Password needs to be over 8 characters long");
                    newAgain.clear();
                    newAgain.setPromptText("Password needs to be over 8 characters long");
                } else {
                    passwordOver8Char = true;
                }
                
                if (!newAg.equals(newPW)) {
                    newPasswd.clear();
                    newPasswd.setPromptText("Passwords did not match");
                    newAgain.clear();
                    newAgain.setPromptText("Passwords did not match");
                } else {
                    newPasswordsMatch = true;
                }
                
                if (!oldPW.contentEquals(userPassword)) {
                    oldPasswd.clear();
                    oldPasswd.setPromptText("Password incorrect");
                } else {
                    oldpasswdMatchesOneOnFile = true;
                }
                
                if (newPasswordsMatch == true && oldpasswdMatchesOneOnFile == true 
                        && passwordOver8Char == true) {
                    this.tryWithKids.changePassword(user, newPW);
                    user.setPassword(newPW);
                    Label saved = new Label("New password saved");
                    change.getChildren().add(saved);
                }
            });
            
            sp.setContent(change);
            
        });
        
        return vb;
    }    
    

    /**
     * passes the user to trywithkids to be deleted
     * @param user user-class instance to be deleted
     */
    public void deleteOne(User user) {
        this.tryWithKids.deleteUser(user);
    }
    
    public void adminCheck() {
        this.tryWithKids.isMaintenancePresent();
    }
}
