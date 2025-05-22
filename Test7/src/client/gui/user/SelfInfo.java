package client.gui.user;

import client.gui.MainFrame;
import client.gui.Tool;
import server.console.User;

import javax.swing.*;
import java.awt.*;

public class SelfInfo extends JFrame {

    public SelfInfo(User user, MainFrame father){
        setTitle("个人信息");
        setSize(480, 480);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(Box.createVerticalGlue());

        //title
        Box title_box = Box.createHorizontalBox();
        JLabel title = Tool.getLabel("个人信息", 100, 25, 20, SwingConstants.CENTER);
        title_box.add(title);
        getContentPane().add(title_box);
        getContentPane().add(Box.createVerticalStrut(15));

        //name
        JLabel name = Tool.getLabel("用户名", 100, 30, 15, SwingConstants.CENTER);
        JTextField name_input = Tool.getTextField(300, 30, user.getName());
        name_input.setEnabled(false);
        Box name_box = Tool.getInputCombine(name, name_input, 10);
        getContentPane().add(name_box);
        getContentPane().add(Box.createVerticalStrut(15));

        //role
        JLabel role = Tool.getLabel("身份", 100, 30, 15, SwingConstants.CENTER);
        JTextField role_input = Tool.getTextField(300, 30, user.getRole());
        role_input.setEnabled(false);
        Box role_box = Tool.getInputCombine(role, role_input, 10);
        getContentPane().add(role_box);
        getContentPane().add(Box.createVerticalStrut(15));

        //passwd
        Box passwd_box = Box.createHorizontalBox();
        passwd_box.add(Box.createHorizontalGlue());
        JLabel passwd = Tool.getLabel("密码", 100, 30, 15, SwingConstants.CENTER);
        JButton passwd_change = new JButton("修改密码");
        passwd_change.setPreferredSize(new Dimension(300, 30));
        passwd_box.add(passwd);
        passwd_box.add(Box.createHorizontalStrut(10));
        passwd_box.add(passwd_change);
        passwd_box.add(Box.createHorizontalGlue());
        getContentPane().add(passwd_box);
        getContentPane().add(Box.createVerticalStrut(15));

        //button_event
        passwd_change.addActionListener(e -> new PasswdChange(user, father, this));

        setVisible(true);
    }
}
