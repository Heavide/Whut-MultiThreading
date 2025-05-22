package console;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

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
//        users.put("S", new Operator("S", "123", "Operator"));
//        users.put("D", new Operator("D", "123", "Operator"));
//        users.put("F", new Operator("F", "123", "Operator"));
//        users.put("G", new Operator("G", "123", "Operator"));
//        users.put("H", new Operator("H", "123", "Operator"));
//        users.put("J", new Operator("J", "123", "Operator"));
//        users.put("K", new Operator("K", "123", "Operator"));
//        users.put("L", new Operator("L", "123", "Operator"));
//        users.put("Z", new Operator("Z", "123", "Operator"));
//        users.put("X", new Operator("X", "123", "Operator"));
//        users.put("C", new Operator("C", "123", "Operator"));
//        users.put("V", new Operator("V", "123", "Operator"));
//        users.put("N", new Operator("N", "123", "Operator"));
//        users.put("M", new Operator("M", "123", "Operator"));
//        users.put("P", new Operator("P", "123", "Operator"));
//        users.put("I", new Operator("I", "123", "Operator"));
//        users.put("Q", new Operator("Q", "123", "Operator"));
//        users.put("W", new Operator("W", "123", "Operator"));
//        users.put("E", new Operator("E", "123", "Operator"));
//        users.put("R", new Operator("R", "123", "Operator"));
//        users.put("T", new Operator("T", "123", "Operator"));
//        users.put("ABC", new Operator("ABC", "123", "Operator"));
//        docs.put("0001", new Doc("0001", "S", new Timestamp(System.currentTimeMillis()), "test", "test.doc"));
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("D:\\Java_Project\\Management\\Test4\\data\\users.txt"))){
            users = (HashMap<String, User>)input.readObject();
        } catch (IOException e) {
            System.out.println("用户加载失败");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("D:\\Java_Project\\Management\\Test4\\data\\docs.txt"))){
            docs = (HashMap<String, Doc>)input.readObject();
        } catch (IOException e) {
            System.out.println("文件加载失败");
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

    public static Vector<Vector<String>> search_get_Users(String key) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        if(Math.random() > Prob_sql) throw new SQLException("用户访问出错");
        Vector<Vector<String>> res = new Vector<>();
        for(var x : users.values()){
            if(x.getName().contains(key)){
                Vector<String> tmp = new Vector<>();
                tmp.add(x.getName());
                tmp.add(x.getPassword());
                tmp.add(x.getRole());
                res.add(tmp);
            }
        }
        return res;
    }
    public static Vector<Vector<String>> getAllUser() throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        if(Math.random() > Prob_sql) throw new SQLException("用户访问出错");
        Vector<Vector<String>> res = new Vector<>();
        for(User x : users.values()){
            Vector<String> tmp = new Vector<>();
            tmp.add(x.getName());
            tmp.add(x.getPassword());
            tmp.add(x.getRole());
            res.add(tmp);
        }
        return res;
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

    public static Vector<Vector<String>> search_get_Docs(String key) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        if(Math.random() > Prob_sql) throw new SQLException("档案访问出错");
        Vector<Vector<String>> res = new Vector<>();
        for(var x : docs.values()){
            if(x.getFilename().contains(key)){
                Vector<String> tmp = new Vector<>();
                tmp.add(x.getId());
                tmp.add(x.getFilename());
                tmp.add(x.getDescription());
                tmp.add(x.getCreator());
                tmp.add(x.getTimestamp().toString());
                res.add(tmp);
            }
        }
        return res;
    }

    public static boolean searchDocByName(String name) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        for(var x : docs.values()){
            if(x.getFilename().equals(name)) return true;
        }
        return false;
    }

    public static boolean searchDocByID(String id) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        for(var x : docs.values()){
            if(x.getId().equals(id)) return true;
        }
        return false;
    }

    public static Vector<Vector<String>> getAllDoc() throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        Vector<Vector<String>> res = new Vector<>();
        for(Doc x : docs.values()){
            Vector<String> tmp = new Vector<>();
            tmp.add(x.getId());
            tmp.add(x.getFilename());
            tmp.add(x.getDescription());
            tmp.add(x.getCreator());
            tmp.add(x.getTimestamp().toString());
            res.add(tmp);
        }
        return res;
    }

    public static void insertDoc(Doc x) throws SQLException {
        if(!if_connect) throw new SQLException("未连接到数据库");
        docs.put(x.getId(), x);
    }

    public static void save(){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("D:\\Java_Project\\Management\\Test4\\data\\users.txt"))){
            output.writeObject(users);
        } catch (IOException e) {
            System.out.println("保存失败");
        }
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("D:\\Java_Project\\Management\\Test4\\data\\docs.txt"))){
            output.writeObject(docs);
        } catch (IOException e) {
            System.out.println("保存失败");
        }
    }

}
