package console;

import java.io.IOException;
import java.sql.SQLException;

public class Browser extends User{

    public Browser(String name, String passwd, String role){
        super(name, passwd, role);
    }
    @Override
    public User showMenu() throws SQLException, IOException {
        return this;
    }
}
