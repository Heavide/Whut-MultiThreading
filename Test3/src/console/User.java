package console;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class User implements Serializable{
    private String User_Name;
    private String User_Password;
    private String User_Role;
    User(String name, String password, String role){
        this.User_Name = name;
        this.User_Password = password;
        this.User_Role = role;
    }

    public User(){}
    public User showMenu() throws SQLException, IOException {
        System.out.print("""
        ****欢迎进入档案系统****
                1.登录
                2.退出
        *********************
        """);
        System.out.print("请选择你的菜单:");
        Scanner in = new Scanner(System.in);
        int op = 0;
        try{
            op = in.nextInt();
        }catch(InputMismatchException e){
            System.out.println("输入非法，请重新输入");
            return this;
        }

        if(op == 1){
            System.out.println("请输入你的用户名:");
            String name = in.next();
            System.out.println("请输入你的密码:");
            String passwd = in.next();
            User now = DataProcessing.search(name, passwd);
            if(now == null){
                System.out.println("用户名或密码错误，请重新登录。");
                return this;
            }
            else return now;
        }
        else return null;
    }

    public void showFileList() throws IOException, SQLException {
        if(Math.random() > 0.8) throw new IOException("档案访问出错");
        DataProcessing.getAllDoc();
    }

    public void downloadFile() throws IOException, SQLException {
        System.out.println("请输入档案编号");
        Scanner in = new Scanner(System.in);
        String id = in.next();
        Doc x = DataProcessing.searchDoc(id);
        if(x == null) {
            System.out.println("档案不存在");
            return;
        }
        try(FileInputStream input = new FileInputStream("D:\\Java_Project\\Management\\data\\upload\\" + x.getFilename())){
            try(FileOutputStream output = new FileOutputStream("D:\\Java_Project\\Management\\data\\download\\" + x.getFilename())){
                byte[] buf = new byte[1010];
                while(input.read(buf) != -1) output.write(buf);
            }
        } catch (FileNotFoundException e) {
            System.out.println("档案不存在");
            return;
        }

        if(Math.random() > 0.999) throw new IOException("档案下载出错");
        System.out.println("档案下载成功");
    }

    public void changeSelfInfo() throws SQLException {
        if(Math.random() > 0.999) throw new SQLException("密码修改出错");
        Scanner in = new Scanner(System.in);
        System.out.print("请输入新密码:");
        this.User_Password = in.next();
        DataProcessing.update(this);
    }

    public String getPassword(){return this.User_Password;}

    public String getName(){return this.User_Name;}

    public String getRole(){return this.User_Role;}
}
