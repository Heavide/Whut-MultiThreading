package console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Browser extends User{

    public Browser(String name, String passwd, String role){
        super(name, passwd, role);
    }
    @Override
    public User showMenu() throws SQLException, IOException {
        System.out.print("""
        ****欢迎进入档案浏览员菜单****
                1.下载文件
                2.文件列表
                3.修改密码
                4.退出
        **************************
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
            this.downloadFile();
        }
        else if(op == 2){
            this.showFileList();
        }
        else if(op == 3){
            this.changeSelfInfo();
        }
        else return new User();

        return this;
    }
}
