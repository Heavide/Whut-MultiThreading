package console;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class DataProcessing {
    static Connection connection;

    public static User searchUser(String name) throws SQLException{
        User res = null;
        String sql = "select * from users where name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            res = new User(resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("role"));
        resultSet.close();
        statement.close();
        return res;
    }

    public static User search(String name, String passwd) throws SQLException {
        User res = null;
        String sql = "select * from users where name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next() && passwd.equals(resultSet.getString("password")))
            res = new User(resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("role"));
        resultSet.close();
        statement.close();
        return res;
    }

    public static Vector<Vector<String>> search_get_Users(String key) throws SQLException {
        Vector<Vector<String>> res = new Vector<>();
        String sql = "select * from users where name like %?%";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            Vector<String> tmp = new Vector<>();
            tmp.add(resultSet.getString("name"));
            tmp.add(resultSet.getString("password"));
            tmp.add(resultSet.getString("role"));
            res.add(tmp);
        }
        resultSet.close();
        statement.close();
        return res;
    }
    public static Vector<Vector<String>> getAllUser() throws SQLException {
        Vector<Vector<String>> res = new Vector<>();
        String sql = "select * from users";
        Statement statement = connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while(resultSet.next()){
            Vector<String> tmp = new Vector<>();
            tmp.add(resultSet.getString("name"));
            tmp.add(resultSet.getString("password"));
            tmp.add(resultSet.getString("role"));
            res.add(tmp);
        }
        resultSet.close();
        statement.close();
        return res;
    }

    public static void connect(){
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/archives_management";
        String user = "root";
        String password = "1234567";
        try{
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("驱动错误");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("数据库错误");
        }
    }

    public static void update(User x) throws SQLException {
        String sql = "update users set password = ?, role = ? where name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, x.getPassword());
        statement.setString(2, x.getRole());
        statement.setString(3, x.getName());
        statement.executeUpdate();
        statement.close();
    }

    public static void insert(User x) throws SQLException {
        String sql = "insert into users values(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, x.getName());
        statement.setString(2, x.getPassword());
        statement.setString(3, x.getRole());
        statement.executeUpdate();
        statement.close();
    }

    public static void delete(String name) throws SQLException {
        String sql = "delete from users where name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.executeUpdate();
        statement.close();
    }

    public static Doc searchDoc(String id) throws SQLException {
        Doc res = null;
        String sql = "select * from docs where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            res = new Doc(
                    resultSet.getString("id"),
                    resultSet.getString("creator"),
                    resultSet.getTimestamp("timestamp"),
                    resultSet.getString("description"),
                    resultSet.getString("filename")
            );
        resultSet.close();
        statement.close();
        return res;
    }

    public static Vector<Vector<String>> search_get_Docs(String key) throws SQLException {
        Vector<Vector<String>> res = new Vector<>();
        String sql = "select * from docs where filename like %?%";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            Vector<String> tmp = new Vector<>();
            tmp.add(resultSet.getString("id"));
            tmp.add(resultSet.getString("filename"));
            tmp.add(resultSet.getString("description"));
            tmp.add(resultSet.getString("creator"));
            tmp.add(resultSet.getTimestamp("timestamp").toString());
            res.add(tmp);
        }
        resultSet.close();
        statement.close();
        return res;
    }

    public static boolean searchDocByName(String name) throws SQLException {
        String sql = "select * from docs where filename = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        boolean x = resultSet.next();
        resultSet.close();
        statement.close();
        return x;
    }

    public static boolean searchDocByID(String id) throws SQLException {
        String sql = "select * from docs where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        boolean x = resultSet.next();
        resultSet.close();
        statement.close();
        return x;
    }

    public static Vector<Vector<String>> getAllDoc() throws SQLException {
        Vector<Vector<String>> res = new Vector<>();
        String sql = "select * from docs";
        Statement statement = connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while(resultSet.next()){
            Vector<String> tmp = new Vector<>();
            tmp.add(resultSet.getString("id"));
            tmp.add(resultSet.getString("filename"));
            tmp.add(resultSet.getString("description"));
            tmp.add(resultSet.getString("creator"));
            tmp.add(resultSet.getTimestamp("timestamp").toString());
            res.add(tmp);
        }
        resultSet.close();
        statement.close();
        return res;
    }

    public static void insertDoc(Doc x) throws SQLException {
        String sql = "insert into docs values(?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, x.getId());
        statement.setString(2, x.getCreator());
        statement.setString(3, x.getTimestamp().toString());
        statement.setString(4, x.getDescription());
        statement.setString(5, x.getFilename());
        statement.executeUpdate();
        statement.close();
    }

}
