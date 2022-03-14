import java.io.*;
import java.util.*;

public class main {

    public static void main(String[] args) throws IOException {

        String[] files = {
//                "a_an_example.in",
//                "b_better_start_small.in",
//                "c_collaboration.in",
                "d_dense_schedule.in",
//                "e_exceptional_skills.in",
//                "f_find_great_mentors.in"
        };

        for (int file_index = 0; file_index < files.length; file_index++) {

            File file = new File(files[file_index] + ".txt");
            Scanner sc = new Scanner(file);
            int nr_person = sc.nextInt();
            int nr_proj = sc.nextInt();
            ArrayList<Person> person_list = new ArrayList<>();
            ArrayList<Role> only_roles = new ArrayList<>();
            HashMap<Role, ArrayList<Person>> role_list = new HashMap<>();
            for(int i = 0; i < nr_person; i++){
                String pers_name = sc.next();
                int nr_roles = sc.nextInt();
                HashMap<Role,Integer> store_roles = new HashMap<>();
                //get all roles
                for(int j = 0; j < nr_roles; j++){
                    String role_name = sc.next();
                    int role_lvl = sc.nextInt();
                    Role use = null;
                    for(Role r : only_roles) {
                        if (r.role.equals(role_name)) {
                            use = r;
                        }
                    }
                    if(use == null){
                        Role newRole = new Role(role_name);
                        store_roles.put(newRole,role_lvl);
                        only_roles.add(newRole);
                    }
                    else {
                        store_roles.put(use, role_lvl);
                    }
                }
                Person p1 = new Person(store_roles,pers_name,0);
                person_list.add(p1);
                int has = 0;
                Role use = null;
                for(Role rol : store_roles.keySet()) {
                    for(Role r1 : role_list.keySet()) {
                        if (r1.role.equals(rol.role)) {
                            has = 1;
                            use = r1;
                            break;
                        }
                    }
                    //System.out.println(rol.role + " " + has);
                    if (has==0) {
                        ArrayList<Person> per = new ArrayList<Person>();
                        per.add(p1);
                        role_list.put(rol,per);
                    }else{
                        ArrayList<Person> old_per = role_list.get(use);
                        old_per.add(p1);
                        role_list.put(use,old_per);
                    }
                    has = 0;
                    use = null;
                }
            }
            //sort arraylist of each role
            for(Role role: role_list.keySet()){
                ArrayList<Person> candid = role_list.get(role);
                candid.sort(Comparator.comparing(h -> h.roles.get(role)));
                //System.out.println(role + " candid " + role_list.get(role).toString());
                role_list.put(role,candid);
//                for(int i = 0, j = 1; i < candid.size()-1; i++){
//                    HashMap<Role,Integer> before = candid.get(i).roles;
//                    HashMap<Role,Integer> after = candid.get(j).roles;
//
//                }
            }
            for(Role role: role_list.keySet()) {
                break;
                //  System.out.println(role + " candid " + role_list.get(role).toString());
            }

            ArrayList<Project> proj_list = new ArrayList<Project>();
            for(int i = 0; i < nr_proj; i++) {
                String proj_name = sc.next();
                int nr_days = sc.nextInt();
                int proj_score = sc.nextInt();
                int best_before = sc.nextInt();
                int nr_contributors = sc.nextInt();
                HashMap<Role,Integer> new_map = new HashMap<Role,Integer>();
                for(int j = 0; j < nr_contributors; j++){
                    String role_name = sc.next();
                    int role_lvl = sc.nextInt();
                    new_map.put(new Role(role_name),role_lvl);
                }
                Project proj1 = new Project(nr_days,proj_score,best_before,proj_name,new_map);
                proj_list.add(proj1);
            }
//            for(Person p : person_list){
//                System.out.println(p.toString());
//            }
            System.out.println("pre sorting projects");
            for(Project p : proj_list){
                System.out.println(p.toString());
            }


            System.out.println("after sorting projects");
            proj_list.sort(Project::compare);

            for(Project p : proj_list){
                System.out.println(p.toString());
            }




            ArrayList<Project> rejects = new ArrayList<>();
            //System.out.println(Arrays.toString(proj_list.toArray()));
            for(Project proj : proj_list){
                ArrayList<Role> mentor_roles = new ArrayList<>();
                for(Role r1 :proj.proj_roles.keySet()){
                    int bottom = proj.proj_roles.get(r1);
                    ArrayList<Person> candid = role_list.get(r1);
                    //System.out.println(proj.proj_name + " " + Arrays.toString(candid.toArray()));
                    for(Person p : candid){
                        if(proj.collab != null) {
                            if (proj.collab.contains(p))
                                continue;
                        }
                        //System.out.println(proj.proj_roles.get(r1));
                        if(p.roles.get(r1)>=bottom){
                            proj.collab.add(p);
                            mentor_roles.addAll((Collection<? extends Role>) p.roles.keySet());
                            p.till_avl+=proj.days_to_complete;
                            if(p.roles.get(r1)==bottom){
                                Integer lvl = p.roles.get(r1);
                                p.roles.put(r1,lvl+1);
                            }
                            break;
                        }
                    }
                }
                if(proj.collab.size()<proj.proj_roles.size()){
                    rejects.add(proj);
                    proj.rej = true;
                    System.out.println(proj.proj_name);
                }
            }


            FileWriter fr = new FileWriter(new File("output" + file_index + ".txt"), true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            int min = 0;
            for(Project p1 :proj_list) {
                if(p1.rej)
                    min ++;
            }
            pr.println(proj_list.size()-min);

            for(Project p1 :proj_list){
                if(p1.rej){
                    System.out.println(p1.proj_name);

                    continue;
                }
                pr.println(p1.proj_name);
                for(Person per: p1.collab){
                    pr.print(per.person_name + " ");
                }
                pr.println();
            }
            pr.close();

        }


    }

}
