
package trywithkids.gui;

import com.mongodb.MongoClient;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import trywithkids.database.Database;
import trywithkids.database.DatabaseUsers;
import trywithkids.domain.TryWithKids;
import trywithkids.domain.User;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class GUI extends Application {
    
    private TryWithKids trywithkids;
    private User user;
    
    @Override
    public void init() throws Exception {
        Morphia morphia = new Morphia();
        morphia.mapPackage("trywithkids.domain");
        morphia.mapPackage("trywithkids.gui");
        Datastore datastore = morphia.createDatastore(new MongoClient(), "experiments");
        datastore.ensureIndexes();
        Database database = new Database(morphia, datastore);
        DatabaseUsers userDatabase = new DatabaseUsers(morphia, datastore);
        this.trywithkids = new TryWithKids(database, userDatabase);
        
        userDatabase.clearDatabaseOfUsers();
        if (trywithkids.getUserN() == 0) {
            this.trywithkids.addDefaultMaintenance();
            this.trywithkids.addDefaultEnduser();
        }
        
        if (trywithkids.databaseSize() == 0) {
            trywithkids.addStarterExperiments(); 
        }
    }
    
    @Override
    public void start(Stage window) /*throws Exception */{
        window.setTitle("Try with kids");
        
        //create other subscenes
        GUIadd add = new GUIadd(trywithkids);
        GUIsearch search = new GUIsearch(trywithkids);
        GUIupdate update = new GUIupdate(trywithkids);
        GUIdelete delete = new GUIdelete(trywithkids);
        GUIview view = new GUIview(trywithkids);
        GUIusers users = new GUIusers(trywithkids);
        GUIowninfo ownInfo = new GUIowninfo(trywithkids);
        
        //create the menu and its buttons
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);
        
        Button addButton = new Button("Add new");
        Button updateButton = new Button("Update");
        Button searchButton = new Button("Search");
        Button deleteButton = new Button("Delete");
        Button viewButton = new Button("Browse all");
        Button usersButton = new Button("Users");
        Button signoutButton = new Button("Sign out");
        
        menu.getChildren().addAll(usersButton, viewButton, addButton, updateButton, 
                searchButton, deleteButton, signoutButton);
        
        //create main scene
        BorderPane setting = new BorderPane();
        setting.setTop(menu);
        
        usersButton.setOnAction((event) -> setting.setCenter(users.getView()));
        viewButton.setOnAction((event) -> setting.setCenter(view.getView()));
        addButton.setOnAction((event)-> setting.setCenter(add.getView()));
        updateButton.setOnAction((event) -> setting.setCenter(update.getView()));
        searchButton.setOnAction((event)-> setting.setCenter(search.getView()));
        deleteButton.setOnAction((event) -> setting.setCenter(delete.getView()));
        
        setting.setCenter(users.getView());
        
        Scene selectMaintenance = new Scene(setting, 1000, 600);
        
        BorderPane settingUser = new BorderPane();
        HBox menuUser = new HBox();
        menuUser.setPadding(new Insets(20, 20, 20, 20));
        menuUser.setSpacing(10);
        Button ownInfoButton = new Button("My info");
        Button viewButton2 = new Button("Browse all");
        Button searchButton2 = new Button("Search");
        Button signoutButton2 = new Button("Sign out");
        ownInfoButton.setOnAction((event) -> settingUser.setCenter(ownInfo.getView()));
        viewButton2.setOnAction((event) -> settingUser.setCenter(view.getView()));
        searchButton2.setOnAction((event)-> settingUser.setCenter(search.getView()));
        
        menuUser.getChildren().addAll(ownInfoButton, viewButton2, searchButton2, signoutButton2);
        settingUser.setTop(menuUser);
        settingUser.setCenter(ownInfo.getView());
        
        Scene selectUser = new Scene(settingUser, 1000, 600);
        
        //create login-window
        GridPane login = new GridPane();
        login.setAlignment(Pos.CENTER);
        login.setHgap(10);
        login.setVgap(10);
        login.setPadding(new Insets(20, 20, 20, 20));
        Text scenetitle = new Text("Welcome to TryWithKids");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        login.add(scenetitle, 0, 0, 2, 1);
        Label username = new Label("Username:");
        login.add(username, 0, 1);
        TextField userTextField = new TextField();
        login.add(userTextField, 1, 1);
        Label pw = new Label("Password:");
        login.add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        login.add(pwBox, 1, 2);
        Button loginButton = new Button("Sign in");
        login.add(loginButton, 0, 3);
        loginButton.setOnAction((event) -> {
            String usernameGUI = userTextField.getText();
            String passwdGUI = pwBox.getText();
            loginButton.setText("Signing in");
            
            int successOfLogin = this.trywithkids.checkUser(usernameGUI, passwdGUI);
            
            if (successOfLogin == 2) {
                userTextField.clear();
                userTextField.setPromptText("No such user in database, please try again");
            }
            
            if (successOfLogin == 3) {
                pwBox.clear();
                pwBox.setPromptText("Password does not match. Please try again");
            }
            
            if (successOfLogin == 4) {
                this.user = this.trywithkids.login();
                userTextField.clear();
                pwBox.clear();
                userTextField.setPromptText("");
                loginButton.setText("Sign in");
                view.setUser(this.user);
                users.setUser(this.user);
                ownInfo.setUser(this.user);
                if (this.user.getMaintenance() == true) {
                    window.setScene(selectMaintenance);
                } else {
                    window.setScene(selectUser);
                }
                
            }
            
            if (successOfLogin == 1 || successOfLogin == 5) {
                System.out.println("success of login problem = " + successOfLogin);
            }    
        });
        
        Scene loginSetting = new Scene(login, 600, 600);
        signoutButton.setOnAction((event) -> window.setScene(loginSetting));
        signoutButton2.setOnAction((event) -> window.setScene(loginSetting));
        
        window.setScene(loginSetting);

        window.show();
    }
    
    public static void main(String[] args) {
        launch(GUI.class);  
    }

}
