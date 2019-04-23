
package trywithkids.gui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.User;

/**
 * view to see end-users own info
 * @author satu
 */
public class GUIowninfo {
    private TryWithKids tryWithKids;
    private User user;
    
    /**
     * constructor
     * @param tryWithKids sets the trywithkids application logic to the view
     */
    public GUIowninfo(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    /**
     * sets user-info onto the view
     * @param user sets user-info for the application, so user is able to view their own info
     */
    public void setUser(User user) {
        this.user = user;
    } 
    
    /**
     * returns view
     * @return view of the userinfo
     */
    public Parent getView() {
        BorderPane bp = new BorderPane();
        
        Button current = new Button("Own info");
        Button changePassw = new Button("Change password");
        HBox actionSelect = new HBox(current, changePassw);
        actionSelect.setSpacing(10);
        actionSelect.setPadding(new Insets(20, 20, 20, 20));
        
        Label currentInfo = new Label("CURRENT INFORMATION:");
        TextArea infoArea = new TextArea();

        VBox infoBox = new VBox();
        infoBox.setSpacing(10);
        infoBox.setPadding(new Insets(20, 20, 20, 20));
        infoBox.getChildren().addAll(currentInfo, infoArea);
        
        VBox change = new VBox();
        change.setLayoutX(5);
        change.setSpacing(10);
        change.setPadding(new Insets(20, 20, 20, 20));
        Label changePw = new Label("CHANGE PASSWORD");    
        PasswordField oldPasswd = new PasswordField();
        oldPasswd.setPromptText("Compulsory: write old password here");
        PasswordField newPasswd = new PasswordField();
        newPasswd.setPromptText("Compulsory: write new password here. Must be over 8 charachers long.");
        PasswordField newAgain = new PasswordField();
        newAgain.setPromptText("Compulsory: please rewrite new password");
        Button save = new Button("SAVE");
            
        change.getChildren().addAll(changePw, oldPasswd, newPasswd, newAgain, save);
            
        save.setOnAction((event)-> {
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
                newPasswd.clear();
                newAgain.clear();
                oldPasswd.clear();
                change.getChildren().add(saved);
            }
        });
        
        current.setOnAction((event) -> {
            String info = getInfo();
            infoArea.setText(info);
            bp.setCenter(infoBox);
        });
        
        changePassw.setOnAction((event) -> {
            bp.setCenter(change); 
        });
        
        bp.setTop(actionSelect);
        
        return bp;
    }
    
    /**
     * returns the toString-method of this.user - updated from the database.
     * @return String user info
     */
    public String getInfo() {
        // aiemmin oli return this.user.toString();
        this.user = this.tryWithKids.findUser(this.user);
        return this.user.toString();
    }
}
