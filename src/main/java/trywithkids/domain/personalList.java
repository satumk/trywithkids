package trywithkids.domain;

import java.util.ArrayList;
import java.util.List;

public class personalList {
    private User creator;
    private List<Experiment> experiments;
    
    public personalList(User user) {
        this.creator = user;
        this.experiments = new ArrayList<>();
    }
}