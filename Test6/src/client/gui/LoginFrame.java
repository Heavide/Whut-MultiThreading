package gui;

import console.DataProcessing;
import console.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    public LoginFrame(){
        setTitle("档案管理系统");
        setSize(512,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        //title
        Box title_box = Box.createHorizontalBox();
        JLabel title = Tool.getLabel("档案管理系统", 250, 30, 25, SwingConstants.CENTER);
        title_box.add(title);
        getContentPane().add(Box.createVerticalGlue());
        getContentPane().add(Box.createVerticalStrut(30));
        getContentPane().add(title_box);
        getContentPane().add(Box.createVerticalStrut(30));

        //login_user
        JLabel user_name = Tool.getLabel("用户名", 50, 30, 15, SwingConstants.CENTER);
        JTextField user_input = Tool.getTextField(300, 30, "");
        Box user = Tool.getInputCombine(user_name, user_input, 20);
        getContentPane().add(user);
        getContentPane().add(Box.createVerticalStrut(30));

        //login_passwd
        JLabel passwd_name = Tool.getLabel("密码", 50, 30, 15, SwingConstants.CENTER);
        JPasswordField passwd_input = Tool.getPasswordField(300, 30);
        Box passwd = Tool.getInputCombine_Passwd(passwd_name, passwd_input, 20);
        getContentPane().add(passwd);
        getContentPane().add(Box.createVerticalStrut(30));

        //login_button
        Box login = Box.createHorizontalBox();
        JButton login_button = new JButton("登录");
        login_button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        //login_button.setEnabled(false);
        login.add(login_button);
        getContentPane().add(login);
        getContentPane().add(Box.createVerticalGlue());


        //button_event
        login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = user_input.getText();
                String code = new String(passwd_input.getPassword());
                try{
                    User now = DataProcessing.search(name, code);
                    if(now == null){
                        JOptionPane.showMessageDialog(null, "账号或密码错误，请重新输入", "登录失败", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        dispose();
                        new MainFrame(now);
                    }
                } catch (SQLException x) {
                    JOptionPane.showMessageDialog(null, "数据库错误", "Worry", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }


}
