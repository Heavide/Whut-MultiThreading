package server.console;

import client.Client;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class User implements Serializable{
    private String User_Name;
    private String User_Password;
    private String User_Role;
    public User(String name, String password, String role){
        this.User_Name = name;
        this.User_Password = password;
        this.User_Role = role;
    }

    public User(){}

    public void changeSelfInfo(String x) throws SQLException, IOException {
        if(Math.random() > 0.999) throw new SQLException("密码修改出错");
        this.User_Password = x;
        Client.update(this);
    }

    public String getPassword(){return this.User_Password;}

    public String getName(){return this.User_Name;}

    public String getRole(){return this.User_Role;}
}
