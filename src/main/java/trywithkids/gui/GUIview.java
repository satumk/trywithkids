
package trywithkids.gui;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import trywithkids.domain.Experiment;
import trywithkids.domain.TryWithKids;


public class GUIview {
    
    private TryWithKids tryWithKids;
    
    public GUIview(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    public Parent getNakyma() {
        ScrollPane sp = new ScrollPane();
        FlowPane setting = new FlowPane();
        setting.setPadding(new Insets(20, 20, 20, 20));
        setting.setVgap(10);
        setting.setHgap(10);
        setting.setPadding(new Insets(10, 10, 10, 10));
        VBox exp = new VBox();
        setting.getChildren().add(exp);
        
        List<Experiment> experiments = this.tryWithKids.findAll();
        
        for (Experiment experiment : experiments) {
            System.out.println(experiment.toString());
        }
        /*
        GridPane setting = new GridPane();
        
        Label info = new Label("This view-window is still under construction");
        
        setting.setAlignment(Pos.CENTER);
        setting.setVgap(10);
        setting.setHgap(10);
        setting.setPadding(new Insets(10, 10, 10, 10));
        
        setting.add(info, 0, 0);
        */
        return setting;
    }
}
