import java.util.ArrayList;
import java.util.HashMap;

public class Project {
    int days_to_complete;
    int proj_score;
    int best_before;
    String proj_name;
    HashMap<Role,Integer> proj_roles = null;
    ArrayList<Person> collab = new ArrayList<Person>();
    Boolean rej = false;
    public Project(int days_to_complete, int proj_score, int best_before, String proj_name, HashMap<Role,Integer> proj_roles) {
        this.days_to_complete = days_to_complete;
        this.proj_score = proj_score;
        this.best_before = best_before;
        this.proj_name = proj_name;
        this.proj_roles = proj_roles;
    }
    public void setCollab(ArrayList<Person> collab){
        this.collab = collab;
    }

    public static int compare(Project h1, Project h2) {
        return h1.best_before- h2.best_before;
    }



    @Override
    public String toString() {
        return "Project{" +
                "days_to_complete=" + days_to_complete +
                ", proj_score=" + proj_score +
                ", best_before=" + best_before +
                ", proj_name='" + proj_name + '\'' +
                ", proj_roles=" + proj_roles +
                '}';
    }

}
