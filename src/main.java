import java.io.*;
import java.util.*;

public class main {

    public static void main(String[] args) throws IOException {

        String[] files = {
                "a_an_example.in",
                "b_better_start_small.in",
                "c_collaboration.in",
                "d_dense_schedule.in",
                "e_exceptional_skills.in",
                "f_find_great_mentors.in"
        };

        for (int file_index = 0; file_index < files.length; file_index++) {

            File file = new File(files[file_index] + ".txt");
            Scanner sc = new Scanner(file);
            int nr_person = sc.nextInt();
            int nr_proj = sc.nextInt();
            ArrayList<Person> person_list = new ArrayList<>();

            //get all people
            for(int i = 0; i < nr_person; i++){
                String pers_name = sc.next();
                int nr_roles = sc.nextInt();
                HashMap<Role,Integer> store_roles = new HashMap<>();
                //get all roles
                for(int j = 0; j < nr_roles; j++){
                    Role role_name = new Role(sc.next());
                    int role_lvl = sc.nextInt();
                    store_roles.put(role_name,role_lvl);
                }
                Person p1 = new Person(store_roles,pers_name,0);
                person_list.add(p1);
            }

            //get all projects
            ArrayList<Project> proj_list = new ArrayList<Project>();
            for(int i = 0; i < nr_proj; i++) {
                String proj_name = sc.next();
                int nr_days = sc.nextInt();
                int proj_score = sc.nextInt();
                int best_before = sc.nextInt();
                int nr_contributors = sc.nextInt();
                LinkedHashMap<Role,Integer> new_map = new LinkedHashMap<Role,Integer>();
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
                //System.out.println(p.toString());
            }


            System.out.println("after sorting projects");
            proj_list.sort(Project::compare);

            for(Project p : proj_list){
                //System.out.println(p.toString());
            }




            ArrayList<Project> rejects = new ArrayList<>();
            //System.out.println(Arrays.toString(proj_list.toArray()));
            for(Project proj : proj_list){
                ArrayList<Role> mentor_roles = new ArrayList<>();
                if(proj.proj_name.equals("FormsZv3"))
                    System.out.println(proj.proj_roles.keySet());
                int col_size = 0;
                for(Role r1 :proj.proj_roles.keySet()){
                    int bottom = proj.proj_roles.get(r1);
                    //System.out.println(proj.proj_name + " " + Arrays.toString(candid.toArray()));
                    for(Person person : person_list){
                        if(proj.collab.contains(person))
                            continue;
                        //person's roles
                        Set<Role> roleSet = person.roles.keySet();
                        for(Role thisrole:roleSet){
                            //if person has role > neccessary then add person to group
                            if(thisrole.toString().equals(r1.toString())&&person.roles.get(thisrole)>=bottom){
                                if(proj.proj_name.equals("FormsZv3"))
                                    System.out.println(thisrole + " " + r1 + " " +person + " " + bottom);
                                proj.collab.add(person);
                                mentor_roles.addAll((Collection<? extends Role>) person.roles.keySet());
//                                if(person.roles.get(thisrole)==bottom){
//                                    Integer lvl = person.roles.get(thisrole);
//                                    person.roles.put(thisrole,lvl+1);
//                                }
                                break;
                            }
                        }
                        if(proj.collab.size()>col_size)
                            break;

                    }
                    if(proj.collab.size()>col_size)
                        col_size++;
                    else
                        break;
                }
                if(proj.collab.size()<proj.proj_roles.size()){
                    rejects.add(proj);
                    proj.rej = true;
                    //System.out.println(proj.proj_name);
                }else{
                    for(int i = 0;i<proj.collab.size();i++){
                        //roles of person i
                        Set<Role> keys = proj.collab.get(i).roles.keySet();
                        //roles of project
                        List roles = List.of(proj.proj_roles.keySet().toArray());
                        for(Role r1:keys) {
                            System.out.println(proj.proj_roles.get(roles.get(i)) + " " + proj.collab.get(i).roles.get(r1) + " " + roles.get(i) + " " + r1);
                            if (roles.get(i).toString().equals(r1.toString()) && proj.proj_roles.get(roles.get(i)) == proj.collab.get(i).roles.get(r1)){
                                System.out.println("increased");
                                proj.collab.get(i).roles.put(r1,proj.collab.get(i).roles.get(r1)+1);
                                break;
                            }
                        }
                    }
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
                    //System.out.println(p1.proj_name);

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
