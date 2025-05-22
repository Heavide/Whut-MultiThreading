package console;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DataProcessing {
    static Map<String, User> users;
    static Map<String, Doc> docs;
    static final double Prob_connect = 0;
    static final double Prob_sql = 0.999;
    static boolean if_connect = false;

    static{
//        users = new HashMap<String, User>();
//        docs = new HashMap<String, Doc>();
//        users.put("B", new Browser("B", "123", "Browser"));
//        users.put("A", new Administrator("A", "123", "Administrator"));
//        users.put("O", new Operator("O", "123", "Operator"));
//        docs.put("0001", new Doc("0001", "S", new Timestamp(System.currentTimeMillis()), "test", "test.doc"));
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("D:\\Java_Project\\Management\\Test3\\data\\users.txt"))){
            users = (HashMap<String, User>)input.readObject();
        } catch (IOException e) {
            System.out.println("加载失败");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("D:\\Java_Project\\Management\\Test3\\data\\docs.txt"))){
            docs = (HashMap<String, Doc>)input.readObject();
        } catch (IOException e) {
            System.out.println("加载失败");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static User searchUser(String name) throws SQLException{
        if(!if_connect) throw new SQLException("未连接到数据库");
        if(Math.random() > Prob_sql) throw new SQLException("用户访问出错");
        return users.getOrDefault(name, null);
    }

    public static User search(String name, String passwd) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        if(Math.random() > Prob_sql) throw new SQLException("用户访问出错");
        User tmp = users.getOrDefault(name, null);
        if(tmp == null) return null;
        if(tmp.getPassword().equals(passwd)) return tmp;
        else return null;
    }

    public static void getAllUser() throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        System.out.println(users.size());
        if(Math.random() > Prob_sql) throw new SQLException("用户访问出错");
        for(User x : users.values()){
            System.out.printf("用户名:%s     身份:%s      密码:%s\n\n", x.getName(), x.getRole(), x.getPassword());
        }
    }

    public static void connect(){
        if(Math.random() > Prob_connect) if_connect = true;
    }

    public static void update(User x) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        if(Math.random() > Prob_sql) throw new SQLException("用户更新出错");
        users.put(x.getName(), x);
    }

    public static void insert(User x) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        if(Math.random() > Prob_sql) throw new SQLException("用户增加出错");
        users.put(x.getName(), x);
    }

    public static void delete(String name) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        if(Math.random() > Prob_sql) throw new SQLException("用户修改出错");
        users.remove(name);
    }

    public static Doc searchDoc(String id) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        return docs.getOrDefault(id, null);
    }

    public static void getAllDoc() throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        for(Doc x : docs.values()){
            System.out.printf("编号:%s   档案名:%s\n创建者:%s   创建时间:%s\n档案描述:%s\n\n",
                    x.getId(), x.getFilename(), x.getCreator(), x.getTimestamp(), x.getDescription());
        }
    }

    public static void insertDoc(Doc x) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        docs.put(x.getId(), x);
    }

    public static void save(){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("D:\\Java_Project\\Management\\Test3\\data\\users.txt"))){
            output.writeObject(users);
        } catch (IOException e) {
            System.out.println("保存失败");
        }
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("D:\\Java_Project\\Management\\Test3\\data\\docs.txt"))){
            output.writeObject(docs);
        } catch (IOException e) {
            System.out.println("保存失败");
        }
    }

}
