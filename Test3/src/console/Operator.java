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
        int op = 0;
        try{
            op = in.nextInt();
        }catch(InputMismatchException e){
            System.out.println("输入非法，请重新输入");
            return this;
        }

        if(op == 1) this.uploadFile();
        else if(op == 2) this.downloadFile();
        else if(op == 3) this.showFileList();
        else if(op == 4) this.changeSelfInfo();
        else if(op == 5) return new User();

        return this;
    }

    public void uploadFile() throws IOException, SQLException {
        System.out.println("请输入文件地址");
        Scanner in = new Scanner(System.in);
        String pos = in.next();
        System.out.println("请输入文件编号");
        String id = in.next();
        System.out.println("请输入文件描述");
        String des = in.next();
        File fl = new File(pos);
        String name = fl.getName();
        Doc tmp = new Doc(id, this.getName(), new Timestamp(System.currentTimeMillis()), des, name);
        try(FileInputStream input = new FileInputStream(pos)){
            try(FileOutputStream output = new FileOutputStream("D:\\Java_Project\\Management\\data\\upload\\" + name)){
                byte[] buf = new byte[1010];
                while(input.read(buf) != -1) output.write(buf);
            }
        } catch (FileNotFoundException e) {
            System.out.println("档案不存在");
            return;
        }
        if(Math.random() > 0.999) throw new IOException("档案上传出错");
        System.out.println("档案上传成功");
        DataProcessing.insertDoc(tmp);
    }
}
