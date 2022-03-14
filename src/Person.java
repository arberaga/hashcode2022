import java.util.HashMap;

public class Person {
    HashMap<Role, Integer> roles = null;
    String person_name;
    int till_avl;
    Person(HashMap<Role,Integer> roles, String name, int till_avl){
        this.roles = roles;
        this.person_name = name;
        this.till_avl = till_avl;
    }

    @Override
    public String toString() {
        return "Person{" +
                "roles=" + roles +
                ", person_name='" + person_name + '\'' +
                '}';
    }

    public static int compare(Person h1, Person h2) {
        return 0;
    }
}
