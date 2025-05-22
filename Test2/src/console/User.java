package console;

import java.util.Scanner;

public class User {
    private String User_Name;
    private String User_Password;
    private String User_Role;
    User(String name, String password, String role){
        this.User_Name = name;
        this.User_Password = password;
        this.User_Role = role;
    }

    public User(){}
    public User showMenu(){
        System.out.print("""
        ****欢迎进入档案系统****
                1.登录
                2.退出
        *********************
        """);
        System.out.print("请选择你的菜单:");
        Scanner in = new Scanner(System.in);
        int op = in.nextInt();
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

    public void showFileList(){
        System.out.println("档案列表");
    }

    public void downloadFile(){
        System.out.println("下载档案");
    }

    public void changeSelfInfo(){
        Scanner in = new Scanner(System.in);
        System.out.print("请输入新密码:");
        this.User_Password = in.next();
        DataProcessing.update(this);
    }

    public String getPassword(){return this.User_Password;}

    public String getName(){return this.User_Name;}

    public String getRole(){return this.User_Role;}
}
