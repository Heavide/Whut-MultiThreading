package server;

import server.console.DataProcessing;
import server.console.Doc;
import server.console.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server {

    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public void runServer() {
        try {
            server = new ServerSocket(12345, 100);
            while (true) {
                try {
                    waitConnection();
                    getStream();
                    checkStream();
                } catch (EOFException e) {

                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException ex) {

        }
    }

    public void waitConnection() throws IOException {
        System.out.println("Wait for connection...");
        client = server.accept();
        System.out.println(client.getInetAddress().getHostName());
    }

    public void getStream() throws IOException {
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        input = new ObjectInputStream(client.getInputStream());
    }

    public void checkStream() throws IOException, ClassNotFoundException, SQLException {
        Vector<Object> op = (Vector<Object>) input.readObject();
        if(op.get(0).equals("search_name_code")){
            output.writeObject(DataProcessing.search(op.get(1).toString(), op.get(2).toString()));
            output.flush();
        }
        else if(op.get(0).equals("search_user")){
            output.writeObject(DataProcessing.searchUser(op.get(1).toString()));
            output.flush();
        }
        else if(op.get(0).equals("search_get_Users")){
            output.writeObject(DataProcessing.search_get_Users(op.get(1).toString()));
            output.flush();
        }
        else if(op.get(0).equals("getAllUser")){
            output.writeObject(DataProcessing.getAllUser());
            output.flush();
        }
        else if(op.get(0).equals("update_user")){
            DataProcessing.update((User)op.get(1));
        }
        else if(op.get(0).equals("insert_user")){
            DataProcessing.insert((User)op.get(1));
        }
        else if(op.get(0).equals("delete_user")){
            DataProcessing.delete(op.get(1).toString());
        }
        else if(op.get(0).equals("search_doc")){
            output.writeObject(DataProcessing.searchDoc(op.get(1).toString()));
            output.flush();
        }
        else if(op.get(0).equals("search_get_Docs")){
            output.writeObject(DataProcessing.search_get_Docs(op.get(1).toString()));
            output.flush();
        }
        else if(op.get(0).equals("searchDocByName")){
            output.writeObject(DataProcessing.searchDocByName(op.get(1).toString()));
            output.flush();
        }
        else if(op.get(0).equals("searchDocByID")){
            output.writeObject(DataProcessing.searchDocByID(op.get(1).toString()));
            output.flush();
        }
        else if(op.get(0).equals("getAllDoc")){
            output.writeObject(DataProcessing.getAllDoc());
            output.flush();
        }
        else if(op.get(0).equals("insert_doc")){
            DataProcessing.insertDoc((Doc)op.get(1));
        }
        else if(op.get(0).equals("download")){
            String from = "D:\\Java_Project\\Management\\Test6\\data\\upload\\" + op.get(1);
            try(FileInputStream in = new FileInputStream(from)){
                byte[] buf = new byte[1010];
                int len;
                while((len = in.read(buf)) != -1) output.write(buf, 0, len);
            }
            output.flush();
        }
        else if(op.get(0).equals("upload")){
            String to = "D:\\Java_Project\\Management\\Test6\\data\\upload\\" + op.get(1);
            try(FileOutputStream out = new FileOutputStream(to)){
                byte[] buf = new byte[1010];
                int len;
                while((len = input.read(buf)) != -1) out.write(buf, 0, len);
            }
        }

        input.close();
        output.close();
        System.out.println(op.get(0));
    }
}
