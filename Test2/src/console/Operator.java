package console;

import java.util.Scanner;

public class Operator extends User{

    public Operator(String name, String passwd, String role){
        super(name, passwd, role);
    }
    @Override
    public User showMenu(){
        System.out.print("""
        ****欢迎进入档案录入人员菜单****
                  1.上传档案
                  2.下载档案
                  3.文件列表
                  4.修改密码
                  5.退出
        ***************************
        """);
        System.out.print("请选择你的菜单:");
        Scanner in = new Scanner(System.in);
        int op = in.nextInt();
        if(op == 1) this.uploadFile();
        else if(op == 2) this.downloadFile();
        else if(op == 3) this.showFileList();
        else if(op == 4) this.changeSelfInfo();
        else if(op == 5) return new User();

        return this;
    }

    public void uploadFile(){
        System.out.println("上传档案");
    }
}
