import console.DataProcessing;
import console.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User obj = new User();
        while(obj != null) {
            try{
                DataProcessing.connect();
                obj = obj.showMenu();
            } catch(SQLException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
        DataProcessing.save();
    }
}