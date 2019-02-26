
package trywithkids.domain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import trywithkids.domain.TryWithKids;

public class GUIupdate {
    private TryWithKids tryWithKids;
    
    public GUIupdate(TryWithKids tryWithKids) {
        this.tryWithKids = tryWithKids;
    }
    
    public Parent getNakyma() {
        GridPane setting = new GridPane();
        
        Label info = new Label("This update-window is still under construction");
        
        setting.setAlignment(Pos.CENTER);
        setting.setVgap(10);
        setting.setHgap(10);
        setting.setPadding(new Insets(10, 10, 10, 10));
        
        setting.add(info, 0, 0);
        
        return setting;
    }
}
