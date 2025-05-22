package console;

import java.util.HashMap;
import java.util.Map;

public class DataProcessing {
    static Map<String, User> users;

    static{
        users = new HashMap<String, User>();
        users.put("B", new Browser("B", "123", "Browser"));
        users.put("A", new Administrator("A", "123", "Administrator"));
        users.put("O", new Operator("O", "123", "Operator"));
    }

    public static User searchUser(String name){
        return users.getOrDefault(name, null);
    }

    public static User search(String name, String passwd){
        User tmp = users.getOrDefault(name, null);
        if(tmp == null) return null;
        if(tmp.getPassword().equals(passwd)) return tmp;
        else return null;
    }

    public static void getAllUser(){
        for(User x : users.values()){
            System.out.printf("用户名:%s    身份:%s\n", x.getName(), x.getRole());
        }
    }

    public static void update(User x){
        users.put(x.getName(), x);
    }

    public static void insert(User x){
        users.put(x.getName(), x);
    }

    public static void delete(String name){
        users.remove(name);
    }
}
