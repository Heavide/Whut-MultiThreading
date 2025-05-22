package console;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Operator extends User{

    public Operator(String name, String passwd, String role){
        super(name, passwd, role);
    }
    @Override
    public User showMenu() throws SQLException, IOException {
        return this;
    }

    public void uploadFile() throws IOException, SQLException {

    }
}
