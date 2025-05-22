package console;

import java.util.Scanner;
import java.util.Stack;

public class Administrator extends User{

    public Administrator(String name, String passwd, String role){
        super(name, passwd, role);
    }
    @Override
    public User showMenu(){
        System.out.print("""
        ****欢迎进入档案管理员菜单****
                  1.新增用户
                  2.删除用户
                  3.修改用户
                  4.用户列表
                  5.下载档案
                  6.档案列表
                  7.修改密码
                  8.退出
        **************************
        """);
        System.out.print("请选择你的菜单:");
        Scanner in = new Scanner(System.in);
        int op = in.nextInt();
        if(op == 1) this.addUser();
        else if(op == 2) this.delUser();
        else if(op == 3) this.changeUserInfo();
        else if(op == 4) this.listUser();
        else if(op == 5) this.downloadFile();
        else if(op == 6) this.showFileList();
        else if(op == 7) this.changeSelfInfo();
        else if(op == 8) return new User();

        return this;
    }

    public void changeUserInfo(){
        Scanner in = new Scanner(System.in);
        String name = null, passwd = null, role = null;
        System.out.print("请输入要修改的用户:");
        name = in.next();
        while(DataProcessing.searchUser(name) == null){
            System.out.print("不存在该用户，请重新输入:");
            name = in.next();
        }
        User who = DataProcessing.searchUser(name), after = null;
        System.out.print("""
        是否修改密码?
        1-是 2-否
        """);
        int cs = in.nextInt();
        if(cs == 1){
            System.out.print("请输入新密码:");
            passwd = in.next();
        }
        else if(cs == 2) passwd = who.getPassword();
        System.out.print("""
        是否修改身份?
        1-是 2-否
        """);
        if(cs == 1){
            System.out.print("""
            请选择身份:
            1-Operator 2-Browser 3-Administrator
            """);
            int x = in.nextInt();
            if(x == 1) role = "Operator";
            else if(x == 2) role = "Browser";
            else if(x == 3) role = "Administrator";
        }
        else if(cs == 2) role = who.getRole();
        if(role.equals("Operator")) after = new Operator(name, passwd, role);
        else if(role.equals("Browser")) after = new Browser(name, passwd, role);
        else if(role.equals("Administrator")) after = new Administrator(name, passwd, role);
        DataProcessing.update(after);
        System.out.println("修改成功!");
    }

    public void delUser(){
        String name = null;
        Scanner in = new Scanner(System.in);
        System.out.print("请输入你要删除的用户:");
        name = in.next();
        while(name.equals(this.getName())){
            System.out.print("你不能删除你自己，请重新输入:");
            name = in.next();
        }
        while(DataProcessing.searchUser(name) == null){
            System.out.println("不存在该用户，请重新输入:");
            name = in.next();
        }
        DataProcessing.delete(name);
        System.out.println("删除成功！");
    }

    public void addUser(){
        String name = null, passwd = null, role = null;
        User tmp = null;
        Scanner in = new Scanner(System.in);
        System.out.print("请输入用户名字:");
        name = in.next();
        System.out.print("请输入用户密码:");
        passwd = in.next();
        System.out.print("""
        请选择身份:
        1-Operator 2-Browser 3-Administrator
        """);
        int x = in.nextInt();
        if(x == 1) tmp = new Operator(name, passwd, "Operator");
        else if(x == 2) tmp = new Operator(name, passwd, "Browser");
        else if(x == 3) tmp = new Operator(name, passwd, "Administrator");
        DataProcessing.insert(tmp);
    }

    public void listUser(){
        DataProcessing.getAllUser();
    }
}
