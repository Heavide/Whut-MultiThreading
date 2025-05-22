package client;

import server.console.Doc;
import server.console.User;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class Client {

    private static Socket client;
    private static String chatServer = "127.0.0.1";
    private static ObjectOutputStream output;
    private static ObjectInputStream input;

    public Client(){
        try{
            client = new Socket(InetAddress.getByName(chatServer), 12345);
            getStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void connect() {
        try{
            client = new Socket(InetAddress.getByName(chatServer), 12345);
            getStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getStream() throws IOException {
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        input = new ObjectInputStream(client.getInputStream());
    }

    //User search(name, code)
    public static User search(String name, String code) throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("search_name_code");query.add(name);query.add(code);
        output.writeObject(query);
        output.flush();
        return (User)input.readObject();
    }

    //User searchUser(String name)
    public static User searchUser(String name) throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("search_user");query.add(name);
        output.writeObject(query);
        output.flush();
        return (User)input.readObject();
    }

    //Vector<Vector<String>> search_get_Users(String key)
    public static Vector<Vector<String>> search_get_Users(String key) throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("search_get_Users");query.add(key);
        output.writeObject(query);
        output.flush();
        return (Vector<Vector<String>>)input.readObject();
    }

    //Vector<Vector<String>> getAllUser()
    public static Vector<Vector<String>> getAllUser() throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("getAllUser");
        output.writeObject(query);
        output.flush();
        return (Vector<Vector<String>>)input.readObject();
    }

    //void update(User x)
    public static void update(User x) throws IOException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("update_user");query.add(x);
        output.writeObject(query);
        output.flush();
    }

    //void insert(User x)
    public static void insert(User x) throws IOException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("insert_user");query.add(x);
        output.writeObject(query);
        output.flush();
    }

    //void delete(String name)
    public static void delete(String name) throws IOException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("delete_user");query.add(name);
        output.writeObject(query);
        output.flush();
    }

    //Doc searchDoc(String id)
    public static Doc searchDoc(String id) throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("search_doc");query.add(id);
        output.writeObject(query);
        output.flush();
        return (Doc)input.readObject();
    }

    //Vector<Vector<String>> search_get_Docs(String key)
    public static Vector<Vector<String>> search_get_Docs(String key) throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("search_get_Docs");query.add(key);
        output.writeObject(query);
        output.flush();
        return (Vector<Vector<String>>)input.readObject();
    }

    //boolean searchDocByName(String name)
    public static boolean searchDocByName(String name) throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("searchDocByName");query.add(name);
        output.writeObject(query);
        output.flush();
        return (boolean)input.readObject();
    }

    //boolean searchDocByID(String id)
    public static boolean searchDocByID(String id) throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("searchDocByID");query.add(id);
        output.writeObject(query);
        output.flush();
        return (boolean)input.readObject();
    }

    //Vector<Vector<String>> getAllDoc()
    public static Vector<Vector<String>> getAllDoc() throws IOException, ClassNotFoundException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("getAllDoc");
        output.writeObject(query);
        output.flush();
        return (Vector<Vector<String>>)input.readObject();
    }

    //void insertDoc(Doc x)
    public static void insertDoc(Doc x) throws IOException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("insert_doc");query.add(x);
        output.writeObject(query);
        output.flush();
    }

    //download
    public static void download(String name, String to) throws IOException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("download");query.add(name);
        output.writeObject(query);
        output.flush();

        try(FileOutputStream out = new FileOutputStream(to)){
            byte[] buf = new byte[1010];
            int len;
            while((len = input.read(buf)) != -1) out.write(buf, 0, len);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "下载错误","错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "下载成功", "成功", JOptionPane.INFORMATION_MESSAGE);

        input.close();
        output.close();
        client.close();
    }

    //upload
    public static void upload(String name, String from) throws IOException {
        connect();
        Vector<Object> query = new Vector<>();
        query.add("upload");query.add(name);
        output.writeObject(query);
        output.flush();

        try(FileInputStream in = new FileInputStream(from)){
            byte[] buf = new byte[1010];
            int len;
            while((len = in.read(buf)) != -1) output.write(buf, 0, len);
            output.flush();
        }

        input.close();
        output.close();
        client.close();
    }
}
