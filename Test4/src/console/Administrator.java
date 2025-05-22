package console;

import java.io.IOException;
import java.sql.SQLException;

public class Administrator extends User{

    public Administrator(String name, String passwd, String role){
        super(name, passwd, role);
    }
    @Override
    public User showMenu() throws SQLException, IOException {
        return this;
    }

    public void changeUserInfo() throws SQLException {

    }

    public void delUser() throws SQLException {

    }

    public void addUser() throws SQLException {

    }

    public void listUser() throws SQLException {

    }
}
